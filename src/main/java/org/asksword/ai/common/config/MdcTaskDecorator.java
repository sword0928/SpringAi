package org.asksword.ai.common.config;

import org.asksword.ai.common.filter.TraceContext;
import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;

import java.util.Map;

public class MdcTaskDecorator implements TaskDecorator {

    @Override
    public Runnable decorate(Runnable runnable) {
        String traceId = TraceContext.get();
        Map<String, String> mdc = MDC.getCopyOfContextMap();

        return () -> {
            if (traceId != null) {
                TraceContext.set(traceId);   // ⭐ 同步两套
            }
            if (mdc != null) {
                MDC.setContextMap(mdc);
            }

            try {
                runnable.run();
            } finally {
                TraceContext.clear();
                MDC.clear();
            }
        };
    }
}
