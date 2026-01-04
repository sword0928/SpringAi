package org.asksword.ai.common.filter;

import org.asksword.ai.common.utils.CommonConstant;
import org.slf4j.MDC;

public class TraceContext {

    private static final ThreadLocal<String> TRACE_ID = new ThreadLocal<>();

    public static void set(String traceId) {
        TRACE_ID.set(traceId);
        MDC.put(CommonConstant.TRACE_ID, traceId);   // ⭐ 同步进 MDC
    }

    public static String get() {
        return TRACE_ID.get();
    }

    public static void clear() {
        TRACE_ID.remove();
        MDC.remove(CommonConstant.TRACE_ID);         // ⭐ 一起清理
    }
}
