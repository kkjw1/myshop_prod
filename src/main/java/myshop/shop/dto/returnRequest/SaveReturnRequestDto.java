package myshop.shop.dto.returnRequest;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import myshop.shop.entity.returnRequest.ReturnReasonCode;
import myshop.shop.entity.returnRequest.ReturnRequestStatus;

import java.math.BigDecimal;

@Getter @Setter
@ToString
public class SaveReturnRequestDto {
    private Long orderItemNo;
    private Long memberNo;
    private int count;
    private ReturnReasonCode returnReasonCode;
    private String reasonDetail;
    private BigDecimal price;
    private ReturnRequestStatus returnRequestStatus;

    public SaveReturnRequestDto() {
    }

    public SaveReturnRequestDto(Long orderItemNo, Long memberNo, int count, ReturnReasonCode returnReasonCode, String reasonDetail, BigDecimal price, ReturnRequestStatus returnRequestStatus) {
        this.orderItemNo = orderItemNo;
        this.memberNo = memberNo;
        this.count = count;
        this.returnReasonCode = returnReasonCode;
        this.reasonDetail = reasonDetail;
        this.price = price;
        this.returnRequestStatus = returnRequestStatus;
    }
}
