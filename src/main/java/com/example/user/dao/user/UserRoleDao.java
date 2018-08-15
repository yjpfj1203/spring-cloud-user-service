package com.example.user.dao.user;

import com.example.user.entity.user.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface UserRoleDao {
    List<UserRole> findByUserId(@Param(value = "userId") Long userId);
}
