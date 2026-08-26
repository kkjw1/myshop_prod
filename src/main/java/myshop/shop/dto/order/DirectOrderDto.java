package myshop.shop.dto.order;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter @Setter
@ToString(of = {"itemNo", "itemOptionNo", "name", "imagePath", "count", "totalStock", "optionStock", "originalPrice", "optionPrice", "discountPer", "optionName", "price"})
public class DirectOrderDto {
    private Long itemNo;            // Item
    private Long itemOptionNo;      // ItemOption, null
    private String name;            // Item
    private String imagePath;       // ItemImage
    private int count;              // Cart
    private int totalStock;         // Item
    private int optionStock;        // ItemOption, null
    private BigDecimal originalPrice;      // Item
    private BigDecimal optionPrice;        // ItemOption, null존재
    private BigDecimal discountPer;        // Item
    private String optionName;      // ItemOption, null존재

    private BigDecimal price;       // 계산된 개당가격(할인된 가격 + 옵션 추가금액)

    public DirectOrderDto() {
    }
}

