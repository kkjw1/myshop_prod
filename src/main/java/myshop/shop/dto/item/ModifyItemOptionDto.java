package myshop.shop.dto.item;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import myshop.shop.entity.item.ItemOption;

import java.math.BigDecimal;

@Getter @Setter
@ToString(of = {"name", "additionalPrice", "optionStock"})
public class ModifyItemOptionDto {
    private String name;
    private BigDecimal additionalPrice;
    private int optionStock;

    public ModifyItemOptionDto() {
    }

    public ModifyItemOptionDto(String name, int additionalPrice, int optionStock) {
        this.name = name;
        this.additionalPrice = BigDecimal.valueOf(additionalPrice);
        this.optionStock = optionStock;
    }
    public ModifyItemOptionDto(String name, BigDecimal additionalPrice, int optionStock) {
        this.name = name;
        this.additionalPrice = additionalPrice;
        this.optionStock = optionStock;
    }

    public ModifyItemOptionDto(ItemOption itemOption) {
        this.name = itemOption.getName();
        this.additionalPrice = itemOption.getAdditionalPrice();
        this.optionStock = itemOption.getOptionStock();
    }
}
