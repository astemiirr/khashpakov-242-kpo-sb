package payments_service.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "accounts")
@Getter
@Setter
@NoArgsConstructor
public class Account {
    @Id
    @Column(nullable = false)
    private Integer userId;

    @Column(nullable = false)
    private Integer balance;

    public Account(Integer userId, Integer balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        this.userId = userId;
        this.balance = balance;
    }

    public void updateBalance(Integer delta) {
        var newBalance = balance + delta;
        if (newBalance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        this.balance = newBalance;
    }
}
