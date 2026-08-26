package myshop.shop.repository.delivery;

import myshop.shop.dto.delivery.OrderDeliveryDto;
import myshop.shop.dto.delivery.OrderItemDeliveryDto;

import java.util.List;
import java.util.Set;

public interface DeliveryRepositoryCustom {
    Set<Long> getOrderNoSet(Long sellerNo);
    List<OrderDeliveryDto> getOrderDeliveryList(Set<Long> orderNoList);
    /**
     * 주문/배송 관리 -> 관리 버튼
     */
    List<OrderItemDeliveryDto> getOrderItemDeliveryDtoList(Long orderNo);
}
