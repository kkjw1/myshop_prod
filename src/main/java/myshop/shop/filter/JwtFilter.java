package myshop.shop.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.*;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myshop.shop.dto.member.LoginCheckMemberDto;
import myshop.shop.service.JwtService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.Duration;
import java.util.List;

import static myshop.shop.entity.member.QMember.member;
import static myshop.shop.service.JwtService.accessCookieValidity;

@RequiredArgsConstructor
@Slf4j
public class JwtFilter implements Filter {
    private final JwtService jwtService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;
        String requestURI = request.getRequestURI();

        // 정적 리소스 요청 검사
        if (isExcludedUrl(requestURI)) {
            chain.doFilter(request, response);
            return;
        }

        String token = getToken(request, "accessToken");

        if (token != null) {
            try {
                LoginCheckMemberDto loginCheckMemberDto = jwtService.getMember(token);

                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        loginCheckMemberDto, null, List.of(new SimpleGrantedAuthority("MEMBER_" + loginCheckMemberDto.getId()))
                );
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("accessToken 인증 완료, url={}, loginCheckMemberDto={}", request.getRequestURI(), loginCheckMemberDto);

            } catch (ExpiredJwtException e) {       // 토큰 만료
                String refreshToken = getToken(request, "refreshToken");

                if (refreshToken != null && jwtService.validToken(refreshToken)) {
                    // Refresh Token이 유효하다면 즉시 새 Access Token 발급
                    LoginCheckMemberDto loginCheckMemberDto = jwtService.getMember(refreshToken);

                    String newAccessToken = jwtService.createAccessToken(loginCheckMemberDto);

                    ResponseCookie newAccessCookie = ResponseCookie
                            .from("accessToken", newAccessToken)
                            .httpOnly(true)
                            .secure(false)
                            .path("/")
                            .maxAge(Duration.ofMillis(accessCookieValidity))
                            .sameSite("Strict")
                            .build();

                    response.addHeader(HttpHeaders.SET_COOKIE, newAccessCookie.toString());

                    Authentication authentication = new UsernamePasswordAuthenticationToken(
                            loginCheckMemberDto, null, List.of(new SimpleGrantedAuthority("MEMBER_" + loginCheckMemberDto.getId()))
                    );
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    log.info("accessToken 재발급 완료, url={}, loginCheckMemberDto={}", request.getRequestURI(), loginCheckMemberDto);
                } else {
                    log.info("accessToken 만료, refreshToken 만료");
/*                    response.sendRedirect("/");
                    return;*/
//                    chain.doFilter(request, response);
                }
            } catch (JwtException e) {  // 토큰 위조
                log.error("accessToken 위조 에러", e);

                ResponseCookie deleteAccess = ResponseCookie.from("accessToken", "")
                        .httpOnly(true)
                        .secure(false)
                        .path("/")
                        .maxAge(0)
                        .sameSite("Strict")
                        .build();
                response.addHeader(HttpHeaders.SET_COOKIE, deleteAccess.toString());
                SecurityContextHolder.clearContext();
            }
        }

        chain.doFilter(request, response);
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


    /**
     * 제외할 URL 체크
     */
    private boolean isExcludedUrl(String uri) {
        // 특정 정적 리소스 폴더 경로 제외
/*        if (uri.startsWith("/css/") ||
                uri.startsWith("/js/") ||
                uri.startsWith("/images/") ||
                uri.startsWith("/shop_image/") ||
                uri.startsWith("/.well-known/")) {
            return true;
        }*/

        // 특정 정적 파일 확장자 및 파비콘 제외
        if (uri.endsWith(".css")
                || uri.endsWith(".js")
                || uri.endsWith(".jpg")
                || uri.endsWith(".jpeg")
                || uri.endsWith(".png")
                || uri.endsWith(".gif")
                || uri.endsWith(".ico")
                || uri.endsWith(".map")
//                || uri.endsWith(".json")
        ) {
            return true;
        }

        return false;
    }
}
