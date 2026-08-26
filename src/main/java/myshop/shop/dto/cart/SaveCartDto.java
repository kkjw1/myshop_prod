package myshop.shop.dto.cart;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(of = {"itemNo", "memberNo", "itemOptionNo", "count"})
public class SaveCartDto {
    private Long itemNo;
    private Long memberNo;
    private Long itemOptionNo;
    private int count;

    public SaveCartDto() {
    }

    public SaveCartDto(Long itemNo, Long memberNo, int count) {
        this.itemNo = itemNo;
        this.memberNo = memberNo;
        this.count = count;
    }

    public SaveCartDto(Long itemNo, Long memberNo, Long itemOptionNo, int count) {
        this.itemNo = itemNo;
        this.memberNo = memberNo;
        this.itemOptionNo = itemOptionNo;
        this.count = count;
    }
}