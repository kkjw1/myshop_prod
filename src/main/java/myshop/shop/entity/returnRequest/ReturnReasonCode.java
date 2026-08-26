package myshop.shop.entity.returnRequest;

import myshop.shop.entity.ReasonCategory;

public enum ReturnReasonCode {
    CHANGE_MIND("단순 변심 (색상/사이즈 등)", ReasonCategory.고객귀책),
    DEFECTIVE("상품 하자가 있음", ReasonCategory.판매자귀책),
    WRONG_DELIVERY("오배송 (다른 상품이 옴)", ReasonCategory.판매자귀책),
    DAMAGED("배송 중 상품이 파손됨", ReasonCategory.판매자귀책),
    OTHER("기타", ReasonCategory.기타);

    private final String description;
    private final ReasonCategory reasonCategory;

    ReturnReasonCode(String description, ReasonCategory reasonCategory) {
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
