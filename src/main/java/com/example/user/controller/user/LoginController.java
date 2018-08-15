package com.example.user.controller.user;

import com.example.user.controller.user.request.LoginRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "login")
public class LoginController {

    @GetMapping
    public void login(@RequestBody LoginRequest loginRequest){
        System.out.println("ssssssssss");
    }
}
