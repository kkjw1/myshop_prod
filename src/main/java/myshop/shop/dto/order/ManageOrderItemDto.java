package myshop.shop.dto.order;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import myshop.shop.entity.delivery.DeliveryStatus;
import myshop.shop.entity.orderItem.OrderItemStatus;

import java.math.BigDecimal;

@Getter @Setter
@ToString(of = {"orderItemNo", "itemNo", "orderItemStatus", "deliveryStatus", "courier", "trackingNumber", "imageUrl", "itemName", "optionName", "totalPrice", "count"})
public class ManageOrderItemDto {
    private Long itemNo;
    private Long orderItemNo;
    private OrderItemStatus orderItemStatus;
    private DeliveryStatus deliveryStatus;
    private String courier;
    private String trackingNumber;
    private String imageUrl;
    private String itemName;
    private String optionName;
    private BigDecimal totalPrice;
    private int count;
    private Boolean review;

    public ManageOrderItemDto() {
    }
}
