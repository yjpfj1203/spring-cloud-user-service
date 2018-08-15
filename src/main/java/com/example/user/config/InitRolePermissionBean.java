package com.example.user.config;

import com.example.user.constants.RedisPrefixConstant;
import com.example.user.constants.RedisService;
import com.example.user.entity.user.Role;
import com.example.user.entity.user.RolePermission;
import com.example.user.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yjpfj1203
 * 初始化redis中的角色与权限的关系，载入系统所有的角色及权限，用于CustomMethodSecurityExpressionRoot在获取用户角色时，判断用户所拥有的权限
 */
@Component
public class InitRolePermissionBean {

    @Autowired
    private RedisService redisService;
    @Autowired
    private PermissionService permissionService;

    /**
     * PostContruct，指定项目启动后执行的方法
     */
    @PostConstruct
    public void init() {
        List<Role> roleList = permissionService.findAllRole();
        List<RolePermission> rolePermissionList = permissionService.findRolePermissionByRoleIds(roleList.stream().map(Role::getId).distinct().collect(Collectors.toList()));
        Map<String, Set<String>> rpMap = rolePermissionList
                .stream()
                .collect(Collectors.groupingBy(rp -> rp.getId().toString(),
                        Collectors.mapping(rpt -> rpt.getPermission().name(), Collectors.toSet())));
        Map<String, Object> map = new HashMap<>();
        rpMap.entrySet().forEach(e -> {
            map.put(e.getKey(), e.getValue());
        });

        redisService.mapPut(RedisPrefixConstant.ROLE_START_INIT, map);
    }
}
