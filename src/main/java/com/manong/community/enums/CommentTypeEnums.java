package com.manong.community.enums;

public enum CommentTypeEnums {

    QUESTION(1),
    COMMENT(2);
    private Integer type;

    CommentTypeEnums(Integer type) {
        this.type = type;
    }

    public static boolean isExist(Integer type) {
        for (CommentTypeEnums commentTypeEnums : CommentTypeEnums.values()) {
            if(commentTypeEnums.getType() == type){
                return true;
            }
        }
        return false;
    }

    public Integer getType() {
        return type;
    }
}
