package myshop.shop;

import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import myshop.shop.dto.cancelRequest.SaveCancelRequestDto;
import myshop.shop.dto.cart.SaveCartDto;
import myshop.shop.dto.item.AddItemDto;
import myshop.shop.dto.item.AddItemOptionDto;
import myshop.shop.dto.order.AddOrderDto;
import myshop.shop.dto.order.AddOrderItemDto;
import myshop.shop.dto.returnRequest.SaveReturnRequestDto;
import myshop.shop.entity.*;
import myshop.shop.entity.cancelRequest.CancelReasonCode;
import myshop.shop.entity.cancelRequest.CancelRequestStatus;
import myshop.shop.entity.delivery.DeliveryStatus;
import myshop.shop.entity.inquiry.Inquiry;
import myshop.shop.entity.inquiry.InquiryCategory;
import myshop.shop.entity.inquiry.InquiryStatus;
import myshop.shop.entity.item.Item;
import myshop.shop.entity.item.ItemCategory;
import myshop.shop.entity.item.ItemOption;
import myshop.shop.entity.item.ItemStatus;
import myshop.shop.entity.member.Gender;
import myshop.shop.entity.member.Member;
import myshop.shop.entity.member.MemberLevel;
import myshop.shop.entity.order.Order;
import myshop.shop.entity.orderItem.OrderItem;
import myshop.shop.entity.orderItem.OrderItemStatus;
import myshop.shop.entity.returnRequest.ReturnReasonCode;
import myshop.shop.entity.returnRequest.ReturnRequestStatus;
import myshop.shop.entity.review.Review;
import myshop.shop.repository.Item.ItemRepository;
import myshop.shop.repository.address.AddressRepository;
import myshop.shop.repository.inquiry.InquiryRepository;
import myshop.shop.repository.member.MemberRepository;
import myshop.shop.repository.review.ReviewRepository;
import myshop.shop.repository.seller.SellerRepository;
import myshop.shop.service.*;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static myshop.shop.InitSeedData.ITEMS;
import static myshop.shop.InitSeedData.REVIEWER_NAMES;


@Component
@Profile("local")
@RequiredArgsConstructor
public class InitDB {

    private final InitData initData;

