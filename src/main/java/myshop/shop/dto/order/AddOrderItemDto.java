package myshop.shop.dto.order;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter @Setter
@ToString(of = {"cartNo", "optionNo", "itemNo", "count", "discountedPrice", "totalPrice", "imageUrl", "itemName", "optionName"})
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

    public AddOrderItemDto() {
    }

    public AddOrderItemDto(Long cartNo, Long optionNo, Long itemNo, int count, BigDecimal discountedPrice, BigDecimal totalPrice, String imageUrl, String itemName, String optionName) {
        this.cartNo = cartNo;
        this.optionNo = optionNo;
        this.itemNo = itemNo;
        this.count = count;
        this.discountedPrice = discountedPrice;
        this.totalPrice = totalPrice;
        this.imageUrl = imageUrl;
        this.itemName = itemName;
        this.optionName = optionName;
    }
}
