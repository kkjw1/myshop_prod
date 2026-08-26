package myshop.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import myshop.shop.entity.item.Item;

import java.util.ArrayList;
import java.util.List;


@Entity
@Getter
@SequenceGenerator(name = "SELLER_SEQ", sequenceName = "SELLER_SEQ", initialValue = 1, allocationSize = 1)
public class Seller extends BaseDateEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SELLER_SEQ")
    @Column(name = "SELLER_NO")
    private Long no;

    private String id;
    private String password;
    private String email;
    private String name;
    private String phoneNumber;

    private String companyName;
    private String companyPhone;

    private String postcode;
    private String roadAddress;
    private String detailAddress;

    @OneToMany(mappedBy = "seller")
    private List<Item> items = new ArrayList<>();

    public Seller() {
    }

    public Seller(String id, String password, String email, String name, String phoneNumber, String companyName, String companyPhone, String postcode, String roadAddress, String detailAddress) {
        this.id = id;
        this.password = password;
        this.email = email;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.companyName = companyName;
        this.companyPhone = companyPhone;
        this.postcode = postcode;
        this.roadAddress = roadAddress;
        this.detailAddress = detailAddress;
    }
}
