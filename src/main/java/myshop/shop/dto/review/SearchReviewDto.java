package myshop.shop.dto.review;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.AllArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SearchReviewDto {
    private String sortType;        // latest, best
    private Long score;
}
