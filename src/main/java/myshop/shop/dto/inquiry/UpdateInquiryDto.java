package myshop.shop.dto.inquiry;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString(of = {"inquiryNo", "answerContent"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateInquiryDto {
    private Long inquiryNo;
    private String answerContent;

}
