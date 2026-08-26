package myshop.shop.repository.cart;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import myshop.shop.controller.HomeController;
import myshop.shop.dto.cart.ManageCartDto;
import myshop.shop.entity.item.ItemOption;
import myshop.shop.entity.item.QItemOption;
import myshop.shop.service.ItemService;
import myshop.shop.service.ItemService.ItemStockUpdateDto;

import java.util.List;

import static myshop.shop.entity.QCart.cart;
import static myshop.shop.entity.item.QItem.item;
import static myshop.shop.entity.item.QItemImage.itemImage;
import static myshop.shop.entity.item.QItemOption.itemOption;

@RequiredArgsConstructor
public class CartRepositoryImpl implements CartRepositoryCustom {
    private final JPAQueryFactory queryFactory;
    private final EntityManager em;

    @Override
    public List<ManageCartDto> getManageCartList(Long memberNo) {

        List<ManageCartDto> manageCartDtoList = queryFactory
                .select(Projections.fields(ManageCartDto.class,
                        cart.no.as("cartNo"),
                        item.no.as("itemNo"),
                        itemOption.no.as("itemOptionNo"),
                        item.name,
                        cart.count,
                        item.totalStock,
                        itemOption.optionStock,
                        item.originalPrice,
                        item.discountPer,
                        itemOption.additionalPrice.as("optionPrice"),
                        itemOption.name.as("optionName")))
                .from(cart)
                .leftJoin(cart.item, item)
                .leftJoin(cart.itemOption, itemOption)
                .where(cart.member.no.eq(memberNo))
                .fetch();

        for (ManageCartDto manageCartDto : manageCartDtoList) {
            Long itemNo = manageCartDto.getItemNo();
            String imagePath = em.createQuery("select ii.imageUrl from ItemImage ii where ii.item.no=:itemNo and ii.isMain=true", String.class)
                    .setParameter("itemNo", itemNo)
                    .getSingleResult();
            manageCartDto.setImagePath(imagePath);
        }

        return manageCartDtoList;
    }

    @Override
    public ManageCartDto getManageCart(Long cartNo) {
        ManageCartDto manageCartDto = queryFactory
                .select(Projections.fields(ManageCartDto.class,
                        cart.no.as("cartNo"),
                        item.no.as("itemNo"),
                        itemOption.no.as("itemOptionNo"),
                        item.name,
                        cart.count,
                        item.totalStock,
                        itemOption.optionStock,
                        item.originalPrice,
                        item.discountPer,
                        itemOption.additionalPrice.as("optionPrice"),
                        itemOption.name.as("optionName")))
                .from(cart)
                .leftJoin(cart.item, item)
                .leftJoin(cart.itemOption, itemOption)
                .where(cart.no.eq(cartNo))
                .fetchOne();

        Long itemNo = manageCartDto.getItemNo();
        String imagePath = em.createQuery("select ii.imageUrl from ItemImage ii where ii.item.no=:itemNo and ii.isMain=true", String.class)
                .setParameter("itemNo", itemNo)
                .getSingleResult();
        manageCartDto.setImagePath(imagePath);

        return manageCartDto;
    }

    @Override
    public ItemStockUpdateDto getItemStockUpdate(Long cartNo) {

        return queryFactory
                .select(Projections.fields(ItemStockUpdateDto.class,
                        cart.item.no.as("itemNo"),
                        cart.itemOption.no.as("optionNo"),
                        cart.count))
                .from(cart)
                .where(cart.no.eq(cartNo))
                .fetchOne();
    }

  /*  @Override
    public ManageCartDto getManageCart(DirectOrderDto directOrderDto) {
        ManageCartDto manageCartDto = queryFactory
                .select(Projections.fields(ManageCartDto.class,
                        item.no.as("itemNo"),
                        item.name,
                        itemImage.imageUrl.as("imagePath"),
                        item.totalStock,
                        item.originalPrice
                ))
                .from(itemImage)
                .leftJoin(itemImage.item, item)
                .where(itemImage.no.eq(directOrderDto.getItemImageNo()), item.no.eq(directOrderDto.getItemNo()))
                .fetchOne();

        if (directOrderDto.getItemOptionNo() != null) {
            ItemOption itemOption = queryFactory
                    .selectFrom(QItemOption.itemOption)
                    .where(QItemOption.itemOption.no.eq(directOrderDto.getItemOptionNo()))
                    .fetchOne();

            manageCartDto.setOptionStock(itemOption.getOptionStock());
            manageCartDto.setOptionPrice(itemOption.getAdditionalPrice());
            manageCartDto.setOptionName(itemOption.getName());
        }

        manageCartDto.setItemOptionNo(directOrderDto.getItemOptionNo());
        manageCartDto.setCount(directOrderDto.getCount());

        return manageCartDto;
    }*/

}
