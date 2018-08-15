package com.example.user.dao.user;

import com.example.user.entity.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserDao {
    List<User> findAll();

    void insertUser(User user);

    User findByEmailOrTel(@Param(value = "email") String email, @Param(value = "tel") String tel);

    User findById(Long id);

    void updateUser(User user);
}
