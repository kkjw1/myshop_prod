package myshop.shop.dto.cart;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter @Setter
@ToString(of = {"cartNo", "itemNo", "itemOptionNo", "name", "imagePath", "count", "totalStock", "optionStock", "originalPrice", "optionPrice", "discountPer", "optionName", "price"})
public class ManageCartDto {
    private Long cartNo;            // Cart
    private Long itemNo;            // Item
    private Long itemOptionNo;      // ItemOption
    private String name;            // Item
    private String imagePath;       // ItemImage
    private int count;              // Cart
    private int totalStock;         // Item
    private int optionStock;        // ItemOption
    private BigDecimal originalPrice;      // Item
    private BigDecimal optionPrice;        // ItemOption, null존재
    private BigDecimal discountPer;        // Item
    private String optionName;      // ItemOption, null존재

    private BigDecimal price;       // 계산된 개당가격(할인된 가격 + 옵션 추가금액)

    public ManageCartDto() {
    }

    public ManageCartDto(Long cartNo, Long itemNo, Long itemOptionNo, String name, String imagePath, int count, int totalStock, int optionStock, int originalPrice, int optionPrice, String optionName) {
        this.cartNo = cartNo;
        this.itemNo = itemNo;
        this.itemOptionNo = itemOptionNo;
        this.name = name;
        this.imagePath = imagePath;
        this.count = count;
        this.totalStock = totalStock;
        this.optionStock = optionStock;
        this.originalPrice = BigDecimal.valueOf(originalPrice);
        this.optionPrice = BigDecimal.valueOf(optionPrice);
        this.optionName = optionName;
    }
    public ManageCartDto(Long cartNo, Long itemNo, Long itemOptionNo, String name, String imagePath, int count, int totalStock, int optionStock, BigDecimal originalPrice, BigDecimal optionPrice, String optionName) {
        this.cartNo = cartNo;
        this.itemNo = itemNo;
        this.itemOptionNo = itemOptionNo;
        this.name = name;
        this.imagePath = imagePath;
        this.count = count;
        this.totalStock = totalStock;
        this.optionStock = optionStock;
        this.originalPrice = originalPrice;
        this.optionPrice = optionPrice;
        this.optionName = optionName;
    }
}
