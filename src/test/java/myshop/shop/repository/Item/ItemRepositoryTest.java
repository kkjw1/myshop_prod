package myshop.shop.repository.Item;

import jakarta.persistence.EntityManager;
import myshop.shop.controller.sellerWeb.ItemController.BulkModifyItemDto;
import myshop.shop.dto.item.*;
import myshop.shop.entity.Seller;
import myshop.shop.entity.item.Item;
import myshop.shop.entity.item.ItemCategory;
import myshop.shop.entity.item.ItemStatus;
import myshop.shop.repository.seller.SellerRepository;
import myshop.shop.service.ItemService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@Transactional
class ItemRepositoryTest {
    @Autowired ItemRepository itemRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    ItemService itemService;
    @Autowired
    EntityManager em;

    @BeforeEach
    @Commit
    public void init() {
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

        itemService.saveItemForTest(new AddItemDto(sellerNo, "상품테스트2(품절)", ItemCategory.바지, 50000, 0, 0,
                List.of(
                        new AddItemOptionDto("면바지", 0, 0),
                        new AddItemOptionDto("청바지", 10000, 0)
                ), null, "/shop_image/1736f6e5-e78c-4f29-96d4-621fbfd034d0.png", null,
                List.of(
                        "/shop_image/97297b82-81cd-4de7-ba70-bd4e467716df.png"
                ), "판매완료", ItemStatus.품절,true, 50L));


        List<AddItemOptionDto> emptyAddItemOptionDtoList = new ArrayList<>();
        itemService.saveItemForTest(new AddItemDto(sellerNo, "상품테스트3(옵션X)", ItemCategory.아우터, 250000, 20, 0,
                emptyAddItemOptionDtoList, null, "/shop_image/30537136-0d60-450e-8544-2f9eda4e4800.png", null,
                List.of(
                        "/shop_image/b3ba7699-d546-4075-84a9-9b6888049a0c.png"
                ), "상품옵션없음, 추가이미지있음", ItemStatus.판매중, true, 5L));


        List<String> emptySubImageList = new ArrayList<>();
        itemService.saveItemForTest(new AddItemDto(sellerNo, "상품테스트4(추가이미지X)", ItemCategory.신발, 150000, 20, 0,
                List.of(
                        new AddItemOptionDto("250", 0, 10),
                        new AddItemOptionDto("260", 10000, 10)
                ), null, "/shop_image/b3ecf6ae-140e-4950-81bf-74adce043239.png", null,
                emptySubImageList, "상품옵션있음, 추가이미지없음",ItemStatus.판매중, true, 20L));


        for (int i=0; i<50; i++) {
            itemService.saveItemForTest(new AddItemDto(sellerNo, "페이징테스트" + i, ItemCategory.신발, i*1000, i, i,
                    emptyAddItemOptionDtoList, null, "/shop_image/c9cf742d-c0b0-4b8d-9253-cc32823d36db.png", null,
                    emptySubImageList, "상품옵션없음, 추가이미지없음", true));
        }

    }


    @Test
    @DisplayName("전체검색")
    @Commit
    public void searchItemPageTest() throws Exception {
        PageRequest pageRequest = PageRequest.of(0, 5);
        Seller seller = sellerRepository.findById("test").orElse(null);

        SearchItemDto searchItemDto = new SearchItemDto(seller.getNo(), null, null, null);
        Page<ManageItemDto> manageItemDtos = itemRepository.searchItemPage(pageRequest, searchItemDto);

        System.out.println("manageItemDtos = " + manageItemDtos);

        System.out.println("전체 데이터 수: " + manageItemDtos.getTotalElements());
        System.out.println("페이지 번호: " + manageItemDtos.getNumber());
        System.out.println("전체 페이지 번호: " + manageItemDtos.getTotalPages());
        System.out.println("첫 번째 항목인가?: " + manageItemDtos.isFirst());
        System.out.println("다음 페이지가 있는가?: " + manageItemDtos.hasNext());
    }


