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
import myshop.shop.entity.item.ItemStatus;
import myshop.shop.entity.member.Gender;
import myshop.shop.entity.member.Member;
import myshop.shop.entity.member.MemberLevel;
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
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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


            for (int i=0; i<50; i++) {
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

            em.flush();
            em.clear();
        }
    }
}
