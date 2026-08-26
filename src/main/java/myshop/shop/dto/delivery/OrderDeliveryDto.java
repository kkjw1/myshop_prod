package myshop.shop.dto.delivery;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import myshop.shop.entity.order.OrderStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@ToString(of = {"orderStatus", "orderNo", "orderInfo", "totalPrice", "orderTime", "recipientName", "recipientPhone", "postcode", "roadAddress", "detailAddress", "deliveryRequest"})
public class OrderDeliveryDto {
    /**
     * 주문 상태
     * 주문번호
     * 상품 정보    (상품 이름에서 처리)
     * 총 결제 금액
     * 주문일자
     * 수취인
     * 연락처
     * 배송지 주소
     * 배송 요청사항
     */
    private OrderStatus orderStatus;
    private Long orderNo;
    private String orderInfo;
    private BigDecimal totalPrice;
    private LocalDateTime orderTime;
    private String recipientName;
    private String recipientPhone;
    private String postcode;
    private String roadAddress;
    private String detailAddress;
    private String deliveryRequest;

    public OrderDeliveryDto() {
    }
}
