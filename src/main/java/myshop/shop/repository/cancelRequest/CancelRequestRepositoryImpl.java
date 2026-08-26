package myshop.shop.repository.cancelRequest;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myshop.shop.dto.cancelRequest.ManageCancelReturnDto;
import myshop.shop.dto.cancelRequest.SellerManageRequestDto;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static myshop.shop.entity.member.QMember.member;
import static myshop.shop.entity.orderItem.QOrderItem.orderItem;
import static myshop.shop.entity.cancelRequest.QCancelRequest.cancelRequest;
import static myshop.shop.entity.item.QItem.item;
import static myshop.shop.entity.returnRequest.QReturnRequest.returnRequest;

@RequiredArgsConstructor
public class CancelRequestRepositoryImpl implements  CancelRequestRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<ManageCancelReturnDto> findCancelReturnList(Long memberNo) {
        List<ManageCancelReturnDto> manageCancelReturnDtoList = new ArrayList<>();

        List<ManageCancelReturnDto> cancelDto = queryFactory
                .select(Projections.fields(ManageCancelReturnDto.class,
                        orderItem.no.as("orderItemNo"),
                        item.no.as("itemNo"),
                        cancelRequest.price.as("price"),
                        cancelRequest.cancelRequestStatus.as("cancelRequestStatus"),
                        cancelRequest.cancelReasonCode.as("cancelReasonCode"),
                        cancelRequest.reasonDetail.as("reasonDetail"),
                        orderItem.itemName.as("itemName"),
                        orderItem.optionName.as("optionName"),
                        cancelRequest.createdDate.as("requestTime"),
                        cancelRequest.count,
                        orderItem.imageUrl
                ))
                .from(cancelRequest)
                .join(cancelRequest.orderItem, orderItem)
                .join(orderItem.item, item)
                .where(cancelRequest.member.no.eq(memberNo))
                .fetch();

        manageCancelReturnDtoList.addAll(cancelDto);

        List<ManageCancelReturnDto> returnDto = queryFactory
                .select(Projections.fields(ManageCancelReturnDto.class,
                        orderItem.no.as("orderItemNo"),
                        item.no.as("itemNo"),
                        returnRequest.price.as("price"),
                        returnRequest.returnRequestStatus.as("returnRequestStatus"),
                        returnRequest.returnReasonCode.as("returnReasonCode"),
                        returnRequest.reasonDetail.as("reasonDetail"),
                        orderItem.itemName.as("itemName"),
                        orderItem.optionName.as("optionName"),
                        returnRequest.createdDate.as("requestTime"),
                        returnRequest.count,
                        orderItem.imageUrl
                ))
                .from(returnRequest)
                .join(returnRequest.orderItem, orderItem)
                .join(orderItem.item, item)
                .where(returnRequest.member.no.eq(memberNo))
                .fetch();

        manageCancelReturnDtoList.addAll(returnDto);

        // 내림차순
        manageCancelReturnDtoList.sort(Comparator.comparing(ManageCancelReturnDto::getRequestTime).reversed());
        return manageCancelReturnDtoList;
    }

    @Override
    public List<SellerManageRequestDto> findSellerManageRequest(Long sellerNo) {

        List<SellerManageRequestDto> sellerManageRequestDtoList = new ArrayList<>();

        sellerManageRequestDtoList.addAll(queryFactory
                .select(Projections.fields(SellerManageRequestDto.class,
                        cancelRequest.no.as("no"),
                        member.no.as("memberNo"),
                        member.name.as("memberName"),
                        orderItem.itemName,
                        orderItem.optionName,
                        cancelRequest.cancelReasonCode,
                        cancelRequest.reasonDetail,
                        cancelRequest.price,
                        cancelRequest.cancelRequestStatus,
                        cancelRequest.decisionReason
                ))
                .from(cancelRequest)
                .leftJoin(cancelRequest.member, member)
                .leftJoin(cancelRequest.orderItem, orderItem)
                .leftJoin(orderItem.item, item)
                .where(item.seller.no.eq(sellerNo))
                .fetch());

        sellerManageRequestDtoList.addAll(queryFactory
                .select(Projections.fields(SellerManageRequestDto.class,
                        returnRequest.no.as("no"),
                        member.no.as("memberNo"),
                        member.name.as("memberName"),
                        orderItem.itemName,
                        orderItem.optionName,
                        returnRequest.returnReasonCode,
                        returnRequest.reasonDetail,
                        returnRequest.price,
                        returnRequest.returnRequestStatus,
                        returnRequest.decisionReason
                ))
                .from(returnRequest)
                .leftJoin(returnRequest.member, member)
                .leftJoin(returnRequest.orderItem, orderItem)
                .leftJoin(orderItem.item, item)
                .where(item.seller.no.eq(sellerNo))
                .fetch());

        return sellerManageRequestDtoList;
    }
}
