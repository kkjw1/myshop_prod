package myshop.shop.repository.delivery;

import myshop.shop.entity.delivery.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeliveryRepository extends JpaRepository<Delivery, Long>, DeliveryRepositoryCustom {
}
