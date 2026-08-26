package myshop.shop.entity.order;

import jakarta.persistence.*;
import lombok.Getter;
import myshop.shop.entity.BaseDateEntity;
import myshop.shop.entity.orderItem.OrderItem;
import myshop.shop.entity.member.Member;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Table(name = "ORDERS")
@SequenceGenerator(name = "ORDERS_SEQ", sequenceName = "ORDERS_SEQ", initialValue = 1, allocationSize = 1)
public class Order extends BaseDateEntity {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ORDERS_SEQ")
    @Column(name = "ORDER_NO")
    private Long no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_NO")
    private Member member;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    private BigDecimal totalPrice;        // 총 결제 금액: orderItemList의 price합

    @OneToMany(mappedBy = "order", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<OrderItem> orderItemList = new ArrayList<>();


    private String recipientName;
    private String recipientPhone;
    private String postcode;
    private String roadAddress;
    private String detailAddress;
    private String deliveryRequest;
    private int deliveryFee;
    private String orderInfo;

    public Order() {
    }

    public Order(Member member, OrderStatus orderStatus, BigDecimal totalPrice, String recipientName,
                 String recipientPhone, String postcode, String roadAddress, String detailAddress, String deliveryRequest, int deliveryFee) {
        this.member = member;
        this.orderStatus = orderStatus;
        this.totalPrice = totalPrice;
        this.recipientName = recipientName;
        this.recipientPhone = recipientPhone;
        this.postcode = postcode;
        this.roadAddress = roadAddress;
        this.detailAddress = detailAddress;
        this.deliveryRequest = deliveryRequest;
        this.deliveryFee = deliveryFee;
    }

    public void setOrderInfo(String orderInfo) {
        this.orderInfo = orderInfo;
    }

    //==========편의 메서드 ============
    public void setTotalPrice(BigDecimal totalPrice) {
        for (OrderItem orderItem : orderItemList) {
            this.totalPrice.add(orderItem.getPrice());
        }
    }
}
