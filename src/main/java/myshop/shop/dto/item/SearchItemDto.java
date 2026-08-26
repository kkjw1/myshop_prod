package myshop.shop.dto.item;

import lombok.Data;
import myshop.shop.entity.item.ItemStatus;

@Data
public class SearchItemDto {
    private Long sellerNo;
    private Long itemNo;
    private String name;
    private ItemStatus itemStatus;

    public SearchItemDto() {
    }

    public SearchItemDto(Long sellerNo, Long itemNo, String name, ItemStatus itemStatus) {
        this.sellerNo = sellerNo;
        this.itemNo = itemNo;
        this.name = name;
        this.itemStatus = itemStatus;
    }

    public SearchItemDto(Long sellerNo) {
        this.sellerNo = sellerNo;
    }
}
