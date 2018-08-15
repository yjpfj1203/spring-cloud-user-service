package com.example.user.config;

import com.example.user.constants.RedisPrefixConstant;
import com.example.user.constants.RedisService;
import com.example.user.controller.model.CurrentUser;
import com.example.user.util.AppContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;
import org.springframework.util.CollectionUtils;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author yjpfj1203
 * 权限注解主体，可以自定义权限
 */
public class CustomMethodSecurityExpressionRoot
        extends SecurityExpressionRoot implements MethodSecurityExpressionOperations {
    //这个没办法用@autowired注入
    private final RedisService redisService = (RedisService) AppContextUtil.getBean("redisService");

    private Object filterObject;
    private Object returnObject;
    private Object target;

    public CustomMethodSecurityExpressionRoot(Authentication authentication) {
        super(authentication);
    }

    /**
     * 判断是否是本人
     * @param userId
     * @return
     */
    public boolean isSelf(Long userId) {
        CurrentUser currentUser = (CurrentUser)authentication.getPrincipal();
        return currentUser.getId().toString().compareTo(userId.toString()) == 0;
    }

    /**
     * 自定义hasRole，因为SecurityExpressionRoot中的hasRole是final的，无法重写
     * @param role
     * @return
     */
    public boolean hasMyRole(String role) {
        Set<String> roleSet = userRoleSet();
        if (CollectionUtils.isEmpty(roleSet)){
            return false;
        }

        return roleSet.contains(role);
    }

    /**
     * 是否拥有指定权限
     * @param permission
     * @return
     */
    public boolean hasPermission(Object permission) {
        try {
            Set<String> userPermissionSet = userPermissionSet();
            return userPermissionSet.contains(permission.toString());
        } catch (AccessDeniedException e) {
            return false;
        }
    }

    /**
     * 是否拥有任意权限
     * @param authorities
     * @return
     */
    public boolean hasAnyPermission(String... authorities) {
        try {
            Set<String> userPermissionSet = userPermissionSet();
            return userPermissionSet.stream().anyMatch(p -> Arrays.asList(authorities).contains(p));
        } catch (AccessDeniedException e) {
            return false;
        }
    }

    /**
     * 获取用户所有权限
     * @return
     */
    private Set<String> userPermissionSet(){
        Set<String> roleSet = userRoleSet();
        if (CollectionUtils.isEmpty(roleSet)){
            return new HashSet<>();
        }

        Set<String> permissionSet = roleSet
                .stream()
                .flatMap(r -> {
                    Set<String> permSet = (Set<String>)redisService.mapGet(RedisPrefixConstant.ROLE_START_INIT).get(r);
                    return permSet.stream();
                })
                .distinct()
                .collect(Collectors.toSet());
        return permissionSet;
    }

    /**
     * redis中获取用户角色
     * @return
     */
    private Set<String> userRoleSet(){
        CurrentUser currentUser = (CurrentUser)authentication.getPrincipal();
        //获取系统所有角色及权限
        Set<Object> roleSet = redisService.setGet(RedisPrefixConstant.USER_TOKEN_PREMISSIONS + currentUser.getId() + "_ROLE");
        return roleSet.stream().map(o -> o.toString()).collect(Collectors.toSet());
    }

    public void setFilterObject(Object filterObject) {
        this.filterObject = filterObject;
    }

    public Object getFilterObject() {
        return this.filterObject;
    }

    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    public Object getReturnObject() {
        return this.returnObject;
    }

    void setThis(Object target) {
        this.target = target;
    }

    public Object getThis() {
        return this.target;
    }
}
