package myshop.shop.dto.item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;
import lombok.ToString;
import myshop.shop.entity.item.ItemCategory;
import myshop.shop.entity.item.ItemOption;

import java.math.BigDecimal;
import java.util.*;

@Getter @Setter
@ToString(of = {"itemNo", "itemImageMap", "itemCategory", "name", "price", "discountedPrice", "discountPer", "totalStock", "detailItemOptionDtoList", "content"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetailItemDto {

    private Long itemNo;

    @Builder.Default
    private Map<Long, String> itemImageMap = new LinkedHashMap<>();

    private ItemCategory itemCategory;
    private String name;
    private BigDecimal price;
    private BigDecimal discountedPrice;         // 할인된 가격
    private BigDecimal discountPer;
    private int totalStock;

    @Builder.Default
    private List<DetailItemOptionDto> detailItemOptionDtoList = new ArrayList<>();

    private String content;

}