    @PostConstruct
    public void init() {
//        initData.dbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitData {
        private final MemberRepository memberRepository;
        private final PasswordEncoder passwordEncoder;
        private final AddressRepository addressRepository;
        private final EntityManager em;
        private final SellerRepository sellerRepository;
        private final ItemService itemService;
        private final CartService cartService;
        private final OrderService orderService;
        private final CancelRequestService cancelRequestService;
        private final ReturnRequestService returnRequestService;
        private final ItemRepository itemRepository;
        private final ReviewRepository reviewRepository;
        private final InquiryRepository inquiryRepository;

        public void dbInit() {
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
                    .mainImageInfo(Map.of(
                            "imageUrl",
                            "https://ax77cega60fl.objectstorage.ap-chuncheon-1.oci.customer-oci.com/n/ax77cega60fl/b/jwshop-images-bucket/o/item_images%2Fef48c475-88b6-4a5c-8cfc-65ca436c5703.jpg",
                            "imageName",
                            "item_images/2Fef48c475-88b6-4a5c-8cfc-65ca436c5703.jpg"
                    ))
                    .subImagesInfo(List.of(
                            Map.of(
                                    "imageUrl",
                                    "https://ax77cega60fl.objectstorage.ap-chuncheon-1.oci.customer-oci.com/n/ax77cega60fl/b/jwshop-images-bucket/o/item_images%2F436afd8b-e71e-495e-9197-16e2f8eb7715.png",
                                    "imageName",
                                    "item_images/2F436afd8b-e71e-495e-9197-16e2f8eb7715.png"
                            ),
                            Map.of(
                                    "imageUrl",
                                    "https://ax77cega60fl.objectstorage.ap-chuncheon-1.oci.customer-oci.com/n/ax77cega60fl/b/jwshop-images-bucket/o/item_images%2Fabdfc871-9bb5-453f-bfb7-59d2af2e5f60.png",
                                    "imageName",
                                    "item_images/2Fabdfc871-9bb5-453f-bfb7-59d2af2e5f60.png"
                            ),
                            Map.of(
                                    "imageUrl",
                                    "https://ax77cega60fl.objectstorage.ap-chuncheon-1.oci.customer-oci.com/n/ax77cega60fl/b/jwshop-images-bucket/o/item_images%2Fca96c447-3571-4c7d-88ac-6e8f7d35c757.png",
                                    "imageName",
                                    "item_images/2Fca96c447-3571-4c7d-88ac-6e8f7d35c757.png"
                            )
                    ))
                    .content("상품옵션있음, 추가이미지있음")
                    .itemStatus(ItemStatus.판매중)
                    .useOptions(true)
                    .subImages(new ArrayList<>())
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
                    .mainImageInfo(Map.of(
                            "imageUrl",
                            "https://ax77cega60fl.objectstorage.ap-chuncheon-1.oci.customer-oci.com/n/ax77cega60fl/b/jwshop-images-bucket/o/item_images%2F1e26cce2-0c57-4ec3-9e48-6e4ca720093f.png",
                            "imageName",
                            "item_images/2F1e26cce2-0c57-4ec3-9e48-6e4ca720093f.png"
                    ))
                    .subImagesInfo(List.of(
                            Map.of(
                                    "imageUrl",
                                    "https://ax77cega60fl.objectstorage.ap-chuncheon-1.oci.customer-oci.com/n/ax77cega60fl/b/jwshop-images-bucket/o/item_images%2F4579bc15-a6da-4785-a96c-ede314e7cd04.png",
                                    "imageName",
                                    "item_images/2F4579bc15-a6da-4785-a96c-ede314e7cd04.png"
                            )
                    ))
                    .content("판매완료")
                    .itemStatus(ItemStatus.품절)
                    .useOptions(true)
                    .subImages(new ArrayList<>())
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
                    .mainImageInfo(Map.of(
                            "imageUrl",
                            "https://ax77cega60fl.objectstorage.ap-chuncheon-1.oci.customer-oci.com/n/ax77cega60fl/b/jwshop-images-bucket/o/item_images%2F38162620-1945-49bd-8c09-067d17633473.png",
                            "imageName",
                            "item_images/2F38162620-1945-49bd-8c09-067d17633473.png"
                    ))
                    .subImagesInfo(List.of(
                            Map.of(
                                    "imageUrl",
                                    "https://ax77cega60fl.objectstorage.ap-chuncheon-1.oci.customer-oci.com/n/ax77cega60fl/b/jwshop-images-bucket/o/item_images%2Fc4ac80db-6519-40d2-af51-b65b40dbcea7.png",
                                    "imageName",
                                    "item_images/2Fc4ac80db-6519-40d2-af51-b65b40dbcea7.png"
                            )
                    ))
                    .content("상품옵션없음, 추가이미지있음")
                    .itemStatus(ItemStatus.판매중)
                    .useOptions(true)
                    .subImages(new ArrayList<>())
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
                    .mainImageInfo(Map.of(
                            "imageUrl",
                            "https://ax77cega60fl.objectstorage.ap-chuncheon-1.oci.customer-oci.com/n/ax77cega60fl/b/jwshop-images-bucket/o/item_images%2F661e3e16-1a15-48de-85d8-98a9d303c118.png",
                            "imageName",
                            "item_images/2F661e3e16-1a15-48de-85d8-98a9d303c118.png"
                    ))
                    .subImagesInfo(emptySubImageList)
                    .content("상품옵션있음, 추가이미지없음")
                    .itemStatus(ItemStatus.판매중)
                    .useOptions(true)
                    .subImages(new ArrayList<>())
                    .build());


/*            for (int i=0; i<50; i++) {
                itemService.saveItem(AddItemDto.builder()
                        .sellerNo(sellerNo)
                        .name("페이징테스트" + i)
                        .itemCategory(ItemCategory.신발)
                        .price(BigDecimal.valueOf(i*1000))
                        .totalStock(i)
                        .discountPer(BigDecimal.valueOf(i))
                        .addItemOptionDtoList(emptyAddItemOptionDtoList)
                        .mainImageInfo(Map.of(
                                "imageUrl",
                                "https://ax77cega60fl.objectstorage.ap-chuncheon-1.oci.customer-oci.com/n/ax77cega60fl/b/jwshop-images-bucket/o/item_images%2F7868b3f0-e2ff-43aa-a10b-996d057202f6.png",
                                "imageName",
                                "item_images/2F7868b3f0-e2ff-43aa-a10b-996d057202f6.png"
                        ))
                        .subImagesInfo(emptySubImageList)
                        .content("상품옵션없음, 추가이미지없음")
                        .itemStatus(ItemStatus.승인대기)
                        .useOptions(true)
                        .subImages(new ArrayList<>())
                        .build());
            }*/

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
                    .imageUrl("https://ax77cega60fl.objectstorage.ap-chuncheon-1.oci.customer-oci.com/n/ax77cega60fl/b/jwshop-images-bucket/o/item_images%2Fef48c475-88b6-4a5c-8cfc-65ca436c5703.jpg")
                    .itemName("상품테스트1")
                    .optionName("검정색")
                    .build());
            addOrderItemDtoList1.add(AddOrderItemDto.builder()
                    .cartNo(3L)
                    .itemNo(3L)
                    .count(6)
                    .totalPrice(BigDecimal.valueOf(1500000))
                    .imageUrl("https://ax77cega60fl.objectstorage.ap-chuncheon-1.oci.customer-oci.com/n/ax77cega60fl/b/jwshop-images-bucket/o/item_images%2F38162620-1945-49bd-8c09-067d17633473.png")
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
                    .imageUrl("https://ax77cega60fl.objectstorage.ap-chuncheon-1.oci.customer-oci.com/n/ax77cega60fl/b/jwshop-images-bucket/o/item_images%2F661e3e16-1a15-48de-85d8-98a9d303c118.png")
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
                    .imageUrl("https://ax77cega60fl.objectstorage.ap-chuncheon-1.oci.customer-oci.com/n/ax77cega60fl/b/jwshop-images-bucket/o/item_images%2Fef48c475-88b6-4a5c-8cfc-65ca436c5703.jpg")
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
            // 반품, 반품은 배송완료, 배송중 상태에서만 가능하다.
            em.createQuery("update Delivery d " +
                            "set d.deliveryStatus=:status, d.courier=:courier, d.trackingNumber=:trackingNumber " +
                            "where d.no = 1L")
                    .setParameter("status", DeliveryStatus.배송완료)
                    .setParameter("courier", "우체국택배")
                    .setParameter("trackingNumber", "12345432")
                    .executeUpdate();
            SaveReturnRequestDto returnRequestDto = SaveReturnRequestDto.builder()
                    .orderItemNo(1L)
                    .memberNo(5L)
                    .count(1)
                    .returnReasonCode(ReturnReasonCode.DEFECTIVE)
                    .reasonDetail("모서리쪽 스크래치가 있음")
                    .price(BigDecimal.valueOf(24300))
                    .returnRequestStatus(ReturnRequestStatus.요청)
                    .build();
            returnRequestService.saveReturnRequest(returnRequestDto);

            // 취소
            SaveCancelRequestDto saveCancelRequestDto = SaveCancelRequestDto.builder()
                    .orderNo(3L)
                    .orderItemNo(4L)
                    .memberNo(5L)
                    .count(1)
                    .cancelReasonCode(CancelReasonCode.WRONG_ORDER)
                    .reasonDetail("사이즈 잘못 선택함")
                    .price(BigDecimal.valueOf(24300))
                    .cancelRequestStatus(CancelRequestStatus.요청)
                    .build();
            cancelRequestService.saveCancelRequest(saveCancelRequestDto);

            // 취소2
            SaveCancelRequestDto saveCancelRequestDto2 = SaveCancelRequestDto.builder()
                    .orderNo(1L)
                    .orderItemNo(2L)
                    .memberNo(5L)
                    .count(6)
                    .cancelReasonCode(CancelReasonCode.CHANGED_MIND)
                    .reasonDetail("단순변심")
                    .price(BigDecimal.valueOf(1500000))
                    .cancelRequestStatus(CancelRequestStatus.요청)
                    .build();
            cancelRequestService.saveCancelRequest(saveCancelRequestDto2);


            em.createQuery("update CancelRequest cr set cr.createdDate=:updateDate, cr.lastModifiedDate=:updateDate2 where cr.no = 1L")
                    .setParameter("updateDate", LocalDateTime.of(2026, 7, 1, 0, 0))
                    .setParameter("updateDate2", LocalDateTime.of(2026, 7, 1, 0, 0))
                    .executeUpdate();

            em.createQuery("update ReturnRequest rr set rr.createdDate=:updateDate, rr.lastModifiedDate=:updateDate2 where rr.no = 1L")
                    .setParameter("updateDate", LocalDateTime.of(2026, 7, 4, 0, 0))
                    .setParameter("updateDate2", LocalDateTime.of(2026, 7, 4, 0, 0))
                    .executeUpdate();

            em.createQuery("update CancelRequest cr set cr.createdDate=:updateDate, cr.lastModifiedDate=:updateDate2 where cr.no = 2L")
                    .setParameter("updateDate", LocalDateTime.of(2026, 7, 7, 0, 0))
                    .setParameter("updateDate2", LocalDateTime.of(2026, 7, 7, 0, 0))
                    .executeUpdate();


            /**
             * 배송완료 데이터
             */
            List<AddOrderItemDto> addOrderItemDtoList4 = new ArrayList<>();
            addOrderItemDtoList4.add(AddOrderItemDto.builder()
                    .cartNo(5L)
                    .optionNo(1L)
                    .itemNo(1L)
                    .count(2)
                    .totalPrice(BigDecimal.valueOf(48600))
                    .imageUrl("https://ax77cega60fl.objectstorage.ap-chuncheon-1.oci.customer-oci.com/n/ax77cega60fl/b/jwshop-images-bucket/o/item_images%2Fef48c475-88b6-4a5c-8cfc-65ca436c5703.jpg")
                    .itemName("상품테스트1")
                    .optionName("검정색")
                    .build());
            orderService.saveOrder(5L, AddOrderDto.builder()
                    .recipientName("메인수령인")
                    .phoneNumber("010-1234-1234")
                    .postcode("12345")
                    .roadAddress("인천광역시 서구")
                    .detailAddress("A아파트")
                    .deliveryRequest("배송 전 미리 연락 부탁드립니다.")
                    .addOrderItemDtoList(addOrderItemDtoList4)
                    .totalProductPrice(BigDecimal.valueOf(48600))
                    .deliveryFee(0)
                    .totalOrderPrice(BigDecimal.valueOf(48600))
                    .build());
            em.createQuery("update Delivery d " +
                            "set d.deliveryStatus=:status, d.courier=:courier, d.trackingNumber=:trackingNumber " +
                            "where d.no = 5L")
                    .setParameter("status", DeliveryStatus.배송완료)
                    .setParameter("courier", "우체국택배")
                    .setParameter("trackingNumber", "12345432")
                    .executeUpdate();
            em.createQuery("update OrderItem oi set oi.orderItemStatus=:orderItemStatus where oi.no = 5L")
                    .setParameter("orderItemStatus", OrderItemStatus.배송완료)
                    .executeUpdate();

            /**
             * 상품리뷰 데이터
             */
            //itemNo=1, itemOption 1,2,3,4 존재함
            Item itemProxy = itemRepository.getReferenceById(1L);
            for (int i=1; i<10; i++) {
                reviewRepository.save(Review.builder()
                        .item(itemProxy)
                        .memberNo(1L)
                        .optionName("검정색")
                        .count(i)
                        .score(5L)
                        .content("상품 1번의 검정색 옵션 상품리뷰" + i)
                        .build());
            }
            for (int i=1; i<9; i++) {
                reviewRepository.save(Review.builder()
                        .item(itemProxy)
                        .memberNo(2L)
                        .optionName("나이키")
                        .count(i)
                        .score(4L)
                        .content("상품 1번의 나이키 옵션 상품리뷰" + i)
                        .build());
            }
            for (int i=1; i<8; i++) {
                reviewRepository.save(Review.builder()
                        .item(itemProxy)
                        .memberNo(3L)
                        .optionName("흰색")
                        .count(i)
                        .score(3L)
                        .content("상품 1번의 흰색 옵션 상품리뷰" + i)
                        .build());
            }
            for (int i=1; i<7; i++) {
                reviewRepository.save(Review.builder()
                        .item(itemProxy)
                        .memberNo(4L)
                        .optionName("로고")
                        .count(i)
                        .score(2L)
                        .content("상품 1번의 로고 옵션 상품리뷰" + i)
                        .build());
            }
            for (int i=1; i<6; i++) {
                reviewRepository.save(Review.builder()
                        .item(itemProxy)
                        .memberNo(3L)
                        .optionName("검정색")
                        .count(i)
                        .score(1L)
                        .content("상품 1번의 검정색 옵션 상품리뷰" + i)
                        .build());
            }


            /**
             * 상품문의 데이터
             * 상품 기타 문의 10개, 상품 반품 문의 5개, 결제 취소 문의 3개
             */

            for (int i=1; i<11; i++) {
                inquiryRepository.save(Inquiry.builder()
                        .item(itemProxy)
                        .memberNo(5L)
                        .optionName("나이키")
                        .inquiryCategory(InquiryCategory.PRODUCT)
                        .title("상품/기타 제목" + i)
                        .content("기타문의 상세내용" + i)
                        .inquiryStatus(InquiryStatus.답변완료)
                        .answerContent("문의 답변 완료" + i)
                        .build());
            }
            for (int i=1; i<6; i++) {
                inquiryRepository.save(Inquiry.builder()
                        .item(itemProxy)
                        .memberNo(5L)
                        .optionName("검정색")
                        .inquiryCategory(InquiryCategory.CANCEL)
                        .title("결제 취소 제목" + i)
                        .content("결제 취소 상세내용" + i)
                        .inquiryStatus(InquiryStatus.답변대기)
                        .build());
            }
            for (int i=1; i<6; i++) {
                inquiryRepository.save(Inquiry.builder()
                        .item(itemProxy)
                        .memberNo(5L)
                        .optionName("흰색")
                        .inquiryCategory(InquiryCategory.RETURN)
                        .title("상품 반품 문의" + i)
                        .content("상품 반품 상세내용" + i)
                        .inquiryStatus(InquiryStatus.답변완료)
                        .answerContent("상품 반품 답변 완료" + i)
                        .build());
            }


            initAiSeedData(sellerNo);


            em.flush();
            em.clear();
        }

