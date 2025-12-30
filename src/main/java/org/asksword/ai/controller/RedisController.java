package org.asksword.ai.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedisController {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @GetMapping("/redis/set")
    public String set() {
        redisTemplate.opsForValue().set("name", "demo");
        return "ok";
    }

    @GetMapping("/redis/get")
    public String get() {
        return redisTemplate.opsForValue().get("name");
    }
}