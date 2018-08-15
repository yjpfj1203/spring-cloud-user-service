package com.example.user.service;

import com.example.user.dao.user.RoleDao;
import com.example.user.dao.user.RolePermissionDao;
import com.example.user.entity.user.Role;
import com.example.user.entity.user.RolePermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private RolePermissionDao rolePermissionDao;

    public List<Role> findAllRole(){
        return roleDao.findAll();
    }

    public List<RolePermission> findRolePermissionByRoleIds(List<Long> roleIdList){
        return rolePermissionDao.findByRoleIdIn(roleIdList);
    }
}
