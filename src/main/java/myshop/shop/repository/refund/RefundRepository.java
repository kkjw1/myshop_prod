package myshop.shop.repository.refund;

import myshop.shop.entity.refund.Refund;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefundRepository extends JpaRepository<Refund, Long> {
}
