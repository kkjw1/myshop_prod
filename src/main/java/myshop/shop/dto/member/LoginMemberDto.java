package myshop.shop.dto.member;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(of = {"id", "password", "loginCheck"})
public class LoginMemberDto {
    @NotBlank(message = "아이디가 공백입니다.")
    String id;
    @NotBlank(message = "비밀번호가 공백입니다.")
    String password;

    Boolean loginCheck;

    public LoginMemberDto() {
    }

    public LoginMemberDto(String id) {
        this.id = id;
    }

    public LoginMemberDto(String id, String password) {
        this.id = id;
        this.password = password;
    }
}
