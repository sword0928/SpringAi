package org.asksword.ai.biz.controller;

import org.asksword.ai.common.NoneMessage;
import org.asksword.ai.common.SwordResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("/redis/set")
    public SwordResponse<NoneMessage> set() {
        redisTemplate.opsForValue().set("name", "demo");
        return SwordResponse.success();
    }

    @GetMapping("/redis/get")
    public SwordResponse<String> get() {
        String name = redisTemplate.opsForValue().get("name");
        return SwordResponse.success(name);
    }

    @GetMapping("/redis/remove")
    public SwordResponse<Boolean> remove() {
        redisTemplate.delete("name");
        return SwordResponse.success();
    }
}