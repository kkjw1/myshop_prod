package myshop.shop.repository.cart;

import myshop.shop.controller.HomeController;
import myshop.shop.dto.cart.ManageCartDto;
import myshop.shop.service.ItemService;
import myshop.shop.service.ItemService.ItemStockUpdateDto;

import java.util.List;

public interface CartRepositoryCustom {
    List<ManageCartDto> getManageCartList(Long memberNo);

    ManageCartDto getManageCart(Long cartNo);

    ItemStockUpdateDto getItemStockUpdate(Long cartNo);

}
