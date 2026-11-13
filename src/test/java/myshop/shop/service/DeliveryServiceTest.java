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
import java.util.Map;

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
            Member member = Member.builder()
                    .id("id" + i)
                    .email("email" + i)
                    .password(testPasswordEncode)
                    .name("name" + i)
                    .telecom("telecom" + i)
                    .phoneNumber("010-0000-000" + i)
                    .gender(Gender.MAN)
                    .memberLevel(MemberLevel.normal)
                    .build();
            memberRepository.save(member);
        }
        String memberPassword = passwordEncoder.encode("test");
        Member admin = Member.builder()
                .id("test")
                .email("kkjjoo1212@naver.com")
                .password(memberPassword)
                .name("테스트아이디")
                .telecom("KT")
                .phoneNumber("010-4710-6305")
                .gender(Gender.MAN)
                .memberLevel(MemberLevel.vip)
                .build();
        memberRepository.save(admin);
        em.flush();
        em.clear();


        /**
         * 주소 저장
         */
        Member findMember = memberRepository.findById("test").orElse(null);
        Address mainAddress = Address.builder()
                .member(findMember)
                .addressName("테스트 메인주소")
                .recipientName("메인수령인")
                .phoneNumber("010-1234-1234")
                .postcode("12345")
                .roadAddress("인천광역시 서구")
                .detailAddress("A아파트")
                .isMain(true)
                .build();
        addressRepository.save(mainAddress);

        for (int i=1; i<5; i++) {
            addressRepository.save(Address.builder()
                    .member(findMember)
                    .addressName("테스트 서브주소" + i)
                    .recipientName("수령인" + i)
                    .phoneNumber("010-0000-000" + i)
                    .postcode("0000" + i)
                    .roadAddress("도로명" + i)
                    .detailAddress("아파트" + i)
                    .isMain(false)
                    .build());
        }


        /**
         * 판매자 저장
         */
        String sellerPassword = passwordEncoder.encode("test");
        Seller seller = Seller.builder()
                .id("test")
                .password(sellerPassword)
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


        /**
         * 상품 저장
         */
        Long sellerNo = sellerRepository.findById("test").orElse(null).getNo();
        itemService.saveItem(AddItemDto.builder()
                .sellerNo(sellerNo)
                .name("상품테스트1")
                .itemCategory(ItemCategory.상의)
                .price(BigDecimal.valueOf(27000))
                .totalStock(48)
                .discountPer(BigDecimal.valueOf(10))
                .addItemOptionDtoList(List.of(
                        AddItemOptionDto.builder()
                                .name("검정색")
                                .additionalPrice(BigDecimal.valueOf(0))
                                .optionStock(4)
                                .build(),
                        AddItemOptionDto.builder()
                                .name("나이키")
                                .additionalPrice(BigDecimal.valueOf(3000))
                                .optionStock(9)
                                .build(),
                        AddItemOptionDto.builder()
                                .name("흰색")
                                .additionalPrice(BigDecimal.valueOf(1000))
                                .optionStock(15)
                                .build(),
                        AddItemOptionDto.builder()
                                .name("로고")
                                .additionalPrice(BigDecimal.valueOf(3000))
                                .optionStock(20)
                                .build()
                ))
                .mainImageInfo(Map.of("/shop_image/8ea0eafb-b1a7-492c-b574-654946184243.jpg", "8ea0eafb-b1a7-492c-b574-654946184243.jpg"))
                .subImagesInfo(List.of(
                        Map.of(
                                "/shop_image/0d57c72e-ad5c-463d-b404-93b58fc020e7.png",
                                "0d57c72e-ad5c-463d-b404-93b58fc020e7.png"
                        ),
                        Map.of(
                                "/shop_image/00e766c7-b5b7-46bb-8b9b-50bb5079668b.png",
                                "00e766c7-b5b7-46bb-8b9b-50bb5079668b.png"
                        ),
                        Map.of(
                                "/shop_image/1c7e2d70-4ef2-4fd9-862e-40226846215c.png",
                                "1c7e2d70-4ef2-4fd9-862e-40226846215c.png"
                        )
                ))
                .content("상품옵션있음, 추가이미지있음")
                .itemStatus(ItemStatus.판매중)
                .subImages(new ArrayList<>())
                .useOptions(true)
                .build());

        itemService.saveItem(AddItemDto.builder()
                .sellerNo(sellerNo)
                .name("상품테스트2(품절)")
                .itemCategory(ItemCategory.바지)
                .price(BigDecimal.valueOf(50000))
                .totalStock(0)
                .discountPer(BigDecimal.valueOf(0))
                .addItemOptionDtoList(List.of(
                        AddItemOptionDto.builder()
                                .name("면바지")
                                .additionalPrice(BigDecimal.valueOf(0))
                                .optionStock(0)
                                .build(),
                        AddItemOptionDto.builder()
                                .name("청바지")
                                .additionalPrice(BigDecimal.valueOf(10000))
                                .optionStock(0)
                                .build()
                ))
                .mainImageInfo(Map.of("/shop_image/1736f6e5-e78c-4f29-96d4-621fbfd034d0.png", "1736f6e5-e78c-4f29-96d4-621fbfd034d0.png"))
                .subImagesInfo(List.of(
                        Map.of(
                                "/shop_image/97297b82-81cd-4de7-ba70-bd4e467716df.png",
                                "97297b82-81cd-4de7-ba70-bd4e467716df.png"
                        )
                ))
                .content("판매완료")
                .itemStatus(ItemStatus.품절)
                .subImages(new ArrayList<>())
                .useOptions(true)
                .build());

        List<AddItemOptionDto> emptyAddItemOptionDtoList = new ArrayList<>();
        itemService.saveItem(AddItemDto.builder()
                .sellerNo(sellerNo)
                .name("상품테스트3(옵션X)")
                .itemCategory(ItemCategory.아우터)
                .price(BigDecimal.valueOf(250000))
                .totalStock(20)
                .discountPer(BigDecimal.valueOf(0))
                .addItemOptionDtoList(emptyAddItemOptionDtoList)
                .mainImageInfo(Map.of("/shop_image/30537136-0d60-450e-8544-2f9eda4e4800.png", "30537136-0d60-450e-8544-2f9eda4e4800.png"))
                .subImagesInfo(List.of(
                        Map.of(
                                "/shop_image/b3ba7699-d546-4075-84a9-9b6888049a0c.png",
                                "b3ba7699-d546-4075-84a9-9b6888049a0c.png"
                        )
                ))
                .content("상품옵션없음, 추가이미지있음")
                .itemStatus(ItemStatus.판매중)
                .subImages(new ArrayList<>())
                .useOptions(true)
                .build());

        List<Map<String, String>> emptySubImageList = new ArrayList<>();
        itemService.saveItem(AddItemDto.builder()
                .sellerNo(sellerNo)
                .name("상품테스트4(추가이미지X)")
                .itemCategory(ItemCategory.신발)
                .price(BigDecimal.valueOf(150000))
                .totalStock(10)
                .discountPer(BigDecimal.valueOf(0))
                .addItemOptionDtoList(List.of(
                        AddItemOptionDto.builder()
                                .name("250")
                                .additionalPrice(BigDecimal.valueOf(0))
                                .optionStock(10)
                                .build(),
                        AddItemOptionDto.builder()
                                .name("260")
                                .additionalPrice(BigDecimal.valueOf(10000))
                                .optionStock(0)
                                .build()
                ))
                .mainImageInfo(Map.of("/shop_image/b3ecf6ae-140e-4950-81bf-74adce043239.png", "b3ecf6ae-140e-4950-81bf-74adce043239.png"))
                .subImagesInfo(emptySubImageList)
                .content("상품옵션있음, 추가이미지없음")
                .itemStatus(ItemStatus.판매중)
                .subImages(new ArrayList<>())
                .useOptions(true)
                .build());

        for (int i = 0; i < 50; i++) {
            itemService.saveItem(AddItemDto.builder()
                    .sellerNo(sellerNo)
                    .name("페이징테스트" + i)
                    .itemCategory(ItemCategory.신발)
                    .price(BigDecimal.valueOf(i * 1000))
                    .totalStock(i)
                    .discountPer(BigDecimal.valueOf(i))
                    .addItemOptionDtoList(emptyAddItemOptionDtoList)
                    .mainImageInfo(Map.of("/shop_image/c9cf742d-c0b0-4b8d-9253-cc32823d36db.png", "c9cf742d-c0b0-4b8d-9253-cc32823d36db.png"))
                    .subImagesInfo(emptySubImageList)
                    .content("상품옵션없음, 추가이미지없음")
                    .itemStatus(ItemStatus.승인대기)
                    .subImages(new ArrayList<>())
                    .useOptions(true)
                    .build());
        }

        /**
         * 장바구니 데이터
         */
        cartService.saveCart(SaveCartDto.builder()
                .itemNo(1L)
                .memberNo(5L)
                .itemOptionNo(1L)
                .count(1)
                .build());
        cartService.saveCart(SaveCartDto.builder()
                .itemNo(1L)
                .memberNo(5L)
                .itemOptionNo(4L)
                .count(2)
                .build());
        cartService.saveCart(SaveCartDto.builder()
                .itemNo(3L)
                .memberNo(5L)
                .count(6)
                .build());
        cartService.saveCart(SaveCartDto.builder()
                .itemNo(4L)
                .memberNo(5L)
                .itemOptionNo(7L)
                .count(1)
                .build());

        em.flush();
        em.clear();

        /**
         * 주문 목록 데이터
         */
        List<AddOrderItemDto> addOrderItemDtoList1 = new ArrayList<>();
        addOrderItemDtoList1.add(AddOrderItemDto.builder()
                .cartNo(1L)
                .optionNo(1L)
                .itemNo(1L)
                .count(1)
                .totalPrice(BigDecimal.valueOf(24300))
                .imageUrl("/shop_image/8ea0eafb-b1a7-492c-b574-654946184243.jpg")
                .itemName("상품테스트1")
                .optionName("검정색")
                .build());
        addOrderItemDtoList1.add(AddOrderItemDto.builder()
                .cartNo(3L)
                .itemNo(3L)
                .count(6)
                .totalPrice(BigDecimal.valueOf(1500000))
                .imageUrl("/shop_image/30537136-0d60-450e-8544-2f9eda4e4800.png")
                .itemName("상품테스트3(옵션X)")
                .optionName("")
                .build());
        orderService.saveOrder(5L, AddOrderDto.builder()
                .recipientName("메인수령인")
                .phoneNumber("010-1234-1234")
                .postcode("12345")
                .roadAddress("인천광역시 서구")
                .detailAddress("A아파트")
                .deliveryRequest("배송 전 미리 연락 부탁드립니다.")
                .addOrderItemDtoList(addOrderItemDtoList1)
                .totalProductPrice(BigDecimal.valueOf(1524300))
                .deliveryFee(0)
                .totalOrderPrice(BigDecimal.valueOf(1524300))
                .build());

        List<AddOrderItemDto> addOrderItemDtoList2 = new ArrayList<>();
        addOrderItemDtoList2.add(AddOrderItemDto.builder()
                .cartNo(4L)
                .optionNo(7L)
                .itemNo(4L)
                .count(1)
                .totalPrice(BigDecimal.valueOf(150000))
                .imageUrl("/shop_image/b3ecf6ae-140e-4950-81bf-74adce043239.png")
                .itemName("상품테스트4(추가이미지X)")
                .optionName("250")
                .build());
        orderService.saveOrder(5L, AddOrderDto.builder()
                .recipientName("메인수령인")
                .phoneNumber("010-1234-1234")
                .postcode("12345")
                .roadAddress("인천광역시 서구")
                .detailAddress("A아파트")
                .deliveryRequest("배송 전 미리 연락 부탁드립니다.")
                .addOrderItemDtoList(addOrderItemDtoList2)
                .totalProductPrice(BigDecimal.valueOf(150000))
                .deliveryFee(0)
                .totalOrderPrice(BigDecimal.valueOf(150000))
                .build());

        List<AddOrderItemDto> addOrderItemDtoList3 = new ArrayList<>();
        addOrderItemDtoList3.add(AddOrderItemDto.builder()
                .cartNo(1L)
                .optionNo(1L)
                .itemNo(1L)
                .count(1)
                .totalPrice(BigDecimal.valueOf(24300))
                .imageUrl("/shop_image/8ea0eafb-b1a7-492c-b574-654946184243.jpg")
                .itemName("상품테스트1")
                .optionName("검정색")
                .build());
        orderService.saveOrder(5L, AddOrderDto.builder()
                .recipientName("메인수령인")
                .phoneNumber("010-1234-1234")
                .postcode("12345")
                .roadAddress("인천광역시 서구")
                .detailAddress("A아파트")
                .deliveryRequest("부재 시 문 앞에 놓아주세요.")
                .addOrderItemDtoList(addOrderItemDtoList3)
                .totalProductPrice(BigDecimal.valueOf(24300))
                .deliveryFee(3000)
                .totalOrderPrice(BigDecimal.valueOf(27300))
                .build());

        em.flush();
        em.clear();
    }

    @Test
    public void findAllDeliveryTest() throws Exception {
        //given



    }



}