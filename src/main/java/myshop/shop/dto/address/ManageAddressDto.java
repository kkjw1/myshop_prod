package myshop.shop.dto.address;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Setter;
import lombok.ToString;
import myshop.shop.entity.Address;

@Getter @Setter
@ToString(of = {"addressName", "recipientName", "phoneNumber", "postcode", "roadAddress", "detailAddress", "isMain"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ManageAddressDto {
    private Long addressNo;
    private String addressName;
    private String recipientName;
    private String phoneNumber;
    private String postcode;
    private String roadAddress;
    private String detailAddress;
    private Boolean isMain;

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
