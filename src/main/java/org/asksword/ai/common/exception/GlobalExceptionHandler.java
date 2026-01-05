package org.asksword.ai.common.exception;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.asksword.ai.common.enums.ErrorCode;
import org.asksword.ai.common.utils.CommonConstant;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @Value("${spring.profiles.active}")
    private String env;

    /**
     * 处理自定义业务异常
     */
    @ExceptionHandler(BizException.class)
    public ResponseEntity<Map<String, Object>> handleBiz(BizException ex, HttpServletRequest request) {
        ErrorCode error = ex.getErrorCode();
        return handleWithStatus(ex, request, error.getStatus(), error.getCode(), ex.getMessage());
    }

    /**
     * 处理 Controller URL 不存在
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<Map<String, Object>> handle404(NoHandlerFoundException ex, HttpServletRequest request) {
        return handleWithStatus(ex, request, HttpStatus.NOT_FOUND, "NOT_FOUND", "接口不存在");
    }

    /**
     * 处理静态资源不存在
     */
    @ExceptionHandler(org.springframework.web.servlet.resource.NoResourceFoundException.class)
    public ResponseEntity<Map<String, Object>> handleStatic404(
            org.springframework.web.servlet.resource.NoResourceFoundException ex,
            HttpServletRequest request) {
        return handleWithStatus(ex, request, HttpStatus.NOT_FOUND, "NOT_FOUND",
                "静态资源不存在: " + ex.getResourcePath());
    }

    /**
     * 处理常见 400
     */
    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            MissingServletRequestParameterException.class,
            IllegalArgumentException.class
    })
    public ResponseEntity<Map<String, Object>> handle400(Exception ex, HttpServletRequest request) {
        return handleWithStatus(ex, request, HttpStatus.BAD_REQUEST, "BAD_REQUEST", ex.getMessage());
    }

    /**
     * 处理 405 / 415
     */
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ResponseEntity<Map<String, Object>> handle405(HttpRequestMethodNotSupportedException ex, HttpServletRequest request) {
        return handleWithStatus(ex, request, HttpStatus.METHOD_NOT_ALLOWED, "METHOD_NOT_ALLOWED", ex.getMessage());
    }

    @ExceptionHandler({HttpMediaTypeNotSupportedException.class})
    public ResponseEntity<Map<String, Object>> handle415(HttpMediaTypeNotSupportedException ex, HttpServletRequest request) {
        return handleWithStatus(ex, request, HttpStatus.UNSUPPORTED_MEDIA_TYPE, "UNSUPPORTED_MEDIA_TYPE", ex.getMessage());
    }

    /**
     * 兜底异常 500
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handle500(Exception ex, HttpServletRequest request) {
        return handleWithStatus(ex, request, HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_ERROR", ex.getMessage());
    }

    /**
     * 核心日志 + 构建 Response
     */
    private ResponseEntity<Map<String, Object>> handleWithStatus(
            Exception ex,
            HttpServletRequest request,
            HttpStatus status,
            String code,
            String message) {

        long cost = resolveCost(request);

        try {
            MDC.put("method", request.getMethod());
            MDC.put("url", request.getRequestURI());
            MDC.put("status", String.valueOf(status.value()));
            MDC.put("cost", String.valueOf(cost));

            // dev 打完整异常，prod 简化
            if (status.is5xxServerError()) {
                if ("dev".equals(env)) {
                    log.error("HTTP {} error", status.value(), ex);
                } else {
                    log.error("HTTP {} error: {}", status.value(), safeMessage(ex));
                }
            } else {
                log.info("HTTP {} {} - {}", status.value(), code, safeMessage(ex));
            }

            Map<String, Object> body = new HashMap<>();
            body.put("code", code);
            body.put("status", status.value());
            body.put("message", message != null ? message : safeMessage(ex));
            body.put(CommonConstant.TRACE_ID, MDC.get(CommonConstant.TRACE_ID));

            return ResponseEntity.status(status).body(body);
        } finally {
            MDC.clear();
        }
    }

    private long resolveCost(HttpServletRequest request) {
        Object start = request.getAttribute("startTime");
        return start instanceof Long ? System.currentTimeMillis() - (Long) start : -1;
    }

    private String safeMessage(Exception ex) {
        return ex.getMessage() == null ? "" : ex.getMessage();
    }
}
