package com.project.devcat.controller;

import com.project.devcat.dto.SignupDto;
import com.project.devcat.repository.MemberRepository;
import com.project.devcat.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


@RequiredArgsConstructor
@Controller
public class MemberController {

    private final MemberService memberService;

    private final MemberRepository memberRepository;

    @PostMapping("/signup")
    public String signupSubmit(SignupDto.request request) {
        memberService.saveNewMember(request);
        return "redirect:/login";
    }

    @PostMapping("/login")
    public String memberLogin(String email) {
        memberRepository.findByEmail(email);
        return "redirect:/";
    }

    /* 카카오 로그인 */
    @GetMapping("/member/kakao/callback")
    public String kakaoLogin(String code) throws JSONException {
        /* authorizeCode : 카카오 서버로부터 받은 인가 코드 */
        memberService.kakaoLogin(code);
        return "redirect:/";
    }

}
