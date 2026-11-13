package myshop.shop.dto.address;

import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import myshop.shop.entity.Address;

@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateAddressDto {
    private Long addressNo;
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

    public UpdateAddressDto(Address address) {
        this.addressNo = address.getNo();
        this.addressName = address.getAddressName();
        this.recipientName = address.getRecipientName();
        this.phoneNumber = address.getPhoneNumber();
        this.postcode = address.getPostcode();
        this.roadAddress = address.getRoadAddress();
        this.detailAddress = address.getDetailAddress();
    }

}
