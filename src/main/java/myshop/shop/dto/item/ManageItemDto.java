package myshop.shop.dto.item;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import myshop.shop.entity.item.Item;
import myshop.shop.entity.item.ItemStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter
@ToString(of = {"itemNo", "mainImagePath", "name", "price", "totalStock", "itemStatus", "createdDate"})
public class ManageItemDto {
    private Long itemNo;
    private String mainImagePath;
    private String name;
    private BigDecimal price;
    private int totalStock;
    private ItemStatus itemStatus;
    private LocalDateTime createdDate;


    public ManageItemDto() {
    }

    public ManageItemDto(Item item) {
        this.itemNo = item.getNo();
        this.name = item.getName();
        this.price = item.getOriginalPrice();
        this.totalStock = item.getTotalStock();
        this.itemStatus = item.getItemStatus();
        this.createdDate = item.getCreatedDate();
    }

    public ManageItemDto(Long itemNo, String mainImagePath, String name, int price, int totalStock, ItemStatus itemStatus, LocalDateTime createdDate) {
        this.itemNo = itemNo;
        this.mainImagePath = mainImagePath;
        this.name = name;
        this.price = BigDecimal.valueOf(price);
        this.totalStock = totalStock;
        this.itemStatus = itemStatus;
        this.createdDate = createdDate;
    }
    public ManageItemDto(Long itemNo, String mainImagePath, String name, BigDecimal price, int totalStock, ItemStatus itemStatus, LocalDateTime createdDate) {
        this.itemNo = itemNo;
        this.mainImagePath = mainImagePath;
        this.name = name;
        this.price = price;
        this.totalStock = totalStock;
        this.itemStatus = itemStatus;
        this.createdDate = createdDate;
    }
}
