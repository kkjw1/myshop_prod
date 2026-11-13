package myshop.shop.dto.order;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@ToString(of = {"orderNo", "orderTime", "manageOrderItemDtoList"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManageOrderDto {
    private Long orderNo;
    private LocalDateTime orderTime;
    @Builder.Default
    private List<ManageOrderItemDto> manageOrderItemDtoList = new ArrayList<>();

}
