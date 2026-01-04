package org.asksword.ai.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/ai")
@Slf4j
public class ChatAiController {

    @Autowired
    private ChatClient chatClient;


    @PostMapping("/chat")
    public String chat(String prompt) {
        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }

    @RequestMapping(value = "/chat2")
    public Flux<String> fluxChat(String prompt) {
        log.info("prompt = {}", prompt);
        return chatClient.prompt()
                .user(prompt)
                .stream()
                .content();
    }
}
