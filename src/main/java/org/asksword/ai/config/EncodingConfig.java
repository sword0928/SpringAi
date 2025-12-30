package org.asksword.ai.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;


@Configuration
public class EncodingConfig {

    @Bean
    public FilterRegistrationBean<CharacterEncodingFilter> encodingFilter() {
        // 创建Spring提供的编码过滤器
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8"); // 强制请求使用UTF-8编码
        filter.setForceEncoding(true); // 强制响应也使用UTF-8编码

        // 将过滤器注册到容器，并设置对所有请求有效
        FilterRegistrationBean<CharacterEncodingFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(filter);
        registrationBean.addUrlPatterns("/*"); // 过滤所有路径
        registrationBean.setOrder(1); // 设置过滤器的执行顺序（数字越小优先级越高）

        return registrationBean;
    }
}