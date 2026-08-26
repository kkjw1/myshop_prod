package myshop.shop.entity.member;

import lombok.Getter;

@Getter
public enum Gender {
    MAN("남성"), WOMAN("여성"), NONE("선택안함");

    private final String val;
    Gender(String val) {
        this.val = val;
    }
}