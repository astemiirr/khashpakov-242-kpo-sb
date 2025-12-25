package order_service.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "outbox_order")
@Getter
@Setter
@NoArgsConstructor
public class OrderToExport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private boolean processed;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public OrderToExport(Integer userId, Integer amount, boolean processed) {
        if (amount < 0) {
            throw new IllegalArgumentException("Amount must be a positive number");
        }

        this.userId = userId;
        this.amount = amount;
        this.processed = processed;
        this.createdAt = LocalDateTime.now();
    }
}