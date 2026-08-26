package myshop.shop.controller.sellerWeb;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myshop.shop.controller.memberWeb.MemberController;
import myshop.shop.dto.delivery.BatchUpdateOrderDeliveryDto;
import myshop.shop.dto.delivery.OrderDeliveryDto;
import myshop.shop.dto.delivery.OrderItemDeliveryDto;
import myshop.shop.dto.delivery.UpdateDeliveryDto;
import myshop.shop.dto.seller.LoginCheckSellerDto;
import myshop.shop.service.DeliveryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static myshop.shop.controller.memberWeb.MemberController.SessionConst.LOGIN_SELLER;

@Controller
@RequiredArgsConstructor
@Slf4j
public class DeliveryController {

    private final DeliveryService deliveryService;


    /**
     * 주문/배송 관리 폼
     * 판매자 센터 -> 주문/배송 관리 폼
     */
    @GetMapping("/seller/order_delivery")
    public String orderDelivery(HttpServletRequest request, Model model) {
        LoginCheckSellerDto loginCheckSellerDto = (LoginCheckSellerDto) request.getSession().getAttribute(LOGIN_SELLER);
        Long sellerNo = loginCheckSellerDto.getNo();

        List<OrderDeliveryDto> orderDeliveryDtoList = deliveryService.findAllDelivery(sellerNo);

        log.info("orderDeliveryDtoList={}", orderDeliveryDtoList);
        model.addAttribute("orderDeliveryDtoList", orderDeliveryDtoList);
        return "seller/delivery/order_delivery";
    }


    /**
     * 주문 상품들 가져오기
     * 주문/배송 관리 폼 -> 관리
     */
    @GetMapping("/seller/order_delivery/manage")
    @ResponseBody
    public List<OrderItemDeliveryDto> manageOrderDelivery(@RequestParam("orderNo") Long orderNo) {
        return deliveryService.manageOrderDelivery(orderNo);
    }


    /**
     * 상품 배송사항 변경
     * 관리 -> 변경사항 저장
     */
    @PostMapping("/seller/order_delivery/update")
    @ResponseBody
    public String updateDelivery(@RequestBody List<UpdateDeliveryDto> updateDeliveryDtoList) {
        log.info("updateDeliveryDtoList={}", updateDeliveryDtoList);
        deliveryService.updateOrderItemDelivery(updateDeliveryDtoList);
        return "ok";
    }


    /**
     * 주문 상태 일괄 변경
     * 주문/배송 관리 -> 선택 주문 상태 처리
     */
    @PostMapping("/seller/order_delivery/batch_update")
    @ResponseBody
    public String batchUpdateOrderDelivery(@RequestBody BatchUpdateOrderDeliveryDto batchUpdateOrderDeliveryDto) {
        log.info("BatchUpdateOrderDeliveryDto={}", batchUpdateOrderDeliveryDto);
        deliveryService.updateOrderStatus(batchUpdateOrderDeliveryDto);
        return "ok";
    }
}