        /**
         * AI 기능 시드 데이터
         * 1. 리뷰 작성 회원 20명 (reviewer01 ~ reviewer20 / password)
         * 2. 판매자 "test" 1명의 상품 22개 (InitSeedData.ITEMS)
         * 3. 상품마다 리뷰 20개, 문의 3~4개
         * 4. 상담 에이전트 시나리오용 주문 (상품준비중 / 배송중 / 배송완료, 타인 주문)
         */
        private void initAiSeedData(Long sellerNo) {
            // 리뷰 작성 회원
            List<Long> reviewerNos = new ArrayList<>();
            String reviewerPassword = passwordEncoder.encode("password");
            for (int i = 0; i < REVIEWER_NAMES.size(); i++) {
                String reviewerId = String.format("reviewer%02d", i + 1);
                Member reviewer = memberRepository.save(new Member(reviewerId, reviewerId + "@myshop.test", reviewerPassword,
                        REVIEWER_NAMES.get(i), "SKT", String.format("010-1111-%04d", i + 1),
                        i % 2 == 0 ? Gender.MAN : Gender.WOMAN, MemberLevel.normal));
                reviewerNos.add(reviewer.getNo());
            }

            // 상품 저장 (이미지는 기존 상품과 동일하게 mainImageInfo / subImagesInfo 형태로 전달)
            for (InitSeedData.SeedItem seedItem : ITEMS) {
                List<AddItemOptionDto> optionDtoList = seedItem.options().stream()
                        .map(o -> AddItemOptionDto.builder()
                                .name(o.name())
                                .additionalPrice(BigDecimal.valueOf(o.additionalPrice()))
                                .optionStock(o.stock())
                                .build())
                        .toList();
                itemService.saveItem(AddItemDto.builder()
                        .sellerNo(sellerNo)
                        .name(seedItem.name())
                        .itemCategory(seedItem.category())
                        .price(BigDecimal.valueOf(seedItem.price()))
                        .totalStock(seedItem.totalStock())
                        .discountPer(BigDecimal.valueOf(seedItem.discountPer()))
                        .addItemOptionDtoList(new ArrayList<>(optionDtoList))
                        .mainImageInfo(toImageInfo(seedItem.mainImage()))
                        .subImagesInfo(seedItem.subImages().stream().map(this::toImageInfo).toList())
                        .subImages(new ArrayList<>())
                        .content(seedItem.content())
                        .itemStatus(ItemStatus.판매중)
                        .useOptions(true)
                        .build());
            }
            em.flush();
            em.clear();

            // 상품명 -> 상품(옵션 포함)
            Map<String, Item> seedItemMap = new HashMap<>();
            for (InitSeedData.SeedItem seedItem : ITEMS) {
                Item item = em.createQuery("select distinct i from Item i left join fetch i.itemOptions where i.name = :name", Item.class)
                        .setParameter("name", seedItem.name())
                        .getSingleResult();
                seedItemMap.put(seedItem.name(), item);
            }

            // 리뷰, 문의 저장
            for (InitSeedData.SeedItem seedItem : ITEMS) {
                Item item = seedItemMap.get(seedItem.name());
                List<InitSeedData.SeedReview> reviews = seedItem.reviews();
                for (int i = 0; i < reviews.size(); i++) {
                    InitSeedData.SeedReview r = reviews.get(i);
                    reviewRepository.save(new Review(item, reviewerNos.get(i % reviewerNos.size()), r.optionName(),
                            1, r.score(), r.content()));
                }

                List<InitSeedData.SeedInquiry> inquiries = seedItem.inquiries();
                for (int i = 0; i < inquiries.size(); i++) {
                    InitSeedData.SeedInquiry q = inquiries.get(i);
                    Long memberNo = reviewerNos.get((i * 3 + seedItem.name().length()) % reviewerNos.size());
                    if (q.answer() == null) {
                        inquiryRepository.save(new Inquiry(item, memberNo, q.optionName(), q.category(), q.title(),
                                q.content(), InquiryStatus.답변대기, null));
                    } else {
                        inquiryRepository.save(new Inquiry(item, memberNo, q.optionName(), q.category(), q.title(),
                                q.content(), InquiryStatus.답변완료, q.answer()));
                    }
                }
            }
            em.flush();
            em.clear();

            /*
             * 상담 에이전트 시나리오 주문
             * - test 회원: "어제 산 신발 언제 와요? 사이즈 잘못 골랐는데 취소돼요?" -> 상품준비중(취소 가능)
             *              "셔츠 어디쯤 왔어요?" -> 배송중(취소 불가, 반품 안내)
             *              "패딩 반품하고 싶어요" -> 배송완료(반품 가능)
             *              "오늘 주문한 거 취소해줘" -> 상품 2개 주문(어느 상품인지 되물어야 함)
             * - id1 회원: 타인 주문 조회/취소 차단 테스트용
             */
            Long testMemberNo = memberRepository.findById("test").orElseThrow().getNo();
            Long otherMemberNo = memberRepository.findById("id1").orElseThrow().getNo();
            LocalDateTime now = LocalDateTime.now();

            Order yesterdayOrder = saveSeedOrder(testMemberNo, seedItemMap, "부재 시 문 앞에 놓아주세요.",
                    List.of(new SeedOrderLine("에어쿠션 데일리 러닝화", "270", 1)));
            updateSeedOrder(yesterdayOrder, DeliveryStatus.상품준비중, null, null, now.minusDays(1));

            Order shippingOrder = saveSeedOrder(testMemberNo, seedItemMap, "배송 전 미리 연락 부탁드립니다.",
                    List.of(new SeedOrderLine("옥스포드 버튼다운 셔츠", "블루 L", 1)));
            updateSeedOrder(shippingOrder, DeliveryStatus.배송중, "CJ대한통운", "564738291045", now.minusDays(3));

            Order deliveredOrder = saveSeedOrder(testMemberNo, seedItemMap, "경비실에 맡겨주세요.",
                    List.of(new SeedOrderLine("경량 구스다운 패딩", "블랙 M", 1),
                            new SeedOrderLine("데일리 노트북 백팩", "블랙", 1)));
            updateSeedOrder(deliveredOrder, DeliveryStatus.배송완료, "우체국택배", "6891234570123", now.minusDays(7));

            Order todayOrder = saveSeedOrder(testMemberNo, seedItemMap, "배송 전 미리 연락 부탁드립니다.",
                    List.of(new SeedOrderLine("와이드 데님 팬츠", "30", 1),
                            new SeedOrderLine("울 블렌드 크루넥 니트", "브라운 M", 1)));
            updateSeedOrder(todayOrder, DeliveryStatus.상품준비중, null, null, now);

            Order otherMemberOrder = saveSeedOrder(otherMemberNo, seedItemMap, "문 앞에 놓아주세요.",
                    List.of(new SeedOrderLine("스웨이드 첼시 부츠", "260", 1)));
            updateSeedOrder(otherMemberOrder, DeliveryStatus.상품준비중, null, null, now.minusDays(1));

            Order otherMemberShippingOrder = saveSeedOrder(otherMemberNo, seedItemMap, "",
                    List.of(new SeedOrderLine("클래식 캔버스 로우 스니커즈", "화이트 250", 2)));
            updateSeedOrder(otherMemberShippingOrder, DeliveryStatus.배송중, "한진택배", "421098765432", now.minusDays(2));
        }

