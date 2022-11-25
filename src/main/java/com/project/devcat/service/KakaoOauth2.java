package com.project.devcat.service;

import com.project.devcat.dto.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@RequiredArgsConstructor
@Service
public class KakaoOauth2 {

    public MemberDto getUserInfo(String authorizedCode) throws JSONException {
        /* 1. 인가코드로 엑세스 토큰 받기 */
        String accessToken = getAccessToken(authorizedCode);

        /* 2. 엑세스 토큰으로 카카오 사용자 정보 받기 */
        MemberDto userInfo = getUserInfoByToken(accessToken);

        return userInfo;
    }

    private String getAccessToken(String authorizedCode) throws JSONException {
        /* HTTP 헤더 생성 */
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");

        /* HTTP 바디 생성 */
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "3d2e797ae6e0606abe36357ca1c17721");
        params.add("redirect_uri", "http://localhost:8080/member/kakao/callback");
        params.add("code", authorizedCode);

        /* HTTP 헤더와 바디를 하나의 오브젝트에 담기 */
        RestTemplate rt = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        /* HTTP 요청하기 */
        ResponseEntity<String> response = rt.exchange(
                "https://kauth.kakao.com/oauth/token",
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class
        );

        /* JSON -> 액세스 토큰 파싱 */
        String tokenJson = response.getBody();
        JSONObject rjson = new JSONObject(tokenJson);
        String accessToken = rjson.getString("access_token");

        return accessToken;
    }

    private MemberDto getUserInfoByToken(String accessToken) throws JSONException {
        /* HttpHeader 오브젝트 생성 */
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        /* HttpHeader와 HttpBody를 하나의 오브젝트에 담기 */
        RestTemplate rt = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest = new HttpEntity<>(headers);

        /**
         * Http 요청하기
         * Method : POST
         * response 변수에 응답 받기
         */
        ResponseEntity<String> response = rt.exchange(
                "https://kapi.kakao.com/v2/user/me",
                HttpMethod.POST,
                kakaoProfileRequest,
                String.class
        );

        JSONObject body = new JSONObject(response.getBody());
        Long id = body.getLong("id");
        String email = body.getJSONObject("kakao_account").getString("email");
//        String nickname = body.getJSONObject("properties").getString("nickname");

        return new MemberDto(id, email, "kakaoUser");
    }
}
