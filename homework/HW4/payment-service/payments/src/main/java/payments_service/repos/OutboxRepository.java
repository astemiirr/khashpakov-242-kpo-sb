package payments_service.repos;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import payments_service.domain.OrderUpdateEvent;

public interface OutboxRepository extends JpaRepository<OrderUpdateEvent, Integer> {
    Optional<OrderUpdateEvent> getFirstByProcessedFalse();
}