    @Test
    @DisplayName("조건 검색")
    @Commit
    public void searchItemPageTest2() throws Exception {
        PageRequest pageRequest = PageRequest.of(0, 3);
        Seller seller = sellerRepository.findById("test").orElse(null);

        SearchItemDto searchItemDto = new SearchItemDto(seller.getNo(), null, "상품테스트", null);
        Page<ManageItemDto> manageItemDtos = itemRepository.searchItemPage(pageRequest, searchItemDto);

        for (ManageItemDto manageItemDto : manageItemDtos) {
            System.out.println("manageItemDto = " + manageItemDto);
        }

        System.out.println("전체 데이터 수: " + manageItemDtos.getTotalElements());
        System.out.println("페이지 번호: " + manageItemDtos.getNumber());
        System.out.println("전체 페이지 번호: " + manageItemDtos.getTotalPages());
        System.out.println("첫 번째 항목인가?: " + manageItemDtos.isFirst());
        System.out.println("다음 페이지가 있는가?: " + manageItemDtos.hasNext());

    }

    @Test
    @DisplayName("조건 검색 여러 개")
    @Commit
    public void searchItemPageTest3() throws Exception {
        PageRequest pageRequest = PageRequest.of(0, 3);
        Seller seller = sellerRepository.findById("test").orElse(null);

        SearchItemDto searchItemDto = new SearchItemDto(seller.getNo(), 1L, "상품테스트", ItemStatus.판매중);
        Page<ManageItemDto> manageItemDtos = itemRepository.searchItemPage(pageRequest, searchItemDto);

        for (ManageItemDto manageItemDto : manageItemDtos) {
            System.out.println("manageItemDto = " + manageItemDto);
            String s = manageItemDto.getMainImagePath();
            String mainImagePath = s.replace("/shop_image/", "/shop_image/");
            System.out.println("mainImagePath = " + mainImagePath);
        }

        System.out.println("전체 데이터 수: " + manageItemDtos.getTotalElements());
        System.out.println("페이지 번호: " + manageItemDtos.getNumber());
        System.out.println("전체 페이지 번호: " + manageItemDtos.getTotalPages());
        System.out.println("첫 번째 항목인가?: " + manageItemDtos.isFirst());
        System.out.println("다음 페이지가 있는가?: " + manageItemDtos.hasNext());

    }

    
    @Test
    @DisplayName("하나만 변화 된 경우")
    public void bulkItemStatusDiscountTest() throws Exception {
        //given
        List<Long> itemNoList = new ArrayList<>();
        itemNoList.add(1L);
        itemNoList.add(2L);
        itemNoList.add(3L);
        itemNoList.add(4L);
        BulkModifyItemDto bulkModifyItemDto = new BulkModifyItemDto(itemNoList, null, 30);

        //when
        itemRepository.bulkItemStatusDiscount(bulkModifyItemDto);
        em.flush();
        em.clear();

        //then
        Item item = itemRepository.findById(1L).orElse(null);
        Assertions.assertThat(item.getDiscountPer()).isEqualTo(30);
        Item item2 = itemRepository.findById(2L).orElse(null);
        Assertions.assertThat(item2.getDiscountPer()).isEqualTo(30);
        Item item3 = itemRepository.findById(1L).orElse(null);
        Assertions.assertThat(item3.getDiscountPer()).isEqualTo(30);
        Item item4 = itemRepository.findById(2L).orElse(null);
        Assertions.assertThat(item4.getDiscountPer()).isEqualTo(30);
    }

    @Test
    @DisplayName("두 개 변화")
    public void bulkItemStatusDiscountTest2() throws Exception {
        //given
        List<Long> itemNoList = new ArrayList<>();
        itemNoList.add(1L);
        itemNoList.add(2L);
        BulkModifyItemDto bulkModifyItemDto = new BulkModifyItemDto(itemNoList, ItemStatus.판매중지, 21);

        //when
        itemRepository.bulkItemStatusDiscount(bulkModifyItemDto);
        em.flush();
        em.clear();

        //then
        Item item = itemRepository.findById(1L).orElse(null);
        Assertions.assertThat(item.getItemStatus()).isEqualTo(ItemStatus.판매중지);
        Assertions.assertThat(item.getDiscountPer()).isEqualTo(21);
        Item item2 = itemRepository.findById(2L).orElse(null);
        Assertions.assertThat(item2.getItemStatus()).isEqualTo(ItemStatus.판매중지);
        Assertions.assertThat(item2.getDiscountPer()).isEqualTo(21);
    }
    
    @Test
    @Commit
    public void findMainItemTest() throws Exception {
        //when
        List<MainItemDto> mainPageItem = itemRepository.findMainItem(3L);
        //4->1->3
        for (MainItemDto mainItemDto : mainPageItem) {
            System.out.println("mainItemDto = " + mainItemDto);
        }

    }

}