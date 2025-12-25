package payments_service.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import payments_service.domain.Account;
import payments_service.services.PaymentService;

@Slf4j
@RestController
@AllArgsConstructor
@Tag(name = "Контроллер для счетов")
public class PaymentsController {
    private final PaymentService paymentService;

    @PostMapping("/payments/")
    @Operation(summary = "Создать счет")
    public ResponseEntity<Account> createAccount(@RequestParam Integer userId, @RequestParam Integer balance) {
        try {
            var account = paymentService.createAccount(userId, balance);
            if (account == null) {
                return ResponseEntity.unprocessableEntity().build();
            }
            return ResponseEntity.ok(account);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/payments/{userId}")
    @Operation(summary = "Обновить баланс счета")
    public ResponseEntity<Account> updateAccount(@PathVariable Integer userId, @RequestParam Integer delta) {
        try {
            var account = paymentService.updateBalance(userId, delta);
            return account.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/payments/{id}")
    @Operation(summary = "Узнать баланс счета")
    public ResponseEntity<Account> getOrderStatus(@PathVariable Integer id) {
        var account = paymentService.getAccount(id);
        return account.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/payments/list")
    @Operation(summary = "Получить список всех счетов")
    public ResponseEntity<List<Account>> getOrdersBuUserId() {
        var accounts = paymentService.getAccounts();
        return ResponseEntity.ok(accounts);
    }
}