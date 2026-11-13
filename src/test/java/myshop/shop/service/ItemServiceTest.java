package myshop.shop.service;

import jakarta.persistence.EntityManager;
import java.math.BigDecimal;
import myshop.shop.dto.item.AddItemDto;
import myshop.shop.dto.item.AddItemOptionDto;
import myshop.shop.dto.item.DetailItemDto;
import myshop.shop.dto.item.DetailItemOptionDto;
import myshop.shop.entity.Seller;
import myshop.shop.entity.item.Item;
import myshop.shop.entity.item.ItemCategory;
import myshop.shop.entity.item.ItemOption;
import myshop.shop.entity.item.ItemStatus;
import myshop.shop.repository.Item.ItemOptionRepository;
import myshop.shop.repository.Item.ItemRepository;
import myshop.shop.repository.seller.SellerRepository;
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
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired
    ItemService itemService;
    @Autowired ItemRepository itemRepository;
    @Autowired SellerRepository sellerRepository;
    @Autowired ItemOptionRepository itemOptionRepository;
    @Autowired PasswordEncoder passwordEncoder;
    @Autowired
    EntityManager em;


    @BeforeEach
    @Commit
    public void init() {
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
        Long sellerNo = sellerRepository.findById("test").orElse(null).getNo();itemService.saveItemForTest(AddItemDto.builder()
                .sellerNo(sellerNo)
                .name("상품테스트1")
                .itemCategory(ItemCategory.상의)
                .price(BigDecimal.valueOf(30000))
                .totalStock(40)
                .discountPer(BigDecimal.valueOf(10))
                .addItemOptionDtoList(List.of(
                        AddItemOptionDto.builder()
                                .name("검정색")
                                .additionalPrice(BigDecimal.valueOf(0))
                                .optionStock(10)
                                .build(),
                        AddItemOptionDto.builder()
                                .name("나이키")
                                .additionalPrice(BigDecimal.valueOf(3000))
                                .optionStock(10)
                                .build(),
                        AddItemOptionDto.builder()
                                .name("흰색")
                                .additionalPrice(BigDecimal.valueOf(1000))
                                .optionStock(10)
                                .build(),
                        AddItemOptionDto.builder()
                                .name("로고")
                                .additionalPrice(BigDecimal.valueOf(3000))
                                .optionStock(10)
                                .build()
                ))
                .mainImageInfo(Map.of("/shop_image/8ea0eafb-b1a7-492c-b574-654946184243.png", "8ea0eafb-b1a7-492c-b574-654946184243.png"))
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
                .useOptions(true)
                .viewCount(10L)
                .subImages(new ArrayList<>())
                .build());

        itemService.saveItemForTest(AddItemDto.builder()
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
                .useOptions(true)
                .viewCount(50L)
                .subImages(new ArrayList<>())
                .build());

        List<AddItemOptionDto> emptyAddItemOptionDtoList = new ArrayList<>();
        itemService.saveItemForTest(AddItemDto.builder()
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
                .useOptions(true)
                .viewCount(5L)
                .subImages(new ArrayList<>())
                .build());

        List<Map<String, String>> emptySubImageList = new ArrayList<>();
        itemService.saveItemForTest(AddItemDto.builder()
                .sellerNo(sellerNo)
                .name("상품테스트4(추가이미지X)")
                .itemCategory(ItemCategory.신발)
                .price(BigDecimal.valueOf(150000))
                .totalStock(20)
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
                                .optionStock(10)
                                .build()
                ))
                .mainImageInfo(Map.of("/shop_image/b3ecf6ae-140e-4950-81bf-74adce043239.png", "b3ecf6ae-140e-4950-81bf-74adce043239.png"))
                .subImagesInfo(emptySubImageList)
                .content("상품옵션있음, 추가이미지없음")
                .itemStatus(ItemStatus.판매중)
                .useOptions(true)
                .viewCount(20L)
                .subImages(new ArrayList<>())
                .build());

        for (int i = 0; i < 50; i++) {
            itemService.saveItemForTest(AddItemDto.builder()
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
                    .useOptions(true)
                    .subImages(new ArrayList<>())
                    .build());
        }

        em.flush();
        em.clear();

    }


/*    @Test
    @DisplayName("상품 옵션 없는 경우")
    public void saveItemTest() throws Exception {
        //given
        Seller seller = sellerRepository.findById("test").orElse(null);
        AddItemDto addItemDto = AddItemDto.builder()
                .sellerNo(seller.getNo())
                .name("상품명")
                .itemCategory(ItemCategory.상의)
                .price(BigDecimal.valueOf(10000))
                .totalStock(10)
                .discountPer(BigDecimal.valueOf(10))
                .mainImageInfo("대표이미지")
                .subImagesInfo(List.of("추가 이미지"))
                .content("제품 상세 설명")
                .itemStatus(ItemStatus.판매중)
                .addItemOptionDtoList(new ArrayList<>())
                .subImages(new ArrayList<>())
                .build();
        itemService.saveItem(addItemDto);

        List<Item> item = itemRepository.findAll();
        List<ItemOption> itemOption = itemOptionRepository.findAll();

        assertThat(item.size()).isEqualTo(1);
        assertThat(item.get(0).getName()).isEqualTo("상품명");
        assertThat(itemOption).isEmpty();


    }

    @Test
    @DisplayName("상품 옵션 있는 경우")
    public void saveItemTest2() throws Exception {
        //given
        Seller seller = sellerRepository.findById("test").orElse(null);
        AddItemOptionDto addItemOptionDto1 = AddItemOptionDto.builder()
                .name("기본옵션1")
                .additionalPrice(BigDecimal.valueOf(0))
                .optionStock(5)
                .build();
        AddItemOptionDto addItemOptionDto2 = AddItemOptionDto.builder()
                .name("고급옵션1")
                .additionalPrice(BigDecimal.valueOf(1000))
                .optionStock(5)
                .build();

        AddItemDto addItemDto = AddItemDto.builder()
                .sellerNo(seller.getNo())
                .name("상품명")
                .itemCategory(ItemCategory.상의)
                .price(BigDecimal.valueOf(10000))
                .totalStock(10)
                .discountPer(BigDecimal.valueOf(10))
                .mainImageInfo("대표이미지")
                .subImagesInfo(List.of("추가 이미지"))
                .content("제품 상세 설명")
                .itemStatus(ItemStatus.판매중)
                .addItemOptionDtoList(new ArrayList<>())
                .subImages(new ArrayList<>())
                .build();
        addItemDto.updateAddItemOptionDtoList(addItemOptionDto1);
        addItemDto.updateAddItemOptionDtoList(addItemOptionDto2);
        itemService.saveItem(addItemDto);

        //when
        List<Item> item = itemRepository.findAll();
        List<ItemOption> itemOption = itemOptionRepository.findAll();

        //then
        assertThat(item.size()).isEqualTo(1);
        assertThat(item.get(0).getName()).isEqualTo("상품명");
        assertThat(itemOption.size()).isEqualTo(2);
        for (ItemOption option : itemOption) {
            System.out.println("option = " + option.getName());
        }
    }
    */
    @Test
    public void getDetailItemTest() throws Exception {
        //given
        Long itemNo = 1L;

        //when
        DetailItemDto detailItem = itemService.getDetailItem(itemNo);

        //then
        System.out.println("detailItem = " + detailItem);
        List<DetailItemOptionDto> detailItemOptionDtoList = detailItem.getDetailItemOptionDtoList();
        for (DetailItemOptionDto detailItemOptionDto : detailItemOptionDtoList) {
            System.out.println("detailItemOptionDto = " + detailItemOptionDto);
        }
    }
}