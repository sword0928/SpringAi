package org.asksword.ai.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.asksword.ai.common.utils.CommonConstant;
import org.slf4j.MDC;

/**
 * 全局统一返回体
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // null 字段不返回
public class SwordResponse<T> {

    /**
     * 业务/错误码
     */
    private String code;

    /**
     * HTTP 状态码
     */
    private int status;

    /**
     * 提示信息
     */
    private String message;

    /**
     * 链路 traceId
     */
    private String traceId;

    /**
     * 返回数据
     */
    private T data;

    /**
     * 可扩展附加字段
     */
    private Object extra;

    // 静态构造方法 - 成功返回
    public static <T> SwordResponse<T> success() {
        SwordResponse<T> resp = new SwordResponse<>();
        resp.setCode("SUCCESS");
        resp.setStatus(200);
        resp.setMessage("OK");
        resp.setTraceId(MDC.get(CommonConstant.TRACE_ID));
        return resp;
    }    // 静态构造方法 - 成功返回
    public static <T> SwordResponse<T> success(T data) {
        SwordResponse<T> resp = new SwordResponse<>();
        resp.setCode("SUCCESS");
        resp.setStatus(200);
        resp.setMessage("OK");
        resp.setData(data);
        resp.setTraceId(MDC.get(CommonConstant.TRACE_ID));
        return resp;
    }

    // 静态构造方法 - 失败返回
    public static <T> SwordResponse<T> fail(String code, String message, int status) {
        SwordResponse<T> resp = new SwordResponse<>();
        resp.setCode(code);
        resp.setStatus(status);
        resp.setMessage(message);
        resp.setTraceId(MDC.get(CommonConstant.TRACE_ID));
        return resp;
    }

    // 支持自定义扩展字段
    public SwordResponse<T> withExtra(Object extra) {
        this.setExtra(extra);
        return this;
    }
}
