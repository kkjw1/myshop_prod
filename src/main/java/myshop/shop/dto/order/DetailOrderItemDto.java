package myshop.shop.dto.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter @Setter
@ToString(of = {"count", "totalPrice", "imageUrl", "itemName", "optionName"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetailOrderItemDto {
    private int count;
    private BigDecimal totalPrice;     // 가격 * 개수
    private String imageUrl;
    private String itemName;
    private String optionName;

}
