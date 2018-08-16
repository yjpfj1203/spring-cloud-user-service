package com.example.user.entity.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 用户拥有的角色
 */
@Setter
@Getter
@NoArgsConstructor
public class UserRole implements Serializable {
    private Long id;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 角色id
     */
    private Long roleId;
}
