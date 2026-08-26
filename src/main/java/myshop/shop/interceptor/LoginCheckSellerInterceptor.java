package myshop.shop.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import static myshop.shop.controller.memberWeb.MemberController.SessionConst.LOGIN_SELLER;

public class LoginCheckSellerInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //@Controller @RequestMapping
        if (handler instanceof HandlerMethod) {
            HttpSession session = request.getSession(false);
            if (session == null || session.getAttribute(LOGIN_SELLER) == null) {

                String uri = request.getRequestURI();
                String queryString = request.getQueryString();
                String redirectURL = (queryString != null) ? uri + "?" + queryString : uri;
                response.sendRedirect("/seller/login?redirectURL=" + redirectURL);
                return false;
            }
        }

        //정적 리소스
        return true;
    }
}
