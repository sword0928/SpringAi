package org.asksword.ai.common.utils;

import org.asksword.ai.common.filter.TraceContext;
import org.slf4j.MDC;

import java.util.Map;
import java.util.function.Supplier;

public class MDCUtil {
    /**
     * 示例：
     * CompletableFuture.runAsync(
     *     MdcUtil.wrap(() -> log.info("async task")), applicationTaskExecutor);
     */

    public static Runnable wrap(Runnable runnable) {
        String traceId = TraceContext.get();

        return () -> {
            TraceContext.set(traceId);
            try {
                runnable.run();
            } finally {
                TraceContext.clear();
            }
        };
    }

    public static <T> Supplier<T> wrap(Supplier<T> supplier) {
        Map<String, String> contextMap = MDC.getCopyOfContextMap();

        return () -> {
            if (contextMap != null) {
                MDC.setContextMap(contextMap);
            }
            try {
                return supplier.get();
            } finally {
                MDC.clear();
            }
        };
    }
}
