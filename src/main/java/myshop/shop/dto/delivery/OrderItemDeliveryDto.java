package myshop.shop.dto.delivery;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import myshop.shop.entity.delivery.DeliveryStatus;
import myshop.shop.entity.orderItem.OrderItemStatus;

import java.math.BigDecimal;

@Getter @Setter
@ToString(of = {"orderItemNo", "itemName", "count", "price", "deliveryStatus", "courier", "trackingNumber", "orderItemStatus"})
public class OrderItemDeliveryDto {
    /**
     * - 관리 쪽 -
     * {"id": "p2", "name": "기본 레이어드 반팔티 (화이트/M)", "count": 1, "price": "30,000원", "status": "배송 중", "courier": "CJ대한통운", "trackingNum": "6802142531"}
     */
    private Long orderItemNo;
    private String itemName;
    private int count;
    private BigDecimal price;
    private DeliveryStatus deliveryStatus;      // 배송상태
    private String courier;
    private String trackingNumber;
    private OrderItemStatus orderItemStatus;        // 상품주문상태

    public OrderItemDeliveryDto() {
    }
}
