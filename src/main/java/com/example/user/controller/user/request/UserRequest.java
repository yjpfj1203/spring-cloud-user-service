package com.example.user.controller.user.request;

import com.example.user.entity.user.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class UserRequest extends UserUpdateRequest{
    private String password;
    private String username;

}
