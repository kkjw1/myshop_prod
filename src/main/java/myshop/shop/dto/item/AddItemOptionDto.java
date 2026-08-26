package myshop.shop.dto.item;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter @Setter
@ToString(of = {"name", "additionalPrice", "optionStock"})
public class AddItemOptionDto {
    private String name;
    private BigDecimal additionalPrice;
    private int optionStock;

    public AddItemOptionDto() {
    }

    public AddItemOptionDto(String name, int additionalPrice, int optionStock) {
        this.name = name;
        this.additionalPrice = BigDecimal.valueOf(additionalPrice);
        this.optionStock = optionStock;
    }
    public AddItemOptionDto(String name, BigDecimal additionalPrice, int optionStock) {
        this.name = name;
        this.additionalPrice = additionalPrice;
        this.optionStock = optionStock;
    }
}