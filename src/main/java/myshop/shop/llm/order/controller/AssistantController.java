package myshop.shop.llm.order.controller;

import lombok.RequiredArgsConstructor;
import myshop.shop.dto.member.LoginCheckMemberDto;
import myshop.shop.llm.order.dto.ChatRequestDto;
import myshop.shop.llm.order.dto.ChatResponseDto;
import myshop.shop.llm.order.service.AssistanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/assistant")
@RequiredArgsConstructor
public class AssistantController {

    private final AssistanceService assistanceService;


    /**
     * 일반 체팅
     */
    @PostMapping("/chat")
    public ResponseEntity<?> chat(@RequestBody ChatRequestDto request) {
        Long memberNo = checkLogin();
        if (memberNo == null) return unauthorized();
        ChatResponseDto response = assistanceService.chat(memberNo, request.getMessage());
        return ResponseEntity.ok(response);
    }


    /**
     * 취소 초안 컨펌
     */
    @PostMapping("/drafts/{draftId}/confirm")
    public ResponseEntity<?> confirm(@PathVariable String draftId) {
        Long memberNo = checkLogin();
        if (memberNo == null) return unauthorized();
        return ResponseEntity.ok(Map.of("message", assistanceService.confirm(memberNo, draftId)));
    }


    /**
     * 대화 세션 삭제
     */
    @DeleteMapping("/drafts/{draftId}")
    public ResponseEntity<?> discard(@PathVariable String draftId) {
        Long memberNo = checkLogin();
        if (memberNo == null) return unauthorized();
        assistanceService.discard(memberNo, draftId);
        return ResponseEntity.ok(Map.of("message", "취소 신청을 닫았습니다."));
    }


    /**
     * 로그인 안했을 경우 말풍선
     */
    private ResponseEntity<?> unauthorized() {
        return ResponseEntity.status(401).body(ChatResponseDto.builder()
                .reply("로그인 후 이용해 주세요.")
                .drafts(List.of())
                .build());
    }



    /**
     * 로그인 체크
     * JwtFilter가 SecurityContext에 넣어 둔 인증 회원의 번호
     */
    private Long checkLogin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof LoginCheckMemberDto dto) {
            return dto.getNo();
        }
        return null;
    }
}
