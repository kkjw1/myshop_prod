package myshop.shop.dto.member;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
@ToString(of = {"id", "password"})
public class ResetPasswordMemberDto {
    private String id;

    @NotBlank(message = "비밀번호가 공백입니다.")
    @Size(min = 8, message = "비밀번호는 8자리 이상이여야 합니다.")
    private String password;

    public ResetPasswordMemberDto(String id) {
        this.id = id;
    }

    public ResetPasswordMemberDto() {
    }
}
