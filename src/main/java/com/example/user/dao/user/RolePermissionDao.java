package com.example.user.dao.user;

import com.example.user.entity.user.RolePermission;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface RolePermissionDao {
    List<RolePermission> findByRoleId(@Param(value = "roleId") Long roleId);

    List<RolePermission> findByRoleIdIn(@Param(value = "roleIdList") List<Long> roleIdList);
}
