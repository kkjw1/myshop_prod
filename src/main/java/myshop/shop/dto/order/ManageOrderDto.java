package myshop.shop.dto.order;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@ToString(of = {"orderNo", "orderTime", "manageOrderItemDtoList"})
public class ManageOrderDto {
    private Long orderNo;
    private LocalDateTime orderTime;
    private List<ManageOrderItemDto> manageOrderItemDtoList = new ArrayList<>();

    public ManageOrderDto() {
    }
}
