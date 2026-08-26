package myshop.shop.entity.cancelRequest;

import jakarta.persistence.*;
import lombok.Getter;
import myshop.shop.entity.BaseDateEntity;
import myshop.shop.entity.orderItem.OrderItem;
import myshop.shop.entity.member.Member;

import java.math.BigDecimal;

@Entity
@Getter
@SequenceGenerator(name = "CANCEL_REQUEST_SEQ", sequenceName = "CANCEL_REQUEST_SEQ", initialValue = 1, allocationSize = 1)
public class CancelRequest extends BaseDateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CANCEL_REQUEST_SEQ")
    @Column(name = "CANCEL_REQUEST_NO")
    private Long no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ORDER_ITEM_NO")
    private OrderItem orderItem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_NO")
    private Member member;

    private int count;

    @Enumerated(EnumType.STRING)
    private CancelReasonCode cancelReasonCode;

    private String reasonDetail;

    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private CancelRequestStatus cancelRequestStatus;

    private String decisionReason;



    public CancelRequest() {
    }

    public CancelRequest(OrderItem orderItem, Member member, int count, CancelReasonCode cancelReasonCode, String reasonDetail, BigDecimal price, CancelRequestStatus cancelRequestStatus) {
        this.orderItem = orderItem;
        this.member = member;
        this.count = count;
        this.cancelReasonCode = cancelReasonCode;
        this.reasonDetail = reasonDetail;
        this.price = price;
        this.cancelRequestStatus = cancelRequestStatus;
    }

    public void updateDecisionReason(String decisionReason) {
        this.decisionReason = decisionReason;
    }

    public void updateCancelRequestStatus(CancelRequestStatus cancelRequestStatus) {
        this.cancelRequestStatus = cancelRequestStatus;
    }
}