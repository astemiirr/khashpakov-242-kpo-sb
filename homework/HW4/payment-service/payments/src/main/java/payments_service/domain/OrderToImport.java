package payments_service.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "inbox_order")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderToImport {
    @Id
    private Integer id;

    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private boolean processed;

    @Column(nullable = false)
    private LocalDateTime createdAt;
}