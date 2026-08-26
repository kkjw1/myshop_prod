package myshop.shop.repository.review;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import myshop.shop.controller.HomeItemController.DetailItemReviewDto;
import myshop.shop.dto.review.ReviewScoreDto;
import myshop.shop.dto.review.SearchReviewDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.io.Serializable;
import java.util.List;

import static myshop.shop.entity.member.QMember.member;
import static myshop.shop.entity.review.QReview.review;

@RequiredArgsConstructor
public class ReviewRepositoryImpl implements ReviewRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    @Override
    public Page<DetailItemReviewDto> findDetailItemReview(Pageable pageable, Long itemNo, SearchReviewDto searchReviewDto) {
        List<DetailItemReviewDto> content = queryFactory
                .select(Projections.fields(DetailItemReviewDto.class,
                        review.no.as("reviewNo"),
                        review.optionName,
                        review.count,
                        review.score,
                        review.content,
                        Expressions.stringTemplate(
                                "CASE WHEN LENGTH({0}) <= 3 THEN {0} ELSE CONCAT(LEFT({0}, 3), REPEAT('*', LENGTH({0}) - 3)) END",
                                member.id
                        ).as("maskedMemberId"),
                        member.name.as("memberName"),
                        review.goodCount
                ))
                .from(review)
                .leftJoin(member)
                .on(review.memberNo.eq(member.no))
                .where(review.item.no.eq(itemNo), scoreEq(searchReviewDto.getScore()))
                .orderBy(orderBy(searchReviewDto))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> count = queryFactory
                .select(review.count())
                .from(review)
                .leftJoin(member)
                .on(review.memberNo.eq(member.no))
                .where(review.item.no.eq(itemNo), scoreEq(searchReviewDto.getScore()));

        return PageableExecutionUtils.getPage(content, pageable, count::fetchOne);
    }

    private BooleanExpression scoreEq(Long score) {
        return score != null ? review.score.eq(score) : null;
    }
    private OrderSpecifier<? extends Serializable> orderBy(SearchReviewDto searchReviewDto) {
        return "best".equals(searchReviewDto.getSortType()) ? review.goodCount.desc() : review.createdDate.desc();
    }


    @Override
    public ReviewScoreDto findReviewScore(Long itemNo) {
        List<Tuple> scoreList = queryFactory
                .select(review.score, review.count())
                .from(review)
                .where(review.item.no.eq(itemNo))
                .groupBy(review.score)
                .fetch();

        ReviewScoreDto reviewScoreDto = new ReviewScoreDto();

        Long total = 0L;
        for (Tuple t : scoreList) {
            Long score = t.get(review.score);
            Long count = t.get(review.count());
            total += count;
            if (score == 5) {
                reviewScoreDto.setScore5(count);
            } else if (score == 4) {
                reviewScoreDto.setScore4(count);
            } else if (score == 3) {
                reviewScoreDto.setScore3(count);
            } else if (score == 2) {
                reviewScoreDto.setScore2(count);
            } else if (score == 1) {
                reviewScoreDto.setScore1(count);
            }
        }
        reviewScoreDto.setTotal(total);
        reviewScoreDto.setScore5(reviewScoreDto.getScore5() == null ? 0L : reviewScoreDto.getScore5());
        reviewScoreDto.setScore4(reviewScoreDto.getScore4() == null ? 0L : reviewScoreDto.getScore4());
        reviewScoreDto.setScore3(reviewScoreDto.getScore3() == null ? 0L : reviewScoreDto.getScore3());
        reviewScoreDto.setScore2(reviewScoreDto.getScore2() == null ? 0L : reviewScoreDto.getScore2());
        reviewScoreDto.setScore1(reviewScoreDto.getScore1() == null ? 0L : reviewScoreDto.getScore1());
        return reviewScoreDto;
    }
}
