package myshop.shop.entity.item;

import jakarta.persistence.*;
import lombok.Getter;
import myshop.shop.entity.BaseDateEntity;

@Entity
@Getter
@SequenceGenerator(name = "ITEM_IMAGE_SEQ", sequenceName = "ITEM_IMAGE_SEQ", initialValue = 1, allocationSize = 1)
public class ItemImage extends BaseDateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ITEM_IMAGE_SEQ")
    @Column(name = "ITEM_IMAGE_NO")
    private Long no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_NO")
    private Item item;

    @Column(nullable = false, length = 500)
    private String imageUrl;    // S3 URL 저장

    private boolean isMain;     // 대표 이미지 여부
    private int sortOrder;      // 이미지 순서

    public ItemImage() {}

    public ItemImage(Item item, String imageUrl, boolean isMain, int sortOrder) {
        this.item = item;
        this.imageUrl = imageUrl;
        this.isMain = isMain;
        this.sortOrder = sortOrder;
    }


    //=============연관관계 편의 메서드=================
    public void addItem(Item item) {
        this.item = item;
        item.getItemImages().add(this);
    }
}