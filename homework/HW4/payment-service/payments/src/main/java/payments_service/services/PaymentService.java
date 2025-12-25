package payments_service.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import payments_service.domain.Account;
import payments_service.domain.OrderStatus;
import payments_service.domain.OrderToImport;
import payments_service.domain.OrderUpdateEvent;
import payments_service.repos.AccountRepository;
import payments_service.repos.InboxRepository;
import payments_service.repos.OutboxRepository;

@Slf4j
@Service
@AllArgsConstructor
@Getter
public class PaymentService {
    private final AccountRepository accountRepository;

    private final InboxRepository inboxRepository;

    private final OutboxRepository outboxRepository;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @KafkaListener(topics = "orders")
    @Transactional
    public void addOrder(String payload) throws JsonProcessingException {
        try {
            var order = objectMapper.readValue(payload, OrderToImport.class);

            if (inboxRepository.existsById(order.getId())) {
                return;
            }
            order.setProcessed(false);
            inboxRepository.save(order);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    @Scheduled(fixedDelay = 500)
    @Transactional
    public void checkInboxAndProcessOrder() {
        var orderOptional = inboxRepository.getFirstByProcessedFalseOrderByCreatedAtAsc();

        if (orderOptional.isEmpty()) {
            return;
        }

        var order = orderOptional.get();

        var event = new OrderUpdateEvent(order.getId(), OrderStatus.NEW, false);

        try {
            var account = accountRepository.getByUserId(order.getUserId()).get();
            account.updateBalance(-order.getAmount());
            event.setStatus(OrderStatus.FINISHED);
            accountRepository.save(account);
        } catch (Exception e) {
            log.error("Error at processing order {}: {}", order.getId(), e.getMessage());
            event.setStatus(OrderStatus.CANCELLED);
        }
        order.setProcessed(true);
        inboxRepository.save(order);
        outboxRepository.save(event);
    }

    @Scheduled(fixedDelay = 500)
    @Transactional
    public void checkOutbox() throws JsonProcessingException {
        var eventOptional = outboxRepository.getFirstByProcessedFalse();

        if (eventOptional.isEmpty()) {
            return;
        }

        var event = eventOptional.get();
        event.setProcessed(true);
        try {
            kafkaTemplate.send("orders-result", objectMapper.writeValueAsString(event));
            outboxRepository.save(event);
        } catch (Exception e) {
            log.error("Error at processing event {}: {}", event.getId(), e.getMessage());
            throw e;
        }
    }

    @Transactional
    public Account createAccount(Integer userId, Integer balance) throws JsonProcessingException {
        var account = new Account(userId, balance);

        if (accountRepository.existsByUserId(account.getUserId())) {
            return null;
        }
        return accountRepository.save(account);
    }

    public Optional<Account> getAccount(Integer userId) {
        return accountRepository.getByUserId(userId);
    }

    public List<Account> getAccounts() {
        return accountRepository.findAll();
    }

    @Transactional
    public Optional<Account> updateBalance(Integer userId, Integer delta) {
        var accountOptional = accountRepository.getByUserId(userId);
        if (accountOptional.isEmpty()) {
            return accountOptional;
        }
        var account = accountOptional.get();
        account.updateBalance(delta);
        return Optional.of(accountRepository.save(account));
    }
}
