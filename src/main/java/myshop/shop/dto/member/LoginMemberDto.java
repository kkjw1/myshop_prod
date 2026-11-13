package myshop.shop.dto.member;

import jakarta.validation.constraints.NotBlank;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(of = {"id", "password", "loginCheck"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginMemberDto {
    @NotBlank(message = "아이디가 공백입니다.")
    String id;
    @NotBlank(message = "비밀번호가 공백입니다.")
    String password;

    Boolean loginCheck;

}
