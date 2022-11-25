package com.project.devcat.service;


import com.project.devcat.domain.Member;
import com.project.devcat.domain.MemberRoleEnum;
import com.project.devcat.dto.MemberDto;
import com.project.devcat.dto.SignupDto;
import com.project.devcat.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final KakaoOauth2 kakaoOauth2;


    public void saveNewMember(SignupDto.request signupRequest) {
        signupRequest.setPassword(passwordEncoder.encode(signupRequest.getPassword()));
        Member member = Member.builder()
                .email(signupRequest.getEmail())
                .password(signupRequest.getPassword())
                .nickname(signupRequest.getNickname())
                .role(MemberRoleEnum.MEMBER)
                .build();
        memberRepository.save(member);
    }

    public void kakaoLogin(String authorizedCode) throws JSONException {
        /* 카카오 OAuth2를 이용해 카카오 사용자 정보 조회 */
        MemberDto accountInfo = kakaoOauth2.getUserInfo(authorizedCode);
        Long id = accountInfo.getId();
        String email = accountInfo.getEmail();
        String nickname = accountInfo.getNickname();


        /**
         * DB에서 회원 정보 가져오기
         * 회원 ID = 카카오 nickname
         * 패스워드 = 카카오 ID + randomUUID
         */
//        String username = nickname + "(카카오)";
        String password = id + UUID.randomUUID().toString();

        /* DB에 중복된 KakaoId 가 있는지 확인 */
        Member kakaoUser = memberRepository.findByKakaoId(id).orElse(null);

        /* 카카오 정보로 회원가입 */
        if (kakaoUser == null) {
            if (memberRepository.existsByEmail(email)) {
                Member member = memberRepository.findByEmail(email).orElse(null);
                kakaoUser = member;
                kakaoUser.setKakaoId(id);
            } else {
                /* 패스워드 인코딩 */
                String encodedPassword = passwordEncoder.encode(password);
                /* ROLE = 사용자 */
                MemberRoleEnum roleEnum = MemberRoleEnum.MEMBER;

                kakaoUser = Member.builder()
                        .email(email)
                        .role(roleEnum)
                        .nickname(nickname)
                        .password(encodedPassword)
                        .kakaoId(id)
                        .build();
            }
            kakaoUser = memberRepository.save(kakaoUser);
        }

        /* 로그인 처리 */
        UserDetailsImpl userDetails = new UserDetailsImpl(kakaoUser);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }
}
