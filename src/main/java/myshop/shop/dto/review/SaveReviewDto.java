package myshop.shop.dto.review;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class SaveReviewDto {
    private Long itemNo;
    private Long orderItemNo;
    private Long memberNo;
    private Long score;
    private int count;
    private String optionName;
    private String content;

    public SaveReviewDto() {
    }
}
