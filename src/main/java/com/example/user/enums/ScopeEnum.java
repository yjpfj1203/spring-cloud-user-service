package com.example.user.enums;

/**
 * scope
 */
public enum ScopeEnum {
    ARTICLE(0, "文章"),
    USER(1, "人员管理");

    private int index;
    private String desc;

    ScopeEnum(int index, String desc){
        this.index = index;
        this.desc = desc;
    }

    public int getIndex(){
        return this.index;
    }


}
