package com.example.user.entity.user;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

/**
 * 角色实体类
 */
@Setter
@Getter
@NoArgsConstructor
public class Role implements Serializable {
    private Long id;
    /**
     * 角色名称
     */
    private String name;
    /**
     * 是否作废
     */
    private boolean active = true;
    /**
     * 角色描述
     */
    private String description;
 }
