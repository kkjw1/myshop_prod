package myshop.shop.repository.review;

import myshop.shop.controller.HomeItemController;
import myshop.shop.dto.review.ReviewScoreDto;
import myshop.shop.dto.review.SearchReviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ReviewRepositoryCustom {
    /**
     * 상품상세 -> 상품 리뷰
     */
    Page<HomeItemController.DetailItemReviewDto> findDetailItemReview(Pageable pageable, Long itemNo, SearchReviewDto searchReviewDto);


    /**
     * 상품상세 -> 리뷰 스코어별 개수 불러오기
     */
    ReviewScoreDto findReviewScore(Long itemNo);
}
