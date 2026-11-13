package myshop.shop.repository.seller;

import myshop.shop.entity.Seller;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SellerRepositoryTest {

    @Autowired SellerRepository sellerRepository;

    @Test
    public void saveTest() throws Exception {
        //given
        Seller seller = Seller.builder()
                .id("test")
                .password("test")
                .email("kkjjoo1212@naver.com")
                .name("판매자테스트")
                .phoneNumber("010-4710-6305")
                .companyName("테스트회사명")
                .companyPhone("테스트회사전화번호")
                .postcode("테스트회사우편번호")
                .roadAddress("테스트회사 도로명")
                .detailAddress("테스트회사 상세주소")
                .build();

        sellerRepository.save(seller);

    }
}