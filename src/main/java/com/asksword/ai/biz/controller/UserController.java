package com.asksword.ai.biz.controller;

import com.asksword.ai.biz.model.NoneMessage;
import com.asksword.ai.biz.model.SwordResponse;
import com.asksword.ai.biz.model.dto.UserInfo;
import com.asksword.ai.biz.service.UserService;
import com.asksword.ai.common.constant.CommonConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    private SwordResponse<NoneMessage> userRegister(@RequestParam String username, @RequestParam String password) {
        userService.userRegister(username, password);
        return SwordResponse.success();
    }

    @PostMapping("/login")
    private SwordResponse<String> userLogin(@RequestParam String username, @RequestParam String password) {
        String token = userService.userLogin(username, password);
        return SwordResponse.success(token);
    }

    @PostMapping("/check/token")
    private SwordResponse<String> checkToken(@RequestParam String token) {
        userService.checkToken(token);
        return SwordResponse.success();
    }

    @GetMapping("/userinfo")
    private SwordResponse<UserInfo> userInfo(@RequestAttribute(CommonConstant.USER_ID) Long userId) {
        return SwordResponse.success(userService.userInfo(userId));
    }


    @PostMapping("/update")
    private SwordResponse<NoneMessage> updateUserInfo(@RequestAttribute(CommonConstant.USER_ID) Long userId, @RequestBody UserInfo userInfo) {
        userService.updateUserInfo(userId,userInfo);
        return SwordResponse.success();
    }
}
