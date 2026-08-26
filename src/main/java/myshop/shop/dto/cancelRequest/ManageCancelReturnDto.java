package myshop.shop.dto.cancelRequest;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import myshop.shop.entity.cancelRequest.CancelReasonCode;
import myshop.shop.entity.cancelRequest.CancelRequestStatus;
import myshop.shop.entity.returnRequest.ReturnReasonCode;
import myshop.shop.entity.returnRequest.ReturnRequestStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
@ToString()
public class ManageCancelReturnDto {

    /**
     * 주문번호
     * 상품번호 (클릭시 접근 가능하게)
     * 환불금액
     * 반품(취소)처리 상태
     * 반품(취소)사유
     * 상세 사유
     * 상품이름
     * 옵션
     * 요청 일시
     * 개수
     * 상품 이미지
     */
    private Long orderItemNo;
    private Long itemNo;
    private BigDecimal price;
    private CancelRequestStatus cancelRequestStatus;
    private ReturnRequestStatus returnRequestStatus;
    private CancelReasonCode cancelReasonCode;
    private ReturnReasonCode returnReasonCode;
    private String reasonDetail;
    private String itemName;
    private String optionName;
    private LocalDateTime requestTime;
    private int count;
    private String imageUrl;

    public ManageCancelReturnDto() {
    }
}
