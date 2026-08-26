package myshop.shop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;

public class MessageCodeTest {
    MessageCodesResolver ms = new DefaultMessageCodesResolver();

    @Test
    @DisplayName("메시지 코드 생성")
    public void messageCode() {
        //resolveMessageCodes(에러코드, 클래스이름, 필드이름, 필드 타입)
        String[] messageCodes = ms.resolveMessageCodes("loginFail", "Member", "memberId", String.class);
        for (String messageCode : messageCodes) {
            System.out.println("messageCode = " + messageCode);
        }
    }
}
