package myshop.shop.dto.cancelRequest;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import myshop.shop.entity.cancelRequest.CancelReasonCode;
import myshop.shop.entity.cancelRequest.CancelRequestStatus;
import myshop.shop.entity.returnRequest.ReturnReasonCode;
import myshop.shop.entity.returnRequest.ReturnRequestStatus;

import java.math.BigDecimal;

@Getter @Setter
@ToString(of = {"no", "memberNo", "memberName", "itemName", "optionName", "cancelReasonCode", "returnReasonCode", "reasonDetail",
"price", "refundPrice", "cancelRequestStatus", "returnRequestStatus", "decisionReason"})
public class SellerManageRequestDto {
    /**
     * 요청번호
     * 구매자
     * 요청타입(취소, 반품)
     * 상품 정보 (상품 이름, 옵션, 개수)
     * 고객 신청 사유([단순 변심] ... )
     * 상세 사유
     * 결제 금액
     * 총 환불 금액
     * 결정 사유(엔티티에 추가 필요)
     */
    private Long no;
    private Long memberNo;
    private String memberName;
    private String itemName;
    private String optionName;
    private CancelReasonCode cancelReasonCode;
    private ReturnReasonCode returnReasonCode;
    private String reasonDetail;
    private BigDecimal price;
    private BigDecimal refundPrice;
    private CancelRequestStatus cancelRequestStatus;
    private ReturnRequestStatus returnRequestStatus;
    private String decisionReason;

    public SellerManageRequestDto() {
    }
}
