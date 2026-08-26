package myshop.shop.entity.item;

import lombok.Getter;

@Getter
public enum ItemStatus {
    판매중("bg-success"),
    품절("bg-danger"),
    판매중지("bg-secondary"),
    승인대기("bg-warning text-dark");

    private String classAppend;

    ItemStatus(String classAppend) {
        this.classAppend = classAppend;
    }
}
