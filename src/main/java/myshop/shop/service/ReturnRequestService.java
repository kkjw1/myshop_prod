package myshop.shop.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import myshop.shop.dto.cancelRequest.ManageCancelReturnDto;
import myshop.shop.dto.cancelRequest.RequestDecisionDto;
import myshop.shop.dto.returnRequest.SaveReturnRequestDto;
import myshop.shop.entity.cancelRequest.CancelRequest;
import myshop.shop.entity.cancelRequest.CancelRequestStatus;
import myshop.shop.entity.orderItem.OrderItem;
import myshop.shop.entity.member.Member;
import myshop.shop.entity.orderItem.OrderItemStatus;
import myshop.shop.entity.refund.Refund;
import myshop.shop.entity.refund.RefundStatus;
import myshop.shop.entity.returnRequest.ReturnRequest;
import myshop.shop.entity.returnRequest.ReturnRequestStatus;
import myshop.shop.repository.cancelRequest.CancelRequestRepository;
import myshop.shop.repository.member.MemberRepository;
import myshop.shop.repository.orderItem.OrderItemRepository;
import myshop.shop.repository.refund.RefundRepository;
import myshop.shop.repository.returnRequest.ReturnRequestRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReturnRequestService {

    private final ReturnRequestRepository returnRequestRepository;
    private final OrderItemRepository orderItemRepository;
    private final MemberRepository memberRepository;
    private final CancelRequestRepository cancelRequestRepository;
    private final RefundRepository refundRepository;
    private final EntityManager em;

    /**
     * 반품 신청
     * 주문 목록/배송 조회 -> 반품 신청
     */
    public void saveReturnRequest(SaveReturnRequestDto saveReturnRequestDto) {
        OrderItem orderItem = orderItemRepository.findById(saveReturnRequestDto.getOrderItemNo()).orElse(null);
        Member memberProxy = memberRepository.getReferenceById(saveReturnRequestDto.getMemberNo());

        orderItem.updateOrderItemStatus(OrderItemStatus.반품접수);

        ReturnRequest saveReturnRequest = new ReturnRequest(orderItem, memberProxy,
                saveReturnRequestDto.getCount(),
                saveReturnRequestDto.getReturnReasonCode(),
                saveReturnRequestDto.getReasonDetail(),
                saveReturnRequestDto.getPrice(),
                ReturnRequestStatus.요청);

        returnRequestRepository.save(saveReturnRequest);

    }


    /**
     * 취소/반품 폼
     * 취소/반품 내역
     * 주문 취소 신청 -> 취소/반품 폼
     * 반품 신청 -> 취소/반품 폼
     */
    public List<ManageCancelReturnDto> getCancelReturnList(Long memberNo) {
        return cancelRequestRepository.findCancelReturnList(memberNo);
    }


    /**
     * 승인 요청, 거부, 완료처리
     * 취소/반품 관리 처리하기 -> 승인 요청
     * 취소/반품 관리 처리하기 -> 요청 거부
     * 취소/반품 관리 완료처리 -> 최종 완료 처리
     */
    public void decisionReturnRequest(RequestDecisionDto requestDecisionDto) {

        ReturnRequest returnRequest = returnRequestRepository.findById(requestDecisionDto.getRequestNo()).orElse(null);

        if (requestDecisionDto.getAction().equals("거부") && returnRequest != null) {
            returnRequest.updateReturnRequestStatus(ReturnRequestStatus.거부);
            returnRequest.updateDecisionReason(requestDecisionDto.getDecisionReason());
        }
        else if (requestDecisionDto.getAction().equals("승인") && returnRequest != null) {
            returnRequest.updateReturnRequestStatus(ReturnRequestStatus.승인);
            refundRepository.save(new Refund(null, returnRequest, requestDecisionDto.getTotalPrice(),
                    requestDecisionDto.getDeliveryFee(), requestDecisionDto.getRefundPrice(), RefundStatus.진행중));
        }
        else if (requestDecisionDto.getAction().equals("완료") && returnRequest != null) {
            returnRequest.updateReturnRequestStatus(ReturnRequestStatus.완료);
            em.createQuery("update Refund f set f.refundStatus=:refundStatus where f.returnRequest.no=:returnRequestNo")
                    .setParameter("refundStatus", RefundStatus.완료)
                    .setParameter("returnRequestNo",requestDecisionDto.getRequestNo())
                    .executeUpdate();
        }
    }

}
