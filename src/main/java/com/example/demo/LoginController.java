package com.example.demo;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class LoginController {

    @GetMapping("/member/login")
    public String login(){
        return "loginForm";
    }

    @GetMapping("/auth/kakao/callback")
    public @ResponseBody String loginCallback(String code){  //코드를 받음. 정상적으로 로그인이 됐다고 판단하면 됨

        //POST방식으로 key-value 데이터를 카카오로 요청
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        //전송할 body 데이터가 key-value 형태의 데이터라고 header를 통해 알려줌
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        //전송할 body
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", "authorization_code");
        params.add("client_id", "57de0cf835661036cca3e9671a47a85f");
        params.add("redirect_uri","http://localhost:8080/auth/kakao/callback");
        params.add("code",code);
        params.add("client_secret", "Q1x38wqObZdkVIcaOVz50BBoLdO49RIG");

        //body와 header 값을 가지고 있는 entity
        HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest = new HttpEntity<>(params, headers);

        ResponseEntity<String> responseEntity = rt.exchange(
                "https://kauth.kakao.com/oauth/token",  //요청 주소
                HttpMethod.POST,
                kakaoTokenRequest,
                String.class  //응답
        );

        return "카카오 토큰 요청 완료 : " + responseEntity;
    }

}
