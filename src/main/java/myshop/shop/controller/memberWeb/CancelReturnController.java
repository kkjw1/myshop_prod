package myshop.shop.controller.memberWeb;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myshop.shop.dto.cancelRequest.ManageCancelReturnDto;
import myshop.shop.dto.cancelRequest.SaveCancelRequestDto;
import myshop.shop.dto.member.LoginCheckMemberDto;
import myshop.shop.dto.returnRequest.SaveReturnRequestDto;
import myshop.shop.service.CancelRequestService;
import myshop.shop.service.ReturnRequestService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@Slf4j
@RequiredArgsConstructor
public class CancelReturnController {

    private final CancelRequestService cancelRequestService;
    private final ReturnRequestService returnRequestService;


    /**
     * 주문 취소 신청
     * 주문 목록/배송 조회 -> 주문 취소 신청
     */
    @PostMapping("/myPage/cancel_request")
    public String cancelRequestForm(@ModelAttribute SaveCancelRequestDto saveCancelRequestDto, RedirectAttributes redirectAttributes) {
        log.info("saveCancelRequestDto={}", saveCancelRequestDto);

        cancelRequestService.saveCancelRequest(saveCancelRequestDto);
        return "redirect:/myPage/orderList";
    }



    /**
     * 반품 신청
     * 주문 목록/배송 조회 -> 반품 신청
     */
    @PostMapping("/myPage/return_request")
    public String returnRequestForm(@ModelAttribute SaveReturnRequestDto saveReturnRequestDto, RedirectAttributes redirectAttributes) {
        log.info("saveReturnRequestDto={}", saveReturnRequestDto);

        returnRequestService.saveReturnRequest(saveReturnRequestDto);
        return "redirect:/myPage/orderList";
    }


    /**
     * 취소/반품 폼
     * 취소/반품 내역
     * 주문 취소 신청 -> 취소/반품 폼
     * 반품 신청 -> 취소/반품 폼
     */
    @GetMapping("/myPage/cancel_return_list")
    public String cancelReturnListForm(@AuthenticationPrincipal LoginCheckMemberDto loginCheckMemberDto, Model model) {
        List<ManageCancelReturnDto> cancelReturnList = returnRequestService.getCancelReturnList(loginCheckMemberDto.getNo());
        log.info("cancelReturnList={}", cancelReturnList);

        model.addAttribute("cancelReturnList", cancelReturnList);
        return "member/mypage/cancel_return_list";
    }

}
