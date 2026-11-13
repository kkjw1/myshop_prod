package myshop.shop.dto.delivery;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;
import lombok.ToString;
import myshop.shop.entity.order.OrderStatus;

import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@ToString(of = {"orderNoList", "orderStatus"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BatchUpdateOrderDeliveryDto {
    @Builder.Default
    private List<Long> orderNoList = new ArrayList<>();
    private OrderStatus orderStatus;

}
