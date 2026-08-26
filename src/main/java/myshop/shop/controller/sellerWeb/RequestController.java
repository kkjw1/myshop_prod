package myshop.shop.controller.sellerWeb;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myshop.shop.dto.cancelRequest.RequestDecisionDto;
import myshop.shop.dto.cancelRequest.SellerManageRequestDto;
import myshop.shop.dto.seller.LoginCheckSellerDto;
import myshop.shop.service.CancelRequestService;
import myshop.shop.service.ReturnRequestService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static myshop.shop.controller.memberWeb.MemberController.SessionConst.LOGIN_SELLER;

@Controller
@RequiredArgsConstructor
@Slf4j
public class RequestController {

    private final CancelRequestService cancelRequestService;
    private final ReturnRequestService returnRequestService;
    /**
     * 취소/반품 관리 폼
     * 판매자 -> 취소/반품 관리
     */
    @GetMapping("/seller/request_manage")
    public String requestManageForm(HttpServletRequest request, Model model) {
        LoginCheckSellerDto loginCheckSellerDto = (LoginCheckSellerDto) request.getSession().getAttribute(LOGIN_SELLER);
        List<SellerManageRequestDto> sellerManageRequestDtoList = cancelRequestService.manageRequestList(loginCheckSellerDto.getNo());
        log.info("sellerManageRequestDtoList={}", sellerManageRequestDtoList);

        model.addAttribute("sellerManageRequestDtoList", sellerManageRequestDtoList);
        return "seller/request/request_manage";
    }


    /**
     * 승인 요청, 거부, 완료처리
     * 취소/반품 관리 처리하기 -> 승인 요청
     * 취소/반품 관리 처리하기 -> 요청 거부
     * 취소/반품 관리 완료처리 -> 최종 완료 처리
     */
    @PostMapping("/seller/request_decision")
    public String requestDecision(@ModelAttribute RequestDecisionDto requestDecisionDto) {
        log.info("requestDecisionDto={}", requestDecisionDto);
        if (requestDecisionDto.getRequestType().equals("취소")) {
            cancelRequestService.decisionCancelRequest(requestDecisionDto);
        }
        else if (requestDecisionDto.getRequestType().equals("반품")) {
            returnRequestService.decisionReturnRequest(requestDecisionDto);
        }
        return "redirect:/seller/request_manage";
    }
}
