package org.asksword.ai.common.config;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAiConfig {


    @Bean
    public ChatClient chatClient(OpenAiChatModel openAiChatModel) {
        //创建ChstClient实例
        return ChatClient.builder(openAiChatModel)
                .defaultSystem("你是Sword Ai助手，你的名称叫做問剑。")
                //配置日志Advisor
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }
}
