package myshop.shop.entity.refund;


import jakarta.persistence.*;
import lombok.Getter;
import myshop.shop.entity.BaseDateEntity;
import myshop.shop.entity.cancelRequest.CancelRequest;
import myshop.shop.entity.returnRequest.ReturnRequest;

import java.math.BigDecimal;

@Entity
@Getter
@SequenceGenerator(name = "REFUND_SEQ", sequenceName = "REFUND_SEQ", initialValue = 1, allocationSize = 1)
public class Refund extends BaseDateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REFUND_SEQ")
    @Column(name = "ITEM_NO")
    private Long no;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CANCEL_REQUEST_NO")
    private CancelRequest cancelRequest;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RETURN_REQUEST_NO")
    private ReturnRequest returnRequest;

    private BigDecimal deliveryFee;
    private BigDecimal totalPrice;
    private BigDecimal refundPrice;

    @Enumerated(EnumType.STRING)
    private RefundStatus refundStatus;

    public Refund() {
    }

    public Refund(CancelRequest cancelRequest, ReturnRequest returnRequest, BigDecimal deliveryFee, BigDecimal totalPrice, BigDecimal refundPrice, RefundStatus refundStatus) {
        this.cancelRequest = cancelRequest;
        this.returnRequest = returnRequest;
        this.deliveryFee = deliveryFee;
        this.totalPrice = totalPrice;
        this.refundPrice = refundPrice;
        this.refundStatus = refundStatus;
    }
}
