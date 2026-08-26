package myshop.shop.dto.review;

import lombok.Data;

@Data
public class SearchReviewDto {
    private String sortType;        // latest, best
    private Long score;
}
