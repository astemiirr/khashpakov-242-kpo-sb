package payments_service.repos;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import payments_service.domain.OrderToImport;

public interface InboxRepository extends JpaRepository<OrderToImport, Integer> {
    Optional<OrderToImport> getFirstByProcessedFalseOrderByCreatedAtAsc();
}
