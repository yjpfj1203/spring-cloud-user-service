package com.example.user.controller.user;

import com.example.user.controller.user.model.OauthTokenResponse;
import com.example.user.controller.user.model.TokenResponse;
import com.example.user.controller.user.request.LoginRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/user/login")
public class LoginController {
    @Value(value = "${server.port}")
    private String serverPort;

    @PostMapping
    public TokenResponse login(@RequestBody LoginRequest loginRequest){
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:"+serverPort+"/oauth/token?" +
                "client_id=web-demo"+"&username="+loginRequest.getUsername()+"&password="+loginRequest.getPassword()+"&grant_type=password";
        OauthTokenResponse oauthTokenResponse = restTemplate.postForObject(url, "", OauthTokenResponse.class);

        return new TokenResponse(oauthTokenResponse.getAccessToken(), oauthTokenResponse.getRefreshToken());
    }
}
