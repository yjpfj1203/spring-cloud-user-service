package com.example.user.dao.user;

import com.example.user.entity.user.Role;
import com.example.user.entity.user.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 用户角色持久化接口
 * @author yjpfj1203
 * 2018-08-13
 */
@Mapper
@Component
public interface RoleDao {
    /**
     * 用户id获取用户角色
     * @param userId
     * @return 角色
     */
    List<Role> findByUserId(@Param(value = "userId") Long userId);

    /**
     * 系统所有角色
     * @return 角色列表
     */
    List<Role> findAll();
}
