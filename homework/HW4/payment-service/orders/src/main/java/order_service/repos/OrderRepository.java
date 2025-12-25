package order_service.repos;

import java.util.List;
import order_service.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findAllByUserId(Integer userId);
}
