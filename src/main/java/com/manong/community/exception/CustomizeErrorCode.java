package com.manong.community.exception;

public enum CustomizeErrorCode implements ICustomizeErrorCode {

    QUESTION_NOT_FOUND(2001,"您查找的问题找不到，麻烦你再试试"),
    TAEGET_PAEAM_NOT_FOUND(2002,"未选择任何问题或评论进行回复"),
    NO_LOGIN(2003,"您的操作需要登录，请登录后重试"),
    SYS_ERROR(2004,"服务器开小差了，麻烦你再试试"),
    TYPE_PAEAM_ERROR(2005,"评论类型错误或不存在"),
    COMMENT_NOT_FOUND(2006,"不好意思评论未找到，请您换一个试试"),
    COMMENT_IS_EMPTY(2007,"代码君，您输入的内容为空啦"),
    READ_NOTIFICATION_FAIL(2008,"代码君，您是想看别人的问题吗？"),
    NOTIFICATION_NOT_FOUND(2009,"代码君，你的通知没有找到"),
    IMAGE_UPLOAD_FAIL(20010,"代码君，图片加载失败");

    private String message;
    private Integer code;

    CustomizeErrorCode(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }
}
