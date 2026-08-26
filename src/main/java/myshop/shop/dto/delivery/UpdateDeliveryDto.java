package myshop.shop.dto.delivery;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import myshop.shop.entity.delivery.DeliveryStatus;

@Getter @Setter
@ToString(of = {"orderItemNo", "deliveryStatus", "courier", "trackingNumber"})
public class UpdateDeliveryDto {
    private Long orderItemNo;
    private DeliveryStatus deliveryStatus;
    private String courier;
    private String trackingNumber;

    public UpdateDeliveryDto() {
    }

    public UpdateDeliveryDto(Long orderItemNo, DeliveryStatus deliveryStatus, String courier, String trackingNumber) {
        this.orderItemNo = orderItemNo;
        this.deliveryStatus = deliveryStatus;
        this.courier = courier;
        this.trackingNumber = trackingNumber;
    }
}
