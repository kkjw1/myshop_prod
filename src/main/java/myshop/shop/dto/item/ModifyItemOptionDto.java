package myshop.shop.dto.item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;
import lombok.ToString;
import myshop.shop.entity.item.ItemOption;

import java.math.BigDecimal;

@Getter @Setter
@ToString(of = {"name", "additionalPrice", "optionStock"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModifyItemOptionDto {
    private String name;
    private BigDecimal additionalPrice;
    private int optionStock;

    public ModifyItemOptionDto(ItemOption itemOption) {
        this.name = itemOption.getName();
        this.additionalPrice = itemOption.getAdditionalPrice();
        this.optionStock = itemOption.getOptionStock();
    }
}
