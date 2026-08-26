package myshop.shop.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
@Getter
public class MailService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String fromAddress;


    /**
     * 인증번호6자리
     */
    public String authCodeCreate() {
        int authCode = ThreadLocalRandom.current().nextInt(100000, 1000000);
        return String.valueOf(authCode);
    }

    public void sendMail(String to, String authCode) {
        MimeMessage mm = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mmh = new MimeMessageHelper(mm, true, "UTF-8");
            mmh.setFrom(fromAddress);
            mmh.setTo(to);
            mmh.setSubject("[MYSHOP] 이메일 인증 코드");
            mmh.setText(setMailForm(authCode, "mailForm"), true);
            javaMailSender.send(mm);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public String setMailForm(String authCode, String type) {
        Context context = new Context();
        context.setVariable("authCode", authCode);
        return templateEngine.process(type, context);
    }
}
