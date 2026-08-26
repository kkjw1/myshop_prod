package myshop.shop.entity.cancelRequest;

import myshop.shop.entity.ReasonCategory;

public enum CancelReasonCode {
    CHANGED_MIND("단순 변심 (의사 취소)", ReasonCategory.고객귀책),
    WRONG_ORDER("주문 실수 (옵션/수량 잘못 선택)", ReasonCategory.고객귀책),
    DELIVERY_DELAY("배송 지연 우려", ReasonCategory.판매자귀책),
    PRICE_FALL("더 저렴한 상품 발견", ReasonCategory.판매자귀책),
    OTHER("기타", ReasonCategory.기타);

    private final String description;
    private final ReasonCategory reasonCategory;

    CancelReasonCode(String description, ReasonCategory reasonCategory) {
        this.description = description;
        this.reasonCategory = reasonCategory;
    }

    public String getDescription() {
        return description;
    }

    public ReasonCategory getReasonCategory() {
        return reasonCategory;
    }
}