package com.example.user.controller.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;

/**
 * 当前登录用户，用于获取token中的用户数据
 */
@Setter
@Getter
@NoArgsConstructor
public class CurrentUser implements Serializable {
    private Long id;
    private String email;
    private String name;
    private String tel;
    private String wechatNumber;

}
