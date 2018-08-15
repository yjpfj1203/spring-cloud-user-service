package com.example.user.entity.user;

import com.example.user.enums.PermissionEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
public class RolePermission implements Serializable {
    private Long id;
    private Long roleId;
    private PermissionEnum permission;
}
