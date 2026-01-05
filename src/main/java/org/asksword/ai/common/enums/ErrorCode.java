package org.asksword.ai.common.enums;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

    // ---------------- 常见 HTTP 状态 ----------------
    BAD_REQUEST(HttpStatus.BAD_REQUEST, "BAD_REQUEST", "参数错误"),
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "UNAUTHORIZED", "未认证"),
    FORBIDDEN(HttpStatus.FORBIDDEN, "FORBIDDEN", "无权限"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "NOT_FOUND", "资源不存在"),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "METHOD_NOT_ALLOWED", "请求方法不允许"),
    UNSUPPORTED_MEDIA_TYPE(HttpStatus.UNSUPPORTED_MEDIA_TYPE, "UNSUPPORTED_MEDIA_TYPE", "不支持的媒体类型"),
    
    // ---------------- 业务错误示例 ----------------
    REDIS_KEY_NOT_FOUND(HttpStatus.NOT_FOUND, "REDIS_KEY_NOT_FOUND", "Redis key不存在"),
    INVALID_PARAM(HttpStatus.BAD_REQUEST, "INVALID_PARAM", "参数校验失败"),

    // ---------------- 兜底 ----------------
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR", "服务器内部错误");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
