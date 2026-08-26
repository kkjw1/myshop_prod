package myshop.shop.repository.order;

import myshop.shop.entity.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {
    Optional<Order> findByNo(Long orderNo);
}
