package com.example.user.service;

import com.example.user.controller.user.request.UserRequest;
import com.example.user.controller.user.request.UserUpdateRequest;
import com.example.user.dao.user.UserDao;
import com.example.user.entity.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserDao userDao;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User saveUser(UserRequest userRequest){
        User userExist = userDao.findByEmailOrTel(userRequest.getEmail(), userRequest.getTel());
        if (userExist != null){
            throw new RuntimeException("用户已经存在了。");
        }
        User user = User.DoFromRequestForm(userRequest, passwordEncoder.encode(userRequest.getPassword()));
        userDao.insertUser(user);
        return user;
    }

    public User findUserByEmailOrTel(String username) {
        User user = userDao.findByEmailOrTel(username, username);
        return user;
    }


    public User findById(Long id) {
        return userDao.findById(id);
    }

    public User updateUser(Long id, UserUpdateRequest userUpdateRequest) {
        User user = userDao.findById(id);
        user.DoFromRequestForm(userUpdateRequest);
        userDao.updateUser(user);
        return user;
    }
}
