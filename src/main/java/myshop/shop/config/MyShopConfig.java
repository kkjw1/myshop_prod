package myshop.shop.config;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.servlet.Filter;
import lombok.RequiredArgsConstructor;
import myshop.shop.filter.JwtFilter;
import myshop.shop.filter.LogbackFilter;
import myshop.shop.interceptor.LoginCheckMemberInterceptor;
import myshop.shop.interceptor.LoginCheckSellerInterceptor;
import myshop.shop.service.JwtService;
import myshop.shop.service.RedisService;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;

import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import tools.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import tools.jackson.databind.jsontype.DefaultBaseTypeLimitingValidator;
import tools.jackson.databind.jsontype.PolymorphicTypeValidator;

import java.util.Optional;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class MyShopConfig implements WebMvcConfigurer {

    @PersistenceContext
    private EntityManager em;

    private final LoginCheckMemberInterceptor loginCheckMemberInterceptor;

    /**
     * redis 설정
     */
    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration("localhost", 6379);
//        config.setPassword(password);   // 인증
//        config.setDatabase(database);   // DB 인덱스 (0~15)
        return new LettuceConnectionFactory(config);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory());

        // Key: String 직렬화
        template.setKeySerializer(new StringRedisSerializer());
        // Value: JSON 직렬화
        template.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        return template;
    }

    @Bean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory connectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        return container;
    }



    /**
     * 등록자, 수정자
     */
    @Bean
    public AuditorAware<String> auditorAware() {
        return () -> Optional.of(UUID.randomUUID().toString());
    }


    /**
     * Querydsl
     */
    @Bean
    public JPAQueryFactory jpaQueryFactory() {
        return new JPAQueryFactory(em);
    }


    /**
     * 필터 설정
     */
    @Bean
    public FilterRegistrationBean LogFilter() {
        FilterRegistrationBean<Filter> filter = new FilterRegistrationBean<>();
        filter.setFilter(new LogbackFilter());
        filter.setOrder(1);
        filter.addUrlPatterns("/*");
        return filter;
    }
    @Bean
    public FilterRegistrationBean jwtFilter(JwtService jwtService) {
        FilterRegistrationBean<Filter> filter = new FilterRegistrationBean<>();
        filter.setFilter(new JwtFilter(jwtService));
        filter.setOrder(2);
        filter.addUrlPatterns("/*");
        return filter;
    }


    /**
     * 인터셉터 설정
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginCheckMemberInterceptor)
                .order(1)
                .addPathPatterns("/myPage/**");

        registry.addInterceptor(new LoginCheckSellerInterceptor())
                .order(2)
                .addPathPatterns("/seller/**")
                .excludePathPatterns("/seller/login");
    }


    /**
     * 파일 접근 설정
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 브라우저에서 /shop_image/** 로 요청하면 실제 로컬 폴더에서 찾음
        registry.addResourceHandler("/shop_image/**")
                .addResourceLocations("file:///C:/project/shop_image/");
    }
}
