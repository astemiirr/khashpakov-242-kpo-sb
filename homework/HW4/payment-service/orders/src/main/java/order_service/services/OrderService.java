package order_service.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import order_service.domain.Order;
import order_service.domain.OrderStatus;
import order_service.domain.OrderToExport;
import order_service.domain.OrderUpdateEvent;
import order_service.repos.OrderRepository;
import order_service.repos.OutboxRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@AllArgsConstructor
@Getter
public class OrderService {
    private final OrderRepository orderRepository;

    private final OutboxRepository outboxRepository;

    private final KafkaTemplate<String, String> kafkaTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());

    @Scheduled(fixedDelay = 500)
    @Transactional
    public void checkOutboxToSend() throws JsonProcessingException {
        var orderOptional = outboxRepository.getFirstByProcessedFalseOrderByCreatedAtAsc();
        if (orderOptional.isEmpty()) {
            return;
        }

        var order = orderOptional.get();
        try {
            kafkaTemplate.send("orders", objectMapper.writeValueAsString(order));
            order.setProcessed(true);
            outboxRepository.save(order);
        } catch (Exception e) {
            log.error("Error at processing orderToExport {}: {}", order.getId(), e.getMessage());
            throw e;
        }
    }

    @Transactional
    public Order createOrder(Integer userId, Integer amount, String description) throws JsonProcessingException {
        Order order = new Order(userId, amount, OrderStatus.NEW, description);

        var orderToExport = new OrderToExport(order.getUserId(), order.getAmount(), false);

        orderRepository.save(order);
        outboxRepository.save(orderToExport);

        return order;
    }

    @KafkaListener(topics = "orders-result")
    @Transactional
    public void updateOrderState(String payload) throws JsonProcessingException {
        try {
            var event = objectMapper.readValue(payload, OrderUpdateEvent.class);
            Order order = orderRepository.getReferenceById(event.getOrderId());
            if (order.getStatus() != event.getStatus()) {
                order.setStatus(event.getStatus());
                orderRepository.save(order);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
            throw e;
        }
    }

    public Order getOrder(Integer orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    public List<Order> getOrdersByUserId(Integer userId) {
        return orderRepository.findAllByUserId(userId);
    }
}
