package myshop.shop.repository.returnRequest;

import myshop.shop.entity.returnRequest.ReturnRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReturnRequestRepository extends JpaRepository<ReturnRequest, Long> {
}
