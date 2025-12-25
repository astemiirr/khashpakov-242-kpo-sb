package order_service.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import order_service.domain.Order;
import order_service.domain.OrderStatus;
import order_service.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@AllArgsConstructor
@Tag(name = "Контроллер для операций")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/orders/")
    @Operation(summary = "Создать заказ")
    public ResponseEntity<Order> createOrder(@RequestParam Integer userId, @RequestParam Integer amount,
                                             @RequestParam String description) {
        try {
            Order order = orderService.createOrder(userId, amount, description);
            return ResponseEntity.ok(order);
        } catch (Exception e) {
            log.error(e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/orders/{id}")
    @Operation(summary = "Узнать состояние заказа")
    public ResponseEntity<OrderStatus> getOrderStatus(@PathVariable Integer id) {
        Order order = orderService.getOrder(id);
        if (order == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(order.getStatus());
    }

    @GetMapping("/orders/user/{userId}")
    @Operation(summary = "Получить список всех заказов пользователя")
    public ResponseEntity<List<Order>> getOrdersBuUserId(@PathVariable Integer userId) {
        var orders = orderService.getOrdersByUserId(userId);
        return ResponseEntity.ok(orders);
    }
}