package com.example.user.enums;

/**
 * 权限集合
 */
public enum PermissionEnum {
    USER_READ("查看人员信息", ScopeEnum.USER),
    USER_CRUD("人员管理", ScopeEnum.USER),
    USER_DEPT_CRUD("部门管理", ScopeEnum.USER),
    USER_ROLE_CRUD("角色管理", ScopeEnum.USER),
    REVIEW_CRUD("审核流程管理", ScopeEnum.USER),
    ARTICLE_CRUD("文稿管理", ScopeEnum.ARTICLE);

    private String name;
    private ScopeEnum scope;

    PermissionEnum(String name, ScopeEnum scope){
        this.scope = scope;
        this.name = name;
    }

    public String getName() {
        return name;
    }

     public ScopeEnum getScope(){
        return this.scope;
     }
}
