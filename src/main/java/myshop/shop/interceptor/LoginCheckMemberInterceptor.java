package myshop.shop.interceptor;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myshop.shop.service.JwtService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;


@Slf4j
@Component
public class LoginCheckMemberInterceptor implements HandlerInterceptor {

    @Value("${jwt.secret}")
    String secret;
    private SecretKey secretKey;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        //@Controller @RequestMapping
        if (handler instanceof HandlerMethod) {
            log.info("preHandle start");
            String token = getToken(request, "accessToken");

            if (token == null) {
                log.info("token이 없습니다. 최초 로그인");
                String uri = request.getRequestURI();
                String queryString = request.getQueryString();
                String redirectURL = (queryString != null) ? uri + "?" + queryString : uri;
                response.sendRedirect("/login?redirectURL=" + redirectURL);
                return false;
            }

            try {
                Jwts.parser()
                        .verifyWith(secretKey)
                        .build()
                        .parseSignedClaims(token)
                        .getPayload();

                log.info("preHandle end");
                return true;

            } catch (JwtException e) {          // 토큰 만료,위조
                log.info("accessToken 만료, 위조");
                String uri = request.getRequestURI();
                String queryString = request.getQueryString();
                String redirectURL = (queryString != null) ? uri + "?" + queryString : uri;
                response.sendRedirect("/login?redirectURL=" + redirectURL);
                return false;
            }
        }
        //정적 리소스
        return true;
    }

    /**
     * 쿠키에서 token 가져옴
     */
    private String getToken(HttpServletRequest request, String tokenType) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (tokenType.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

}
