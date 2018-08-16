package com.example.user.constants;

/**
 * 存入redis的key值或是前缀
 */
public interface RedisPrefixConstant {
    /**
     * 用户token的前缀
     */
    String USER_TOKEN_PREMISSIONS = "CUSTOMER_USER_TOKEN_PERMISSIONS_";
    /**
     * 系统所有role与permission的key
     */
    String ROLE_START_INIT = "CONSTANT_ROLES_PERMISSIONS";
}
