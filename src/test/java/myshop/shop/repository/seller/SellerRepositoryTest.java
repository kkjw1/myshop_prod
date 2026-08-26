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
        Seller seller = new Seller("test", "test", "kkjjoo1212@naver.com", "판매자테스트", "010-4710-6305", "테스트회사명",
                "테스트회사전화번호", "테스트회사우편번호", "테스트회사 도로명", "테스트회사 상세주소");

        sellerRepository.save(seller);

    }
}