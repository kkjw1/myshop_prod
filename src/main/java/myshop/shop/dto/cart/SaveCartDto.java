package myshop.shop.dto.cart;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(of = {"itemNo", "memberNo", "itemOptionNo", "count"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveCartDto {
    private Long itemNo;
    private Long memberNo;
    private Long itemOptionNo;
    private int count;

}