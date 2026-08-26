package myshop.shop.entity.orderItem;

import jakarta.persistence.*;
import lombok.Getter;
import myshop.shop.entity.BaseDateEntity;
import myshop.shop.entity.delivery.Delivery;
import myshop.shop.entity.delivery.DeliveryStatus;
import myshop.shop.entity.item.Item;
import myshop.shop.entity.order.Order;

import java.math.BigDecimal;

@Entity
@Getter
@SequenceGenerator(name = "ORDER_ITEM_SEQ", sequenceName = "ORDER_ITEM_SEQ", initialValue = 1, allocationSize = 1)
public class OrderItem extends BaseDateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDER_ITEM_SEQ")
    @Column(name = "ORDER_ITEM_NO")
    private Long no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_NO")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_NO")
    private Item item;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "DELIVERY_NO")
    private Delivery delivery;

    private int count;
    private BigDecimal price;              // 가격 = (할인된가격 + 옵션가격) * 개수
    private String imageUrl;
    private String itemName;
    private String optionName;

    @Enumerated(EnumType.STRING)
    private OrderItemStatus orderItemStatus;

    private Boolean review;     // 리뷰 있으면 true

    public OrderItem() {
    }

    public OrderItem(Order order, Item item, Delivery delivery, int count, BigDecimal price, String imageUrl, String itemName, String optionName, OrderItemStatus orderItemStatus) {
        this.order = order;
        this.item = item;
        this.delivery = delivery;
        this.count = count;
        this.price = price;
        this.imageUrl = imageUrl;
        this.itemName = itemName;
        this.optionName = optionName;
        this.orderItemStatus = orderItemStatus;
        this.review = false;
    }
    public OrderItem(Order order, Item item, Delivery delivery, int count, int price, String imageUrl, String itemName, String optionName, OrderItemStatus orderItemStatus) {
        this.order = order;
        this.item = item;
        this.delivery = delivery;
        this.count = count;
        this.price = BigDecimal.valueOf(price);
        this.imageUrl = imageUrl;
        this.itemName = itemName;
        this.optionName = optionName;
        this.orderItemStatus = orderItemStatus;
        this.review = false;
    }

    //==========편의 메서드===========
    public void updateOrder(Order order) {
        this.order = order;
        order.getOrderItemList().add(this);
    }

    public void updateItem(Item item) {
        this.item = item;
        item.getOrderItemList().add(this);
    }

    public void updateReview(Boolean review) {
        this.review = review;
    }

    public void updateOrderItemStatus(OrderItemStatus orderItemStatus) {
        this.orderItemStatus = orderItemStatus;
    }

    public void updateOrderItemStatus(DeliveryStatus deliveryStatus) {
        if (deliveryStatus == DeliveryStatus.상품준비중) { this.orderItemStatus = OrderItemStatus.상품준비중; }
        else if (deliveryStatus == DeliveryStatus.배송중) { this.orderItemStatus = OrderItemStatus.배송중; }
        else if (deliveryStatus == DeliveryStatus.배송완료) { this.orderItemStatus = OrderItemStatus.배송완료; }
    }
}
