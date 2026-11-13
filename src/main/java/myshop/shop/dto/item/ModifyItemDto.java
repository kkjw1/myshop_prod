package myshop.shop.dto.item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;
import lombok.ToString;
import myshop.shop.entity.item.Item;
import myshop.shop.entity.item.ItemOption;
import myshop.shop.entity.item.ItemStatus;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter @Setter
@ToString(of = {"itemNo", "name", "price", "totalStock", "itemStatus", "discountPer", "modifyItemOptionDtoList",
        "mainImage", "mainImageUrl", "mainImageName", "subImages", "subImagesInfo", "content"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModifyItemDto {
    private Long itemNo;
    private String name;
    private BigDecimal price;

    private int totalStock;
    private ItemStatus itemStatus;
    private BigDecimal discountPer;

    @Builder.Default
    private List<ModifyItemOptionDto> modifyItemOptionDtoList = new ArrayList<>();

    private MultipartFile mainImage;
    private String mainImageUrl;
    private String mainImageName;

    @Builder.Default
    private List<MultipartFile> subImages = new ArrayList<>();
    @Builder.Default
    private List<Map<String, String>> subImagesInfo = new ArrayList<>();

    private String content;

    public ModifyItemDto(Item item, List<ItemOption> itemOptionList) {
        this();
        this.name = item.getName();
        this.price = item.getOriginalPrice();
        this.totalStock = item.getTotalStock();
        this.itemStatus = item.getItemStatus();
        this.discountPer = item.getDiscountPer();
        this.modifyItemOptionDtoList = itemOptionList.stream()
                .map(ModifyItemOptionDto::new)
                .toList();
        this.content = item.getContent();
    }
}
