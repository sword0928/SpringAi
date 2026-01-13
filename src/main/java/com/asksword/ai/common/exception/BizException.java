package com.asksword.ai.common.exception;

import com.asksword.ai.common.enums.ErrorCodeEnum;

public class BizException extends RuntimeException {

    private final ErrorCodeEnum errorCodeEnum;

    public BizException(ErrorCodeEnum errorCodeEnum) {
        super(errorCodeEnum.getMessage());
        this.errorCodeEnum = errorCodeEnum;
    }

    public BizException(ErrorCodeEnum errorCodeEnum, String customMessage) {
        super(customMessage);
        this.errorCodeEnum = errorCodeEnum;
    }

    public ErrorCodeEnum getErrorCode() {
        return errorCodeEnum;
    }
}