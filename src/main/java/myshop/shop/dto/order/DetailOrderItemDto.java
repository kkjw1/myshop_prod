package myshop.shop.dto.order;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter @Setter
@ToString(of = {"count", "totalPrice", "imageUrl", "itemName", "optionName"})
public class DetailOrderItemDto {
    private int count;
    private BigDecimal totalPrice;     // 가격 * 개수
    private String imageUrl;
    private String itemName;
    private String optionName;

    public DetailOrderItemDto() {
    }
}
