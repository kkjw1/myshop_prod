package myshop.shop.repository.order;

import myshop.shop.controller.HomeItemController;
import myshop.shop.dto.order.DirectOrderDto;

public interface OrderRepositoryCustom {
    DirectOrderDto getDirectOrder(HomeItemController.CheckDirectOrderDto checkDirectOrderDto);
}
