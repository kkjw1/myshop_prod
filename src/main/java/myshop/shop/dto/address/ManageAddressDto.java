package myshop.shop.dto.address;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import myshop.shop.entity.Address;

@Getter @Setter
@ToString(of = {"addressName", "recipientName", "phoneNumber", "postcode", "roadAddress", "detailAddress", "isMain"})
public class ManageAddressDto {
    private Long addressNo;
    private String addressName;
    private String recipientName;
    private String phoneNumber;
    private String postcode;
    private String roadAddress;
    private String detailAddress;
    private Boolean isMain;

    public ManageAddressDto() {
    }

    public ManageAddressDto(Long addressNo, String addressName, String recipientName, String phoneNumber,
                            String postcode, String roadAddress, String detailAddress, Boolean isMain) {
        this.addressNo = addressNo;
        this.addressName = addressName;
        this.recipientName = recipientName;
        this.phoneNumber = phoneNumber;
        this.postcode = postcode;
        this.roadAddress = roadAddress;
        this.detailAddress = detailAddress;
        this.isMain = isMain;
    }

    public ManageAddressDto(Address address) {
        this.addressNo = address.getNo();
        this.addressName = address.getAddressName();
        this.recipientName = address.getRecipientName();
        this.phoneNumber = address.getPhoneNumber();
        this.postcode = address.getPostcode();
        this.roadAddress = address.getRoadAddress();
        this.detailAddress = address.getDetailAddress();
        this.isMain = address.getIsMain();
    }
}
