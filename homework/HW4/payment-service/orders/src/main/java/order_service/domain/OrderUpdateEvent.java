package order_service.domain;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderUpdateEvent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer orderId;

    @Column(nullable = false)
    private OrderStatus status;

    @Column(nullable = false)
    private Boolean processed;

    public OrderUpdateEvent(Integer orderId, OrderStatus status, Boolean processed) {
        this.orderId = orderId;
        this.status = status;
        this.processed = processed;
    }
}