package myshop.shop.repository.cancelRequest;

import myshop.shop.entity.cancelRequest.CancelRequest;
import myshop.shop.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CancelRequestRepository extends JpaRepository<CancelRequest, Long>, CancelRequestRepositoryCustom {
    Long member(Member member);
}
