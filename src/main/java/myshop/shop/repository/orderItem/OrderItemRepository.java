package myshop.shop.repository.orderItem;

import myshop.shop.entity.orderItem.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long>, OrderItemRepositoryCustom {
}