        // OCI 에 업로드한 시드 이미지 URL 접두사 (뒤에 파일명이 붙는다)
        private static final String OCI_IMAGE_URL_PREFIX =
                "https://ax77cega60fl.objectstorage.ap-chuncheon-1.oci.customer-oci.com/n/ax77cega60fl/b/jwshop-images-bucket/o/item_images%2F";

        /**
         * 시드 이미지 파일명 -> OCI 이미지 URL
         * 예) "seed_01_running_main.jpg" -> ".../o/item_images%2Fseed_01_running_main.jpg"
         */
        private String toImageUrl(String fileName) {
            return OCI_IMAGE_URL_PREFIX + fileName;
        }

        /**
         * 시드 이미지 파일명 -> saveItem 이 읽는 이미지 정보(Map<imageUrl, imageName>) 로 변환
         * imageName 은 OCI 오브젝트 이름(item_images/파일명)
         */
        private Map<String, String> toImageInfo(String fileName) {
            return Map.of(
                    "imageUrl", toImageUrl(fileName),
                    "imageName", "item_images/" + fileName);
        }

        record SeedOrderLine(String itemName, String optionName, int count) {
        }

        /**
         * 시드 주문 저장, 금액은 (할인가 + 옵션 추가금) * 수량으로 계산
         */
        private Order saveSeedOrder(Long memberNo, Map<String, Item> seedItemMap, String deliveryRequest, List<SeedOrderLine> lines) {
            List<AddOrderItemDto> addOrderItemDtoList = new ArrayList<>();
            BigDecimal totalProductPrice = BigDecimal.ZERO;
            for (SeedOrderLine line : lines) {
                Item item = seedItemMap.get(line.itemName());
                ItemOption option = item.getItemOptions().stream()
                        .filter(o -> o.getName().equals(line.optionName()))
                        .findFirst()
                        .orElseThrow();
                BigDecimal discountedPrice = item.getOriginalPrice()
                        .multiply(BigDecimal.valueOf(100).subtract(item.getDiscountPer()))
                        .divide(BigDecimal.valueOf(100), 0, RoundingMode.DOWN);
                BigDecimal totalPrice = discountedPrice.add(option.getAdditionalPrice())
                        .multiply(BigDecimal.valueOf(line.count()));
                String mainImage = ITEMS.stream()
                        .filter(s -> s.name().equals(line.itemName()))
                        .findFirst()
                        .orElseThrow()
                        .mainImage();
                addOrderItemDtoList.add(AddOrderItemDto.builder()
                        .cartNo(null)
                        .optionNo(option.getNo())
                        .itemNo(item.getNo())
                        .count(line.count())
                        .discountedPrice(discountedPrice)
                        .totalPrice(totalPrice)
                        .imageUrl(toImageUrl(mainImage))
                        .itemName(item.getName())
                        .optionName(option.getName())
                        .build());
                totalProductPrice = totalProductPrice.add(totalPrice);
            }
            int deliveryFee = totalProductPrice.compareTo(BigDecimal.valueOf(50000)) < 0 ? 3000 : 0;

            Member member = memberRepository.findById(memberNo).orElseThrow();
            return orderService.saveOrder(memberNo, AddOrderDto.builder()
                    .recipientName(member.getName())
                    .phoneNumber(member.getPhoneNumber())
                    .postcode("12345")
                    .roadAddress("인천광역시 서구")
                    .detailAddress("A아파트")
                    .deliveryRequest(deliveryRequest)
                    .addOrderItemDtoList(addOrderItemDtoList)
                    .totalProductPrice(totalProductPrice)
                    .deliveryFee(deliveryFee)
                    .totalOrderPrice(totalProductPrice.add(BigDecimal.valueOf(deliveryFee)))
                    .build());
        }

