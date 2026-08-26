package myshop.shop.entity;


import jakarta.persistence.*;
import lombok.Getter;
import myshop.shop.entity.item.Item;
import myshop.shop.entity.item.ItemImage;
import myshop.shop.entity.item.ItemOption;
import myshop.shop.entity.member.Member;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@SequenceGenerator(name = "CART_SEQ", sequenceName = "CART_SEQ", initialValue = 1, allocationSize = 1)
public class Cart extends BaseDateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CART_SEQ")
    @Column(name = "CART_NO")
    private Long no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MEMBER_NO")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_NO")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_OPTION_NO")
    private ItemOption itemOption;


    private int count;

    public Cart() {
    }

    public Cart(Member member, Item item, int count, ItemOption itemOption) {
        this.member = member;
        this.item = item;
        this.count = count;
        this.itemOption = itemOption;
    }

    public Cart(Member member, Item item, int count) {
        this.member = member;
        this.item = item;
        this.count = count;
    }

    public void updateCount(int count) {
        this.count = count;
    }

    public void addCount(int count) {
        this.count += count;
    }
}
