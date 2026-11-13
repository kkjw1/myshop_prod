package myshop.shop.dto.item;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter @Setter
@ToString(of = {"name", "additionalPrice", "optionStock"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddItemOptionDto {
    private String name;
    private BigDecimal additionalPrice;
    private int optionStock;

}