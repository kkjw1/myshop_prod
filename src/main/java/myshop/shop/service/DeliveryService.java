package myshop.shop.service;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myshop.shop.dto.delivery.BatchUpdateOrderDeliveryDto;
import myshop.shop.dto.delivery.OrderDeliveryDto;
import myshop.shop.dto.delivery.OrderItemDeliveryDto;
import myshop.shop.dto.delivery.UpdateDeliveryDto;
import myshop.shop.entity.delivery.Delivery;
import myshop.shop.entity.delivery.DeliveryStatus;
import myshop.shop.entity.order.Order;
import myshop.shop.entity.orderItem.OrderItem;
import myshop.shop.entity.orderItem.OrderItemStatus;
import myshop.shop.repository.delivery.DeliveryRepository;
import myshop.shop.repository.orderItem.OrderItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static myshop.shop.entity.orderItem.QOrderItem.orderItem;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class DeliveryService {
    private final DeliveryRepository deliveryRepository;
    private final EntityManager em;
    private final JPAQueryFactory queryFactory;
    private final OrderItemRepository orderItemRepository;

    /**
     * 주문 물품 가져오기
     * 판매자 센터 -> 주문/배송 관리 폼
     */
    public List<OrderDeliveryDto> findAllDelivery(Long sellerNo) {
        // delivery 값 가져오기
        Set<Long> orderNoSet = deliveryRepository.getOrderNoSet(sellerNo);
        return deliveryRepository.getOrderDeliveryList(orderNoSet);
    }


    /**
     * 주문 상품들 가져오기
     * 주문/배송 관리 폼 -> 관리
     */
    public List<OrderItemDeliveryDto> manageOrderDelivery(Long orderNo) {
        return deliveryRepository.getOrderItemDeliveryDtoList(orderNo);
    }


    /**
     * 상품 배송사항 변경
     * 관리 -> 변경사항 저장
     */
    public void updateOrderItemDelivery(List<UpdateDeliveryDto> updateDeliveryDtoList) {
        for (UpdateDeliveryDto updateOrderDeliveryDto : updateDeliveryDtoList) {
            Delivery delivery = queryFactory
                    .select(orderItem.delivery)
                    .from(orderItem)
                    .where(orderItem.no.eq(updateOrderDeliveryDto.getOrderItemNo()))
                    .fetchFirst();

            delivery.updateCourier(updateOrderDeliveryDto.getCourier());
            delivery.updateTrackingNumber(updateOrderDeliveryDto.getTrackingNumber());
            delivery.updateDeliveryStatus(updateOrderDeliveryDto.getDeliveryStatus());


            // orderItem의 상태가 상품준비중, 배송중, 배송완료 일때만 수정(나머지일때는 판매자가 취소, 반품 요청한 상태)
            OrderItem orderItem = orderItemRepository.findById(updateOrderDeliveryDto.getOrderItemNo()).orElse(null);
            if(orderItem.getOrderItemStatus() == OrderItemStatus.상품준비중 || orderItem.getOrderItemStatus() == OrderItemStatus.배송중
                    || orderItem.getOrderItemStatus() == OrderItemStatus.배송완료) {
                orderItem.updateOrderItemStatus(updateOrderDeliveryDto.getDeliveryStatus());
            }
            em.flush();
            em.clear();
        }
    }


    /**
     * 주문 상태 일괄 변경
     * 주문/배송 관리 -> 선택 주문 상태 처리
     */
    public void updateOrderStatus(BatchUpdateOrderDeliveryDto batchUpdateOrderDeliveryDto) {
        em.createQuery("update Order o set o.orderStatus = :orderStatus where o.no in :orderNoList")
                .setParameter("orderStatus", batchUpdateOrderDeliveryDto.getOrderStatus())
                .setParameter("orderNoList", batchUpdateOrderDeliveryDto.getOrderNoList())
                .executeUpdate();
    }
}
