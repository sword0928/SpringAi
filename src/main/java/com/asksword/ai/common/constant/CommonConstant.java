package com.asksword.ai.common.constant;

public class CommonConstant {

    public static final String TRACE_ID = "traceId";
    public static final String USER_ID = "userId";
    public static final String LOGIN_TOKEN_KEY = "login:token:";

    public static String loginTokenKey(Long userId) {
        return LOGIN_TOKEN_KEY + userId;
    }

}
