package myshop.shop.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import myshop.shop.dto.cart.SaveCartDto;
import myshop.shop.dto.item.AddItemDto;
import myshop.shop.dto.item.AddItemOptionDto;
import myshop.shop.dto.order.AddOrderDto;
import myshop.shop.dto.order.AddOrderItemDto;
import myshop.shop.entity.Address;
import myshop.shop.entity.Seller;
import myshop.shop.entity.item.ItemCategory;
import myshop.shop.entity.item.ItemStatus;
import myshop.shop.entity.member.Gender;
import myshop.shop.entity.member.Member;
import myshop.shop.entity.member.MemberLevel;
import myshop.shop.repository.address.AddressRepository;
import myshop.shop.repository.member.MemberRepository;
import myshop.shop.repository.seller.SellerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class DeliveryServiceTest {

    @Autowired EntityManager em;
    @Autowired PasswordEncoder passwordEncoder;
    @Autowired MemberRepository memberRepository;
    @Autowired AddressRepository addressRepository;
    @Autowired SellerRepository sellerRepository;
    @Autowired ItemService itemService;
    @Autowired CartService cartService;
    @Autowired OrderService orderService;

    @Autowired DeliveryService deliveryService;

    @BeforeEach
    public void init() {
        /**
         * 회원 저장
         */
        for(int i=1; i<5; i++) {
            String testPasswordEncode = passwordEncoder.encode("password" + i);
            Member member = new Member("id" + i, "email" + i, testPasswordEncode, "name" + i,
                    "telecom" + i, "010-0000-000" + i, Gender.MAN, MemberLevel.normal);
            memberRepository.save(member);
        }
        String memberPassword = passwordEncoder.encode("test");
        Member admin = new Member("test", "kkjjoo1212@naver.com", memberPassword,
                "테스트아이디", "KT", "010-4710-6305", Gender.MAN, MemberLevel.vip);
        memberRepository.save(admin);
        em.flush();
        em.clear();


        /**
         * 주소 저장
         */
        Member findMember = memberRepository.findById("test").orElse(null);
        Address mainAddress = new Address(findMember, "테스트 메인주소", "메인수령인",
                "010-1234-1234", "12345", "인천광역시 서구",
                "A아파트", true);
        addressRepository.save(mainAddress);

        for (int i=1; i<5; i++) {
            addressRepository.save(new Address(findMember, "테스트 서브주소" + i, "수령인" + i,
                    "010-0000-000" + i, "0000" + i, "도로명" + i,
                    "아파트" + i, false));
        }


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
        itemService.saveItem(new AddItemDto(sellerNo, "상품테스트1", ItemCategory.상의, 27000, 48, 10,
                List.of(
                        new AddItemOptionDto("검정색", 0, 4),
                        new AddItemOptionDto("나이키", 3000, 9),
                        new AddItemOptionDto("흰색", 1000, 15),
                        new AddItemOptionDto("로고", 3000, 20)
                ), null, "/shop_image/8ea0eafb-b1a7-492c-b574-654946184243.jpg", null,
                List.of(
                        "/shop_image/0d57c72e-ad5c-463d-b404-93b58fc020e7.png",
                        "/shop_image/00e766c7-b5b7-46bb-8b9b-50bb5079668b.png",
                        "/shop_image/1c7e2d70-4ef2-4fd9-862e-40226846215c.png"
                ), "상품옵션있음, 추가이미지있음", ItemStatus.판매중 ,true));

        itemService.saveItem(new AddItemDto(sellerNo, "상품테스트2(품절)", ItemCategory.바지, 50000, 0, 0,
                List.of(
                        new AddItemOptionDto("면바지", 0, 0),
                        new AddItemOptionDto("청바지", 10000, 0)
                ), null, "/shop_image/1736f6e5-e78c-4f29-96d4-621fbfd034d0.png", null,
                List.of(
                        "/shop_image/97297b82-81cd-4de7-ba70-bd4e467716df.png"
                ), "판매완료", ItemStatus.품절,true));


        List<AddItemOptionDto> emptyAddItemOptionDtoList = new ArrayList<>();
        itemService.saveItem(new AddItemDto(sellerNo, "상품테스트3(옵션X)", ItemCategory.아우터, 250000, 20, 0,
                emptyAddItemOptionDtoList, null, "/shop_image/30537136-0d60-450e-8544-2f9eda4e4800.png", null,
                List.of(
                        "/shop_image/b3ba7699-d546-4075-84a9-9b6888049a0c.png"
                ), "상품옵션없음, 추가이미지있음", ItemStatus.판매중, true));


        List<String> emptySubImageList = new ArrayList<>();
        itemService.saveItem(new AddItemDto(sellerNo, "상품테스트4(추가이미지X)", ItemCategory.신발, 150000, 10, 0,
                List.of(
                        new AddItemOptionDto("250", 0, 10),
                        new AddItemOptionDto("260", 10000, 0)
                ), null, "/shop_image/b3ecf6ae-140e-4950-81bf-74adce043239.png", null,
                emptySubImageList, "상품옵션있음, 추가이미지없음", ItemStatus.판매중,true));


        for (int i=0; i<50; i++) {
            itemService.saveItem(new AddItemDto(sellerNo, "페이징테스트" + i, ItemCategory.신발, i*1000, i, i,
                    emptyAddItemOptionDtoList, null, "/shop_image/c9cf742d-c0b0-4b8d-9253-cc32823d36db.png", null,
                    emptySubImageList, "상품옵션없음, 추가이미지없음", true));
        }

        /**
         * 장바구니 데이터
         */
        cartService.saveCart(new SaveCartDto(1L, 5L, 1L, 1));
        cartService.saveCart(new SaveCartDto(1L, 5L, 4L, 2));
        cartService.saveCart(new SaveCartDto(3L, 5L, null, 6));
        cartService.saveCart(new SaveCartDto(4L, 5L, 7L, 1));

        em.flush();
        em.clear();

        /**
         * 주문 목록 데이터
         */
        List<AddOrderItemDto> addOrderItemDtoList1 = new ArrayList<>();
        addOrderItemDtoList1.add(new AddOrderItemDto(1L, 1L, 1L, 1, null, BigDecimal.valueOf(24300),
                "/shop_image/8ea0eafb-b1a7-492c-b574-654946184243.jpg", "상품테스트1", "검정색"));
        addOrderItemDtoList1.add(new AddOrderItemDto(3L, null, 3L, 6, null, BigDecimal.valueOf(1500000),
                "/shop_image/30537136-0d60-450e-8544-2f9eda4e4800.png", "상품테스트3(옵션X)", ""));
        orderService.saveOrder(5L, new AddOrderDto("메인수령인", "010-1234-1234", "12345", "인천광역시 서구",
                "A아파트", "배송 전 미리 연락 부탁드립니다.", addOrderItemDtoList1, BigDecimal.valueOf(1524300),
                0, BigDecimal.valueOf(1524300)));

        List<AddOrderItemDto> addOrderItemDtoList2 = new ArrayList<>();
        addOrderItemDtoList2.add(new AddOrderItemDto(4L, 7L, 4L, 1, null, BigDecimal.valueOf(150000),
                "/shop_image/b3ecf6ae-140e-4950-81bf-74adce043239.png", "상품테스트4(추가이미지X)", "250"));
        orderService.saveOrder(5L, new AddOrderDto("메인수령인", "010-1234-1234", "12345", "인천광역시 서구",
                "A아파트", "배송 전 미리 연락 부탁드립니다.", addOrderItemDtoList2, BigDecimal.valueOf(150000),
                0, BigDecimal.valueOf(150000)));

        List<AddOrderItemDto> addOrderItemDtoList3 = new ArrayList<>();
        addOrderItemDtoList3.add(new AddOrderItemDto(1L, 1L, 1L, 1, null, BigDecimal.valueOf(24300),
                "/shop_image/8ea0eafb-b1a7-492c-b574-654946184243.jpg", "상품테스트1", "검정색"));
        orderService.saveOrder(5L, new AddOrderDto("메인수령인", "010-1234-1234", "12345", "인천광역시 서구",
                "A아파트", "부재 시 문 앞에 놓아주세요.", addOrderItemDtoList3, BigDecimal.valueOf(24300),
                3000, BigDecimal.valueOf(27300)));

        em.flush();
        em.clear();
    }

    @Test
    public void findAllDeliveryTest() throws Exception {
        //given



    }



}