package myshop.shop.dto.cancelRequest;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter @Setter
@ToString(of = {"requestNo", "requestType", "totalPrice", "deliveryFee", "refundPrice", "action", "decisionReason"})
public class RequestDecisionDto {
    /**
     * 요청번호
     * 요청타입(취소, 반품)
     * 결제금액
     * 차감배송비
     * 총반품금액
     * 처리 상태 (요청 거부 or 승인)
     * 거부 사유
     */
    private Long requestNo;
    private String requestType;
    private BigDecimal totalPrice;
    private BigDecimal deliveryFee;
    private BigDecimal refundPrice;
    private String action;
    private String decisionReason;

    public RequestDecisionDto() {
    }
}
