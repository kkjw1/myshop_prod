package myshop.shop.controller.memberWeb;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import myshop.shop.dto.inquiry.CheckInquiryDto;
import myshop.shop.dto.inquiry.SaveInquiryDto;
import myshop.shop.dto.member.LoginCheckMemberDto;
import myshop.shop.service.InquiryService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

import static myshop.shop.controller.memberWeb.MemberController.SessionConst.LOGIN_MEMBER;

@Controller
@RequiredArgsConstructor
@Slf4j
public class InquiryController {

    private final InquiryService inquiryService;


    /**
     * 문의하기
     * 주문 목록/배송 조회 -> 문의하기
     * 취소/반품 내역 -> 문의하기
     */
    @PostMapping("/myPage/inquiry")
    public String inquiry(@ModelAttribute SaveInquiryDto saveInquiryDto) {
        log.info("saveInquiryDto={}", saveInquiryDto);
        inquiryService.saveInquiry(saveInquiryDto);
        return "redirect:/myPage/inquiry";
    }


    /**
     * 상품/기타 문의하기
     * 아이템 상세 -> 문의하기
     */
    @PostMapping("/inquiry")
    public String inquiry2(@ModelAttribute SaveInquiryDto saveInquiryDto) {
        log.info("inquiry2 saveInquiryDto={}", saveInquiryDto);
        inquiryService.saveProductInquiry(saveInquiryDto);
        return "redirect:/myPage/inquiry";
    }


    /**
     * 문의내역 확인 폼
     */
    @GetMapping("/myPage/inquiry")
    public String inquiryListForm(@AuthenticationPrincipal LoginCheckMemberDto loginCheckMemberDto, Model model) {

        List<CheckInquiryDto> checkInquiryDtoList = inquiryService.getInquiryList(loginCheckMemberDto.getNo());

        model.addAttribute("checkInquiryDtoList", checkInquiryDtoList);
        return "member/mypage/inquiry_list";
    }
}
