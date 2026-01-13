package com.asksword.ai.common.enums;

public enum ErrorCodeEnum {


    // ---------------- 业务错误示例 ----------------
    INVALID_PARAM(10000, "INVALID_PARAM", "参数校验失败"),
    USER_EXISTS(10001, "USER_EXISTS", "用户已存在"),
    USER_NOT_FOUND(10002, "USER_NOT_FOUND", "用户不存在"),
    PASSWORD_ERROR(10003, "PASSWORD_ERROR", "密码错误"),
    PARAMETER_NOT_EMPTY(10004, "PARAMETER_NOT_EMPTY", "参数不能为空"),
    ;

    private final Integer status;
    private final String code;
    private final String message;

    ErrorCodeEnum(Integer status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
