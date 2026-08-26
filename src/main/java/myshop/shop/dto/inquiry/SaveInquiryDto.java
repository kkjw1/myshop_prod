package myshop.shop.dto.inquiry;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import myshop.shop.entity.inquiry.InquiryCategory;

@Getter @Setter
@ToString
public class SaveInquiryDto {
    private Long itemNo;
    private Long orderItemNo;
    private Long memberNo;
    private String optionName;
    private InquiryCategory inquiryCategory;
    private String title;
    private String content;

    public SaveInquiryDto() {
    }
}
