package myshop.shop.entity.inquiry;

import lombok.Getter;
import lombok.Setter;

@Getter
public enum InquiryCategory {
    RETURN("상품 반품 문의"),
    CANCEL("결제 취소 문의"),
    PRODUCT("상품/기타 문의");

    private final String description;

    InquiryCategory(String description) {
        this.description = description;
    }
}
