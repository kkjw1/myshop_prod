package myshop.shop.dto.cancelRequest;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import myshop.shop.entity.cancelRequest.CancelReasonCode;
import myshop.shop.entity.cancelRequest.CancelRequestStatus;

import java.math.BigDecimal;

@Getter @Setter
@ToString(of = {"orderNo", "orderItemNo", "memberNo", "count", "cancelReasonCode", "reasonDetail", "price", "cancelRequestStatus"})
public class SaveCancelRequestDto {
    private Long orderNo;
    private Long orderItemNo;
    private Long memberNo;
    private int count;
    private CancelReasonCode cancelReasonCode;
    private String reasonDetail;
    private BigDecimal price;
    private CancelRequestStatus cancelRequestStatus;

    public SaveCancelRequestDto() {
    }

    public SaveCancelRequestDto(Long orderNo, Long orderItemNo, Long memberNo, int count, CancelReasonCode cancelReasonCode, String reasonDetail, BigDecimal price, CancelRequestStatus cancelRequestStatus) {
        this.orderNo = orderNo;
        this.orderItemNo = orderItemNo;
        this.memberNo = memberNo;
        this.count = count;
        this.cancelReasonCode = cancelReasonCode;
        this.reasonDetail = reasonDetail;
        this.price = price;
        this.cancelRequestStatus = cancelRequestStatus;
    }
}
