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
@RequestMapping(value = "departments")
public class DepartmentController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserDao userDao;

    @PreAuthorize("hasPermission('USER_DEPT_CRUD')")
    @GetMapping
    public String findAll(){
        return "you have department's view permission";
    }

    @PreAuthorize("hasPermission('USER_DEPT_CRUD')")
    @PostMapping
    public String save(){
        return "you have department's creation permission";
    }

}
