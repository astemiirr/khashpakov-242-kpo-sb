package payments_service.repos;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import payments_service.domain.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> getByUserId(Integer userId);

    boolean existsByUserId(Integer userId);
}
