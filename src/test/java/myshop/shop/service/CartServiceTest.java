package myshop.shop.service;

import jakarta.persistence.EntityManager;
import myshop.shop.controller.memberWeb.CartController;
import myshop.shop.dto.cart.ManageCartDto;
import myshop.shop.dto.cart.SaveCartDto;
import myshop.shop.dto.item.AddItemDto;
import myshop.shop.dto.item.AddItemOptionDto;
import myshop.shop.entity.Cart;
import myshop.shop.entity.Seller;
import myshop.shop.entity.item.ItemCategory;
import myshop.shop.entity.item.ItemStatus;
import myshop.shop.entity.member.Gender;
import myshop.shop.entity.member.Member;
import myshop.shop.entity.member.MemberLevel;
import myshop.shop.repository.Item.ItemOptionRepository;
import myshop.shop.repository.Item.ItemRepository;
import myshop.shop.repository.cart.CartRepository;
import myshop.shop.repository.member.MemberRepository;
import myshop.shop.repository.seller.SellerRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.*;


@SpringBootTest
@Transactional
class CartServiceTest {

    @Autowired CartService cartService;
    @Autowired ItemService itemService;
    @Autowired ItemRepository itemRepository;
    @Autowired SellerRepository sellerRepository;
    @Autowired ItemOptionRepository itemOptionRepository;
    @Autowired PasswordEncoder passwordEncoder;
    @Autowired EntityManager em;
    @Autowired MemberRepository memberRepository;
    @Autowired
    private CartRepository cartRepository;

    @BeforeEach
    @Commit
    public void init() {

        String memberPassword = passwordEncoder.encode("test");
        Member admin = new Member("test", "kkjjoo1212@naver.com", memberPassword,
                "테스트아이디", "KT", "010-4710-6305", Gender.MAN, MemberLevel.vip);
        memberRepository.save(admin);

        /**
         * 판매자 저장
         */
        String sellerPassword = passwordEncoder.encode("test");
        Seller seller = new Seller("test", sellerPassword, "kkjjoo1212@naver.com", "판매자테스트", "010-4710-6305", "테스트회사명",
                "테스트회사전화번호", "테스트회사우편번호", "테스트회사 도로명", "테스트회사 상세주소");
        sellerRepository.save(seller);


        /**
         * 상품 저장
         */
        Long sellerNo = sellerRepository.findById("test").orElse(null).getNo();
        itemService.saveItemForTest(new AddItemDto(sellerNo, "상품테스트1", ItemCategory.상의, 30000, 40, 10,
                List.of(
                        new AddItemOptionDto("검정색", 0, 10),
                        new AddItemOptionDto("나이키", 3000, 10),
                        new AddItemOptionDto("흰색", 1000, 10),
                        new AddItemOptionDto("로고", 3000, 10)
                ), null, "/shop_image/8ea0eafb-b1a7-492c-b574-654946184243.png", null,
                List.of(
                        "/shop_image/0d57c72e-ad5c-463d-b404-93b58fc020e7.png",
                        "/shop_image/00e766c7-b5b7-46bb-8b9b-50bb5079668b.png",
                        "/shop_image/1c7e2d70-4ef2-4fd9-862e-40226846215c.png"
                ), "상품옵션있음, 추가이미지있음", ItemStatus.판매중, true, 10L));

/*        List<AddItemOptionDto> emptyAddItemOptionDtoList = new ArrayList<>();
        itemService.saveItem(new AddItemDto(sellerNo, "상품테스트3(옵션X)", ItemCategory.아우터, 250000, 20, 0,
                emptyAddItemOptionDtoList, null, "/shop_image/30537136-0d60-450e-8544-2f9eda4e4800.png", null,
                List.of(
                        "/shop_image/b3ba7699-d546-4075-84a9-9b6888049a0c.png"
                ), "상품옵션없음, 추가이미지있음", ItemStatus.판매중, true));*/

        em.flush();
        em.clear();

    }
    
    @Test
    @DisplayName("장바구니 처음 저장")
    public void saveCartTest() throws Exception {
        //given
        SaveCartDto saveCartDto = new SaveCartDto(1L, 1L, 1L, 5);

        //when
        cartService.saveCart(saveCartDto);
        em.flush();
        em.clear();

        //then
        Cart cart = cartRepository.findById(1L).orElse(null);
        assertThat(cart.getCount()).isEqualTo(5);
    }

    @Test
    @DisplayName("같은 물품 장바구니에 저장")
    public void saveCartTest2() throws Exception {
        //given
        SaveCartDto saveCartDto = new SaveCartDto(1L, 1L, 1L,5);
        cartService.saveCart(saveCartDto);
        em.flush();
        em.clear();

        //when
        SaveCartDto saveCartDto2 = new SaveCartDto(1L, 1L, 1L, 4);
        cartService.saveCart(saveCartDto2);

        //then
        Cart cart = cartRepository.findById(1L).orElse(null);
        assertThat(cart.getCount()).isEqualTo(9);
    }
    
    @Test
    @DisplayName("다른 물품 장바구니에 저장")
    public void saveCartTest3() throws Exception {
        //given
        SaveCartDto saveCartDto = new SaveCartDto(1L, 1L, 1L, 5);
        cartService.saveCart(saveCartDto);
        em.flush();
        em.clear();

        //when
        SaveCartDto saveCartDto2 = new SaveCartDto(1L, 1L, 2L, 4);
        cartService.saveCart(saveCartDto2);

        //then
        Member member = memberRepository.getReferenceById(1L);
        List<Cart> cartList = cartRepository.findByMember(member);
        assertThat(cartList.size()).isEqualTo(2);
        assertThat(cartList.get(0).getCount()).isEqualTo(5);
        assertThat(cartList.get(1).getCount()).isEqualTo(4);

    }
    

    @Test
    public void findAllCartTest() throws Exception {
        //given
        SaveCartDto saveCartDto = new SaveCartDto(1L, 1L, 1L, 5);
        cartService.saveCart(saveCartDto);
        SaveCartDto saveCartDto2 = new SaveCartDto(1L, 1L, 2L, 4);
        cartService.saveCart(saveCartDto2);
        SaveCartDto saveCartDto3 = new SaveCartDto(1L, 1L, 1L, 2);
        cartService.saveCart(saveCartDto3);
        em.flush();
        em.clear();


        //when
        List<ManageCartDto> allCart = cartService.findAllCart(1L);
        for (ManageCartDto manageCartDto : allCart) {
            System.out.println("manageCartDto = " + manageCartDto);
        }


    }

}