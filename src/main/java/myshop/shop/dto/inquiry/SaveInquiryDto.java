package myshop.shop.dto.inquiry;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import myshop.shop.entity.inquiry.InquiryCategory;

@Getter @Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveInquiryDto {
    private Long itemNo;
    private Long orderItemNo;
    private Long memberNo;
    private String optionName;
    private InquiryCategory inquiryCategory;
    private String title;
    private String content;

}