        /**
         * 시드 주문의 배송 상태, 택배사, 운송장번호, 주문일 변경
         */
        private void updateSeedOrder(Order order, DeliveryStatus deliveryStatus, String courier, String trackingNumber,
                                     LocalDateTime orderDate) {
            em.flush();
            List<OrderItem> orderItems = em.createQuery("select oi from OrderItem oi join fetch oi.delivery " +
                            "where oi.order.no = :orderNo", OrderItem.class)
                    .setParameter("orderNo", order.getNo())
                    .getResultList();
            for (OrderItem orderItem : orderItems) {
                orderItem.getDelivery().updateDeliveryStatus(deliveryStatus);
                orderItem.getDelivery().updateCourier(courier);
                orderItem.getDelivery().updateTrackingNumber(trackingNumber);
                orderItem.updateOrderItemStatus(deliveryStatus);
            }
            em.flush();

            em.createQuery("update Order o set o.createdDate=:orderDate, o.lastModifiedDate=:orderDate where o.no = :orderNo")
                    .setParameter("orderDate", orderDate)
                    .setParameter("orderNo", order.getNo())
                    .executeUpdate();
            em.createQuery("update OrderItem oi set oi.createdDate=:orderDate where oi.order.no = :orderNo")
                    .setParameter("orderDate", orderDate)
                    .setParameter("orderNo", order.getNo())
                    .executeUpdate();
        }
    }
}
