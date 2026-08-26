package myshop.shop.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;
import myshop.shop.entity.member.Member;

@Entity
@Getter
@SequenceGenerator(name = "ADDRESS_SEQ", sequenceName = "ADDRESS_SEQ", initialValue = 1, allocationSize = 1)
@ToString(of = {"addressName", "recipientName", "phoneNumber", "postcode", "roadAddress", "detailAddress", "isMain"})
public class Address extends BaseDateEntity{
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ADDRESS_SEQ")
    @Column(name = "address_no")
    private Long no;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_no")
    private Member member;

    private String addressName;
    private String recipientName;
    private String phoneNumber;
    private String postcode;
    private String roadAddress;
    private String detailAddress;
    private Boolean isMain;


    public Address() {
    }

    // 회원가입
    public Address(Member member, String addressName, String postcode, String roadAddress, String detailAddress, String recipientName, String phoneNumber) {
        this.member = member;
        this.addressName = addressName;
        this.postcode = postcode;
        this.roadAddress = roadAddress;
        this.detailAddress = detailAddress;
        this.recipientName = recipientName;
        this.phoneNumber = phoneNumber;
    }

    public Address(Member member, String addressName, String recipientName, String phoneNumber, String postcode,
                   String roadAddress, String detailAddress, boolean isMain) {
        this.member = member;
        this.addressName = addressName;
        this.recipientName = recipientName;
        this.phoneNumber = phoneNumber;
        this.postcode = postcode;
        this.roadAddress = roadAddress;
        this.detailAddress = detailAddress;
        this.isMain = isMain;
    }

    public void updateAddressName(String addressName) {
        this.addressName = addressName;
    }

    public void updateRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public void updatePhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void updatePostcode(String postcode) {
        this.postcode = postcode;
    }

    public void updateRoadAddress(String roadAddress) {
        this.roadAddress = roadAddress;
    }

    public void updateDetailAddress(String detailAddress) {
        this.detailAddress = detailAddress;
    }

    public void updateIsMain(Boolean isMain) {
        this.isMain = isMain;
    }

    public void setMember(Member member) {
        this.member = member;
    }
}
