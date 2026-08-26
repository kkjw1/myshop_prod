package myshop.shop.controller.memberWeb;

import lombok.extern.slf4j.Slf4j;
import myshop.shop.controller.HomeController;
import myshop.shop.controller.HomeItemController;
import myshop.shop.dto.member.LoginCheckMemberDto;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@Slf4j
@ControllerAdvice(basePackages = "myshop.shop.controller.memberWeb", assignableTypes = {HomeController.class, HomeItemController.class})
public class LoginModelAdvice {

    // @ControllerAdvice의 @ModelAttribute를 쓰면 필터가 인증만 해두고, Model에 값 넣는 건 컨트롤러마다
    // 전역으로 자동 처리
    @ModelAttribute
    public void addLoginInfo(Model model) {
        log.info("ControllerAdvice start");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        boolean isLogin = authentication != null
                && authentication.isAuthenticated()
                && authentication.getPrincipal() instanceof LoginCheckMemberDto;

        model.addAttribute("isLogin", isLogin);

        if (authentication.getPrincipal() instanceof LoginCheckMemberDto loginCheckMemberDto) {
            model.addAttribute("loginCheckMemberDto", loginCheckMemberDto);
        }
    }
}
