package org.asksword.ai.common.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
@Slf4j
public class HttpAccessLogFilter extends OncePerRequestFilter {
    private static final int MAX_BODY_LENGTH_DEV = 2048;
    private static final int MAX_BODY_LENGTH_PROD = 512;

    @Value("${spring.profiles.active}")
    private String env;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        long start = System.currentTimeMillis();

        ContentCachingRequestWrapper req = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper resp = new ContentCachingResponseWrapper(response);

        try {
            filterChain.doFilter(req, resp);
        } finally {
            long cost = System.currentTimeMillis() - start;

            // 放入 MDC
            MDC.put("method", request.getMethod());
            MDC.put("url", request.getRequestURI());
            MDC.put("status", String.valueOf(resp.getStatus()));
            MDC.put("cost", String.valueOf(cost));

            // 判断环境控制返回体长度
            int maxLen = "dev".equals(env) ? MAX_BODY_LENGTH_DEV : MAX_BODY_LENGTH_PROD;
            String responseBody = getResponseBody(resp, maxLen);

            // 只打印成功请求 INFO
            if (resp.getStatus() < 400) {
                log.info("Response: {}", responseBody);
            }

            resp.copyBodyToResponse();

            // 清理 MDC
            MDC.remove("method");
            MDC.remove("url");
            MDC.remove("status");
            MDC.remove("cost");
        }
    }

    private String getResponseBody(ContentCachingResponseWrapper response, int maxLen) {
        byte[] buf = response.getContentAsByteArray();
        if (buf.length == 0) return "";
        String body = new String(buf, StandardCharsets.UTF_8);
        return body.length() > maxLen ? body.substring(0, maxLen) + "...(truncated)" : body;
    }
}
