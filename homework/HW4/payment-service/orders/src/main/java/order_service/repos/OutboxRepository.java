package order_service.repos;

import java.util.Optional;
import order_service.domain.OrderToExport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OutboxRepository extends JpaRepository<OrderToExport, Integer> {
    Optional<OrderToExport> getFirstByProcessedFalseOrderByCreatedAtAsc();
}
