package myshop.shop.entity.delivery;

import jakarta.persistence.*;
import lombok.Getter;
import myshop.shop.entity.BaseDateEntity;
import myshop.shop.entity.orderItem.OrderItem;

@Entity
@Getter
@SequenceGenerator(name = "DELIVERY_SEQ", sequenceName = "DELIVERY_SEQ", initialValue = 1, allocationSize = 1)
public class Delivery extends BaseDateEntity {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DELIVERY_SEQ")
    @Column(name = "DELIVERY_NO")
    private Long no;

    private String courier;
    private String trackingNumber;

    @Enumerated(EnumType.STRING)
    private DeliveryStatus deliveryStatus;


    @OneToOne(mappedBy = "delivery", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private OrderItem orderItem;

    public Delivery() {
    }

    public Delivery(String courier, String trackingNumber, DeliveryStatus deliveryStatus) {
        this.courier = courier;
        this.trackingNumber = trackingNumber;
        this.deliveryStatus = deliveryStatus;
    }

    public void updateCourier(String courier) {
        this.courier = courier;
    }

    public void updateTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    public void updateDeliveryStatus(DeliveryStatus deliveryStatus) {
        this.deliveryStatus = deliveryStatus;
    }
}
