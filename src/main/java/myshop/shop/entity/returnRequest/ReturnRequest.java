package myshop.shop.entity.returnRequest;

import jakarta.persistence.*;
import lombok.Getter;
import myshop.shop.entity.BaseDateEntity;
import myshop.shop.entity.orderItem.OrderItem;
import myshop.shop.entity.member.Member;

import java.math.BigDecimal;

@Entity
@Getter
@SequenceGenerator(name = "RETURN_REQUEST_SEQ", sequenceName = "RETURN_REQUEST_SEQ", initialValue = 1, allocationSize = 1)
public class ReturnRequest extends BaseDateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "RETURN_REQUEST_SEQ")
    @Column(name = "RETURN_REQUEST_NO")
    private Long no;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ORDER_ITEM_NO", nullable = false)
    private OrderItem orderItem;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "MEMBER_NO", nullable = false)
    private Member member;

    private int count;

    @Enumerated(EnumType.STRING)
    private ReturnReasonCode returnReasonCode;

    private String reasonDetail;

    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private ReturnRequestStatus returnRequestStatus;

    private String decisionReason;

    public ReturnRequest() {
    }

    public ReturnRequest(OrderItem orderItem, Member member, int count, ReturnReasonCode returnReasonCode, String reasonDetail, BigDecimal price, ReturnRequestStatus returnRequestStatus) {
        this.orderItem = orderItem;
        this.member = member;
        this.count = count;
        this.returnReasonCode = returnReasonCode;
        this.reasonDetail = reasonDetail;
        this.price = price;
        this.returnRequestStatus = returnRequestStatus;
    }

    public void updateDecisionReason(String decisionReason) {
        this.decisionReason = decisionReason;
    }

    public void updateReturnRequestStatus(ReturnRequestStatus returnRequestStatus) {
        this.returnRequestStatus = returnRequestStatus;
    }
}
