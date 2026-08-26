package myshop.shop.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import myshop.shop.dto.cancelRequest.RequestDecisionDto;
import myshop.shop.dto.cancelRequest.SaveCancelRequestDto;
import myshop.shop.dto.cancelRequest.SellerManageRequestDto;
import myshop.shop.entity.orderItem.OrderItem;
import myshop.shop.entity.cancelRequest.CancelRequest;
import myshop.shop.entity.cancelRequest.CancelRequestStatus;
import myshop.shop.entity.member.Member;
import myshop.shop.entity.orderItem.OrderItemStatus;
import myshop.shop.entity.refund.Refund;
import myshop.shop.entity.refund.RefundStatus;
import myshop.shop.repository.cancelRequest.CancelRequestRepository;
import myshop.shop.repository.member.MemberRepository;
import myshop.shop.repository.orderItem.OrderItemRepository;
import myshop.shop.repository.refund.RefundRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class CancelRequestService {

    private final CancelRequestRepository cancelRequestRepository;
    private final OrderItemRepository orderItemRepository;
    private final MemberRepository memberRepository;
    private final RefundRepository refundRepository;
    private final EntityManager em;

    /**
     * 주문 취소 신청
     * 주문 목록/배송 조회 -> 주문 취소 신청
     */
    public void saveCancelRequest(SaveCancelRequestDto saveCancelRequestDto) {
        OrderItem orderItem = orderItemRepository.findById(saveCancelRequestDto.getOrderItemNo()).orElse(null);
        Member memberProxy = memberRepository.getReferenceById(saveCancelRequestDto.getMemberNo());

        orderItem.updateOrderItemStatus(OrderItemStatus.취소접수);

        CancelRequest saveCancelRequest = new CancelRequest(orderItem, memberProxy,
                saveCancelRequestDto.getCount(),
                saveCancelRequestDto.getCancelReasonCode(),
                saveCancelRequestDto.getReasonDetail(),
                saveCancelRequestDto.getPrice(),
                CancelRequestStatus.요청);

        cancelRequestRepository.save(saveCancelRequest);
    }


    /**
     * 취소/반품 관리 폼
     * 판매자 -> 취소/반품 관리
     */
    public List<SellerManageRequestDto> manageRequestList(Long sellerNo) {
        return cancelRequestRepository.findSellerManageRequest(sellerNo);
    }


    /**
     * 승인 요청, 거부, 완료처리
     * 취소/반품 관리 처리하기 -> 승인 요청
     * 취소/반품 관리 처리하기 -> 요청 거부
     * 취소/반품 관리 완료처리 -> 최종 완료 처리
     */
    public void decisionCancelRequest(RequestDecisionDto requestDecisionDto) {
        CancelRequest cancelRequest = cancelRequestRepository.findById(requestDecisionDto.getRequestNo()).orElse(null);

        if (requestDecisionDto.getAction().equals("거부") && cancelRequest != null) {
            cancelRequest.updateCancelRequestStatus(CancelRequestStatus.거부);
            cancelRequest.updateDecisionReason(requestDecisionDto.getDecisionReason());
        }
        else if (requestDecisionDto.getAction().equals("승인") && cancelRequest != null) {
            cancelRequest.updateCancelRequestStatus(CancelRequestStatus.승인);
            refundRepository.save(new Refund(cancelRequest, null, requestDecisionDto.getTotalPrice(),
                    requestDecisionDto.getDeliveryFee(), requestDecisionDto.getRefundPrice(), RefundStatus.진행중));
        }
        else if (requestDecisionDto.getAction().equals("완료") && cancelRequest != null) {
            cancelRequest.updateCancelRequestStatus(CancelRequestStatus.완료);
            em.createQuery("update Refund f set f.refundStatus=:refundStatus where f.cancelRequest.no=:cancelRequestNo")
                            .setParameter("refundStatus", RefundStatus.완료)
                            .setParameter("cancelRequestNo",requestDecisionDto.getRequestNo())
                            .executeUpdate();
        }
    }
}
