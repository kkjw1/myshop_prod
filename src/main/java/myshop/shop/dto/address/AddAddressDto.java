package myshop.shop.dto.address;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import myshop.shop.entity.Address;

@Getter @Setter
public class AddAddressDto {

    private String addressName;
    @NotBlank(message = "수령인 이름이 비어있습니다.")
    private String recipientName;
    @NotBlank(message = "휴대폰 번호가 비어있습니다.")
    private String phoneNumber;
    @NotBlank(message = "주소가 비어있습니다.")
    private String postcode;
    @NotBlank(message = "주소가 비어있습니다.")
    private String roadAddress;
    private String detailAddress;

    private Boolean isMain;        // 기본 배송지 여부

    public AddAddressDto() {
    }

    public AddAddressDto(String addressName, String recipientName, String phoneNumber, String postcode, String roadAddress, String detailAddress, Boolean isMain) {
        this.addressName = addressName;
        this.recipientName = recipientName;
        this.phoneNumber = phoneNumber;
        this.postcode = postcode;
        this.roadAddress = roadAddress;
        this.detailAddress = detailAddress;
        this.isMain = isMain;
    }

    public AddAddressDto(Address address) {
        this.addressName = address.getAddressName();
        this.recipientName = address.getRecipientName();
        this.phoneNumber = address.getPhoneNumber();
        this.postcode = address.getPostcode();
        this.roadAddress = address.getRoadAddress();
        this.detailAddress = address.getDetailAddress();
        this.isMain = address.getIsMain();
    }

}
