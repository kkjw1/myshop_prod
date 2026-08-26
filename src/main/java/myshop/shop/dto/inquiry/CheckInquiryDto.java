package myshop.shop.dto.inquiry;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import myshop.shop.entity.inquiry.InquiryCategory;
import myshop.shop.entity.inquiry.InquiryStatus;

@Getter @Setter
@ToString
public class CheckInquiryDto {

    /**
     * 상품 문의 번호
     * 문의 유형
     * 상품 이름
     * 상품 옵션 이름
     * 문의 제목
     * 문의 내용
     * 문의 상태
     */
    private Long inquiryNo;
    private InquiryCategory inquiryCategory;
    private String itemName;
    private String optionName;
    private String title;
    private String content;
    private InquiryStatus inquiryStatus;
    private Long memberNo;

    /**
     * 문의 응답 내용
     */
    private String answerContent;

    public CheckInquiryDto() {
    }
}
