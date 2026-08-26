package myshop.shop.entity.item;

import jakarta.persistence.*;
import lombok.Getter;
import myshop.shop.entity.BaseDateEntity;
import myshop.shop.entity.Cart;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@SequenceGenerator(name = "ITEM_OPTION_SEQ", sequenceName = "ITEM_OPTION_SEQ", initialValue = 1, allocationSize = 1)
public class ItemOption extends BaseDateEntity {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ITEM_OPTION_SEQ")
    @Column(name = "ITEM_OPTION_NO")
    private Long no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_NO")
    private Item item;
    private String name;
    private BigDecimal additionalPrice;

    @Version
    private Long version;   // 동시성 제어

    private int optionStock;

    @OneToMany(mappedBy = "itemOption", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cart> cartList = new ArrayList<>();

    public ItemOption() {
    }

    public ItemOption(Item item, String name, int additionalPrice, int optionStock) {
        this.item = item;
        this.name = name;
        this.additionalPrice = BigDecimal.valueOf(additionalPrice);
        this.optionStock = optionStock;
    }
    public ItemOption(Item item, String name, BigDecimal additionalPrice, int optionStock) {
        this.item = item;
        this.name = name;
        this.additionalPrice = additionalPrice;
        this.optionStock = optionStock;
    }

    //==========연관관계 편의 메서드 ============
    public void addItem(Item item) {
        this.item = item;
        item.getItemOptions().add(this);
    }

    public void subOptionStock(int count) {
        this.optionStock -= count;
    }

    public void addOptionStock(int count) {
        this.optionStock += count;
    }
}
