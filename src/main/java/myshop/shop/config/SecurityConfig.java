package myshop.shop.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * 암호화, 복호화
     * 구현체: BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http){
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/**").permitAll()  // 해당하는 페이지들을 모두 허용한다.
                )
/*                .logout(logout -> logout
                    .logoutUrl("/logout") // 로그아웃 처리 URL
                    .logoutSuccessUrl("/") // 로그아웃 성공 후 이동할 URL
                    .invalidateHttpSession(true) // 세션 무효화
                    .deleteCookies("JSESSIONID") // 쿠키 삭제
                    .permitAll())*/
                .logout(logout -> logout.disable())

                .csrf(csrf -> csrf.disable());      // CSRF 보호를 끔, POST요청 특정토큰 필요없이 사용 가능
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("password")
                        .roles("USER")
                        .build();

        return new InMemoryUserDetailsManager(user);
    }
}
