package myshop.shop.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MailServiceTest {

    @Autowired MailService mailService;

    @Test
    public void mailSendTest() throws Exception {
        //given
        String authCode = mailService.authCodeCreate();
        String fromAddress = mailService.getFromAddress();

        System.out.println("authCode = " + authCode);
        System.out.println("fromAddress = " + fromAddress);

//        mailService.sendMail("kkjjoo1212@naver.com", authCode);
    }

}