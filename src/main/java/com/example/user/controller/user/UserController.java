package com.example.user.controller.user;

import com.example.user.controller.model.CurrentUser;
import com.example.user.controller.user.request.UserRequest;
import com.example.user.controller.user.request.UserUpdateRequest;
import com.example.user.dao.user.UserDao;
import com.example.user.entity.user.User;
import com.example.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserDao userDao;

    @PreAuthorize("hasAnyPermission('USER_CRUD', 'USER_READ')")
    @GetMapping
    public List<User> findAll(@AuthenticationPrincipal CurrentUser user){
        return userDao.findAll();
    }

    @GetMapping(value = "/test")
    public String testNoToken(@AuthenticationPrincipal CurrentUser user){
        return "无token验证OK";
    }

    @PreAuthorize("hasPermission('USER_CRUD')")
    @PostMapping
    public Long saveUser(@RequestBody UserRequest userRequest,
                         @AuthenticationPrincipal CurrentUser user){
        return userService.saveUser(userRequest);
    }

    /**
     * 用户有007权限
     * @return
     */
    @PreAuthorize("hasPermission('USER_CRUD')")
    @PutMapping(value = "/{id}")
    public String updateUser(@PathVariable(value = "id") Long id,
                             @RequestBody UserUpdateRequest userUpdateRequest){
        User user = userService.updateUser(id, userUpdateRequest);
        return "you have update user. username is " + user.getUsername();
    }

    @PreAuthorize("isSelf(#id)")
    @GetMapping(value = "/self/{id}")
    public String isMember(@PathVariable(value = "id") Long id){
        return "你是当前登录者。";
    }

}
