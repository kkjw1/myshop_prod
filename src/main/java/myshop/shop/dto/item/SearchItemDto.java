package myshop.shop.dto.item;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import myshop.shop.entity.item.ItemStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchItemDto {
    private Long sellerNo;
    private Long itemNo;
    private String name;
    private ItemStatus itemStatus;

}
