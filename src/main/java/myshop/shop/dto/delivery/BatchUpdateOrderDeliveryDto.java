package myshop.shop.dto.delivery;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import myshop.shop.entity.order.OrderStatus;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@ToString(of = {"orderNoList", "orderStatus"})
public class BatchUpdateOrderDeliveryDto {
    private List<Long> orderNoList = new ArrayList<>();
    private OrderStatus orderStatus;

    public BatchUpdateOrderDeliveryDto() {
    }

    public BatchUpdateOrderDeliveryDto(List<Long> orderNoList, OrderStatus orderStatus) {
        this.orderNoList = orderNoList;
        this.orderStatus = orderStatus;
    }
}
