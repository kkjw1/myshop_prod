package myshop.shop.entity.member;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;
import myshop.shop.entity.Address;
import myshop.shop.entity.BaseDateEntity;
import myshop.shop.entity.Cart;
import myshop.shop.entity.order.Order;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@SequenceGenerator(name = "MEMBER_SEQ", sequenceName = "MEMBER_SEQ", initialValue = 1, allocationSize = 1)
@ToString(of = {"id", "name", "email", "telecom", "phoneNumber", "gender", "memberLevel"})
public class Member extends BaseDateEntity {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "MEMBER_SEQ")
    @Column(name = "MEMBER_NO")
    private Long no;

    private String id;
    private String email;
    private String password;
    private String name;

    private String telecom;
    private String phoneNumber;


    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Enumerated(EnumType.STRING)
    private MemberLevel memberLevel;

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Address> addresses = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Order> orderList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<Cart> cartList = new ArrayList<>();

    public Member() {
    }

    public Member(String id, String email, String password, String name, String telecom, String phoneNumber, Gender gender, MemberLevel memberLevel) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.telecom = telecom;
        this.phoneNumber = phoneNumber;
        this.gender = gender;
        this.memberLevel = memberLevel;
    }

    public boolean passwordEquals(String password) {
        return this.password.equals(password);
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateName(String name) {
        this.name = name;
    }

    public void updateGender(Gender gender) {
        this.gender = gender;
    }

    public void updateTelecom(String telecom) {
        this.telecom = telecom;
    }

    public void updatePhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void updateMemberLevel(MemberLevel memberLevel) {
        this.memberLevel = memberLevel;
    }

    public void addAddresses(Address address) {
        addresses.add(address);
        address.setMember(this);
    }
}
