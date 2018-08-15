package com.example.user.entity.user;

import com.example.user.controller.user.request.UserRequest;
import com.example.user.controller.user.request.UserUpdateRequest;
import com.example.user.enums.ScopeEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.Set;

/**
 * 用户数据
 */
@Setter
@Getter
@NoArgsConstructor
public class User {
    private Long id;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String tel;
    /**
     * 用户名
     */
    private String username;
    /**
     * 姓名
     */
    private String name;
    /**
     * 密码
     */
    private String password;
    /**
     * 微信号
     */
    private String wechatNumber;
    /**
     * 是否有效
     */
    private boolean active = true;
    /**
     * 是否删除
     */
    private boolean deleted = false;
    /**
     * 创建时间
     */
    private Date createdDate;

    public User(User user){
        setId(user.getId());
        setEmail(user.getEmail());
        setName(user.getName());
     }

    public static User DoFromRequestForm(UserRequest request, String encodedPassword) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setDeleted(false);
        user.setActive(true);
        user.setName(request.getName());
        user.setUsername(request.getUsername());
        user.setPassword(encodedPassword);
        user.setTel(request.getTel());
        user.setWechatNumber(request.getWechatNumber());
        return user;
    }

    public void DoFromRequestForm(UserUpdateRequest request) {
        setEmail(request.getEmail());
        setName(request.getName());
        setTel(request.getTel());
        setWechatNumber(request.getWechatNumber());
    }
}
