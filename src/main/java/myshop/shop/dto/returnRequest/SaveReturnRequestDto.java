package myshop.shop.dto.returnRequest;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;
import lombok.ToString;
import myshop.shop.entity.returnRequest.ReturnReasonCode;
import myshop.shop.entity.returnRequest.ReturnRequestStatus;

import java.math.BigDecimal;

@Getter @Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveReturnRequestDto {
    private Long orderItemNo;
    private Long memberNo;
    private int count;
    private ReturnReasonCode returnReasonCode;
    private String reasonDetail;
    private BigDecimal price;
    private ReturnRequestStatus returnRequestStatus;

}
