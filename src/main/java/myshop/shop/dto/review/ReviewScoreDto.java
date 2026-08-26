package myshop.shop.dto.review;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString
public class ReviewScoreDto {
    private Long total;
    private Long score5;
    private Long score4;
    private Long score3;
    private Long score2;
    private Long score1;

    public ReviewScoreDto() {
    }

    public ReviewScoreDto(Long total, Long score5, Long score4, Long score3, Long score2, Long score1) {
        this.total = total;
        this.score5 = score5;
        this.score4 = score4;
        this.score3 = score3;
        this.score2 = score2;
        this.score1 = score1;
    }
}
