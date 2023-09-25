package com.blockcoding.ezon.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
@Slf4j
@CrossOrigin(origins="*")
public class oAuthController {
    @GetMapping("/whaleSpace/getUserInfo")
    public Object getUserInfo(@RequestParam("code") String token) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        String BASE_URL = "https://api.whalespace.io/oauth2/v1.1/userinfo";
        String TOKEN = "Bearer " + token;
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", TOKEN);
        HttpEntity httpRequest = new HttpEntity(headers);
        ResponseEntity<String> response = restTemplate.exchange(
                BASE_URL,
                HttpMethod.GET,
                httpRequest,
                String.class
        );
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> userMap = mapper.readValue(response.getBody(), Map.class);
        Object nameObject = userMap.get("name");
        Map<String, Object> nameMap = mapper.convertValue(nameObject, Map.class);
        Object fullName = nameMap.get("fullName");
        log.info(fullName.toString() + "님이 로그인 하셨습니다.");
        return fullName;
    }

    @GetMapping("/whaleSpace/Logout")
    public void writeLogoutLog(@RequestParam("userName") String userName){
        log.info(userName + "님이 로그아웃 하셨습니다.");
    }
}
