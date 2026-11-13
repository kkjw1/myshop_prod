package myshop.shop.dto.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter @Setter
@ToString(of = {"cartNo", "optionNo", "itemNo", "count", "discountedPrice", "totalPrice", "imageUrl", "itemName", "optionName"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddOrderItemDto {
    private Long cartNo;
    private Long optionNo;
    private Long itemNo;
    private int count;
    private BigDecimal discountedPrice;     // 할인된 가격
    private BigDecimal totalPrice;     // count * price
    private String imageUrl;
    private String itemName;
    private String optionName;

}
