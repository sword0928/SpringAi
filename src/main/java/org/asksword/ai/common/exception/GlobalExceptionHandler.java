package org.asksword.ai.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.asksword.ai.common.utils.CommonConstant;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @Value("${spring.profiles.active}")
    private String env;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleException(Exception ex,
                                                               HttpServletRequest request) {

        // 放入 MDC
        MDC.put("method", request.getMethod());
        MDC.put("url", request.getRequestURI());
        MDC.put("status", "500");
        MDC.put("cost", "0");

        // ERROR 日志 + 异常堆栈
        if ("dev".equals(env)) {
            // dev 打完整异常
            log.error("HTTP request failed: {} - {}", ex.getClass().getSimpleName(), ex.getMessage(), ex);
        } else {
            // prod 只打简短异常
            log.error("HTTP request failed: {} - {}", ex.getClass().getSimpleName(), ex.getMessage());
        }

        // 返回前端
        Map<String, Object> body = new HashMap<>();
        body.put("code", 500);
        body.put("message", "Internal Server Error");
        body.put(CommonConstant.TRACE_ID, MDC.get(CommonConstant.TRACE_ID));

        MDC.remove("method");
        MDC.remove("url");
        MDC.remove("status");
        MDC.remove("cost");

        return ResponseEntity.status(500).body(body);
    }
}
