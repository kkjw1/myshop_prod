package myshop.shop.dto.review;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReviewScoreDto {
    private Long total;
    private Long score5;
    private Long score4;
    private Long score3;
    private Long score2;
    private Long score1;

}
