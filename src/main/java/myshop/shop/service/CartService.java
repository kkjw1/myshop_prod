package myshop.shop.service;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import myshop.shop.controller.HomeController;
import myshop.shop.dto.cart.ManageCartDto;
import myshop.shop.dto.cart.SaveCartDto;
import myshop.shop.entity.Cart;
import myshop.shop.entity.item.Item;
import myshop.shop.entity.item.ItemImage;
import myshop.shop.entity.item.ItemOption;
import myshop.shop.entity.member.Member;
import myshop.shop.repository.Item.ItemImageRepository;
import myshop.shop.repository.Item.ItemOptionRepository;
import myshop.shop.repository.Item.ItemRepository;
import myshop.shop.repository.cart.CartRepository;
import myshop.shop.repository.member.MemberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

import static myshop.shop.service.ItemService.getDiscountedPrice;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;
    private final ItemOptionRepository itemOptionRepository;
    private final EntityManager em;
    private final ItemImageRepository itemImageRepository;



    /**
     * 장바구니 저장
     */
    public void saveCart(SaveCartDto saveCartDto) {
        log.info("saveCartDto={}", saveCartDto);
        Member memberProxy = memberRepository.getReferenceById(saveCartDto.getMemberNo());
        Item itemProxy = itemRepository.getReferenceById(saveCartDto.getItemNo());
        Long itemOptionNo = saveCartDto.getItemOptionNo();
        int count = saveCartDto.getCount();

        List<Cart> findCart = em.createQuery("select c from Cart c where c.item=:item and c.member=:member and c.itemOption.no=:itemOptionNo", Cart.class)
                .setParameter("item", itemProxy)
                .setParameter("member", memberProxy)
                .setParameter("itemOptionNo", itemOptionNo)
                .getResultList();

        log.info("findCart.isEmpty()={}", findCart.isEmpty());
        if (findCart.isEmpty()) {
            if (itemOptionNo == null) {
                Cart cart = new Cart(memberProxy,
                        itemProxy,
                        count);
                cartRepository.save(cart);
            }
            else {
                ItemOption itemOptionProxy = itemOptionRepository.getReferenceById(itemOptionNo);
                Cart cart = new Cart(memberProxy,
                        itemProxy,
                        count,
                        itemOptionProxy);
                cartRepository.save(cart);
            }
        }
        else {
            findCart.get(0).addCount(count);
        }
    }



    /**
     * 모든 장바구니 불러오기
     */
    public List<ManageCartDto> findAllCart(Long memberNo) {
        List<ManageCartDto> manageCartList = cartRepository.getManageCartList(memberNo);

        for (ManageCartDto manageCartDto : manageCartList) {
            BigDecimal originalPrice = manageCartDto.getOriginalPrice();
            BigDecimal discountPer = manageCartDto.getDiscountPer();
            BigDecimal optionPrice = manageCartDto.getOptionPrice() == null ? BigDecimal.valueOf(0) : manageCartDto.getOptionPrice();
            BigDecimal discountedPrice = getDiscountedPrice(originalPrice, discountPer);
            manageCartDto.setPrice(discountedPrice.add(optionPrice));
        }

        log.info("manageCartList={}", manageCartList);
        return manageCartList;
    }



    /**
     * 주문 결제 데이터 불러오기
     * 장바구니 폼 -> 구매하기
     */
    public ManageCartDto findCart(Long cartNo) {
        ManageCartDto manageCartDto = cartRepository.getManageCart(cartNo);

        BigDecimal originalPrice = manageCartDto.getOriginalPrice();
        BigDecimal discountPer = manageCartDto.getDiscountPer();
        BigDecimal optionPrice = manageCartDto.getOptionPrice() == null ? BigDecimal.valueOf(0) : manageCartDto.getOptionPrice();
        BigDecimal discountedPrice = getDiscountedPrice(originalPrice, discountPer);
        manageCartDto.setPrice(discountedPrice.add(optionPrice));

        return manageCartDto;
    }



    /**
     * 주문/결제 폼
     * 상품 상세 폼 -> 바로 구매
     */
/*    public ManageCartDto findCart(DirectOrderDto directOrderDto) {
        return cartRepository.getManageCart(directOrderDto);
    }*/



    /**
     * 장바구니 개수 수정
     */
    public void updateCount(Long cartNo, int count) {
        Cart cart = em.createQuery("select c from Cart c where c.no=:cartNo", Cart.class)
                .setParameter("cartNo", cartNo)
                .getSingleResult();
        cart.updateCount(count);

    }



    /**
     * 장바구니 삭제
     */
    public void removeCart(Long cartNo) {
        cartRepository.deleteByNo(cartNo);
    }
}
