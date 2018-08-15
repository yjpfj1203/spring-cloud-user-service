package com.example.user.controller.user.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserUpdateRequest {
    private String email;
    private String name;
    private String tel;
    private String wechatNumber;

}
