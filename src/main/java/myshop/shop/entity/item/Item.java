package myshop.shop.entity.item;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import myshop.shop.entity.BaseDateEntity;
import myshop.shop.entity.Cart;
import myshop.shop.entity.orderItem.OrderItem;
import myshop.shop.entity.Seller;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@SequenceGenerator(name = "ITEM_SEQ", sequenceName = "ITEM_SEQ", initialValue = 1, allocationSize = 1)
public class Item extends BaseDateEntity {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ITEM_SEQ")
    @Column(name = "ITEM_NO")
    private Long no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SELLER_NO")
    private Seller seller;

    private String name;
    @Enumerated(EnumType.STRING)
    private ItemCategory itemCategory;
    private BigDecimal originalPrice;

    @Version
    private Long version;       // 동시성 제어

    private int totalStock;
    private BigDecimal discountPer;
    private String content;

    @Enumerated(EnumType.STRING)
    private ItemStatus itemStatus;

    private Long viewCount;

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("sortOrder ASC")
    private List<ItemImage> itemImages = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemOption> itemOptions = new ArrayList<>();

    @OneToMany(mappedBy = "item")
    private List<OrderItem> orderItemList = new ArrayList<>();

    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Cart> cartList = new ArrayList<>();

    public Item() {
    }

    // viewCount가 null이면 0으로 초기화 (테스트에서는 viewCount 주입 가능)
    @Builder
    public Item(Seller seller, String name, ItemCategory itemCategory, BigDecimal originalPrice, int totalStock, BigDecimal discountPer, String content, ItemStatus itemStatus, Long viewCount) {
        this.seller = seller;
        this.name = name;
        this.itemCategory = itemCategory;
        this.originalPrice = originalPrice;
        this.totalStock = totalStock;
        this.discountPer = discountPer;
        this.content = content;
        this.itemStatus = itemStatus;
        this.viewCount = viewCount != null ? viewCount : 0L;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updatePrice(int price) {
        this.originalPrice = BigDecimal.valueOf(price);
    }

    public void updatePrice(BigDecimal price) {
        this.originalPrice = price;
    }

    public void updateTotalStock(int totalStock) {
        this.totalStock = totalStock;
    }

    public void subTotalStock(int count) {
        this.totalStock -= count;
    }
    public void addTotalStock(int count) {
        this.totalStock += count;
    }

    public void updateDiscount(int discountPer) {
        this.discountPer = BigDecimal.valueOf(discountPer);
    }

    public void updateDiscount(BigDecimal discountPer) {
        this.discountPer = discountPer;
    }

    public void updateContent(String content) {
        this.content = content;
    }

    public void updateItemStatus(ItemStatus itemStatus) {
        this.itemStatus = itemStatus;
    }

}
