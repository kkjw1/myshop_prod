package myshop.shop.entity.review;

import jakarta.persistence.*;
import lombok.Getter;
import myshop.shop.entity.BaseDateEntity;
import myshop.shop.entity.item.Item;

@Entity
@Getter
@SequenceGenerator(name = "REVIEW_SEQ", sequenceName = "REVIEW_SEQ", initialValue = 1, allocationSize = 1)
public class Review extends BaseDateEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "REVIEW_SEQ")
    @Column(name = "REVIEW_NO")
    private Long no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ITEM_NO")
    private Item item;

    private Long memberNo;
    private String optionName;
    private int count;
    private Long score;
    private String content;
    private Long goodCount;

    public Review() {
    }

    public Review(Item item, Long memberNo, String optionName, int count, Long score, String content) {
        this.item = item;
        this.memberNo = memberNo;
        this.optionName = optionName;
        this.count = count;
        this.score = score;
        this.content = content;
        this.goodCount = 0L;
    }

    public void addGoodCount() {
        this.goodCount += 1;
    }

    public void subGoodCount() {
        if (this.goodCount > 0) {
            this.goodCount -= 1;
        }
    }
}
