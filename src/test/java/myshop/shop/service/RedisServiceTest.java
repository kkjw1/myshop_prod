package myshop.shop.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RedisServiceTest {

    @Autowired RedisService redisService;
    @Test
    public void redisTest() throws Exception {
        //given
        redisService.saveData("AuthNum", "123456", 3L);
        String authNum = redisService.getData("AuthNum");

        assertThat(authNum).isEqualTo("123456");
    }

}