package com.asksword.ai.biz.controller;

import com.asksword.ai.biz.model.SwordResponse;
import com.asksword.ai.common.constant.CommonConstant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @GetMapping("/list")
    private SwordResponse<?> getArticleList(@RequestAttribute(CommonConstant.USER_ID) Long userId) {
        return SwordResponse.success();
    }
}
