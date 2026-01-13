package com.asksword.ai.biz.service;

import com.asksword.ai.biz.model.dto.UserInfo;
import com.asksword.ai.biz.model.entity.UserEntity;
import com.asksword.ai.biz.repository.UserRepository;
import com.asksword.ai.common.constant.CommonConstant;
import com.asksword.ai.common.enums.ErrorCodeEnum;
import com.asksword.ai.common.enums.YesOrNoEnum;
import com.asksword.ai.common.exception.BizException;
import com.asksword.ai.common.utils.*;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 用户注册
     */
    public void userRegister(String username, String password) {
        if (ObjectUtils.isEmpty(username) || ObjectUtils.isEmpty(password) || username.length() > 20) {
            throw new BizException(ErrorCodeEnum.INVALID_PARAM);
        }
        UserEntity userByUserName = userRepository.getUserByUserName(username);
        if (userByUserName != null) {
            throw new BizException(ErrorCodeEnum.USER_EXISTS);
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setUserId(SnowflakeIdUtil.nextId())
                .setUsername(username)
                .setPassword(MD5Util.md5(password))
                .setRecordStatus(YesOrNoEnum.YES.getCode())
                .setCreateTime(LocalDateTimeUtil.now())
                .setUpdateTime(LocalDateTimeUtil.now());
        userRepository.register(userEntity);
    }

    /**
     * 用户登录
     *
     * @return token
     */
    public String userLogin(String username, String password) {
        UserEntity loginUser = userRepository.getUserByUserName(username);
        if (ObjectUtils.isEmpty(loginUser)) {
            throw new BizException(ErrorCodeEnum.USER_EXISTS);
        }
        if (!MD5Util.md5(password).equals(loginUser.getPassword())) {
            throw new BizException(ErrorCodeEnum.PASSWORD_ERROR);
        }
        HashMap<String, Object> claims = new HashMap<>();
        claims.put(CommonConstant.USER_ID, loginUser.getUserId());
        claims.put("username", loginUser.getUsername());
        String accessToken = JwtUtil.generateAccessToken(claims);
        String key = CommonConstant.loginTokenKey(loginUser.getUserId());
        redisTemplate.opsForValue().set(key, accessToken, 12, TimeUnit.HOURS);
        return accessToken;
    }

    /**
     * 验证token
     */
    public void checkToken(String token) {
        // JWT 校验
        DecodedJWT jwt = JwtUtil.verify(token);
        Long userId = jwt.getClaim("uid").asLong();
        // Redis 校验（是否被踢）
        String redisToken = (String) redisTemplate.opsForValue()
                .get(CommonConstant.loginTokenKey(userId));
        if (redisToken == null) {
            throw new RuntimeException("登录已失效，请重新登录");
        }
        if (!redisToken.equals(token)) {
            throw new RuntimeException("账号在其他地方登录，已被踢下线");
        }
    }

    public UserInfo userInfo(Long userId) {
        UserEntity userEntity = userRepository.selectUserInfoByUserId(userId);
        return BeanCopyUtil.copy(userEntity, UserInfo.class);
    }

    public void updateUserInfo(Long userId, UserInfo userInfo) {
        if (ObjectUtils.isEmpty(userInfo.getNickname()) || ObjectUtils.isEmpty(userInfo.getEmail())) {
            throw new BizException(ErrorCodeEnum.PARAMETER_NOT_EMPTY);
        }
        UserEntity userEntity = userRepository.selectUserInfoByUserId(userId);
        userEntity.setNickname(userInfo.getNickname())
                .setEmail(userInfo.getEmail())
                .setUsername(ObjectUtils.isEmpty(userInfo.getUsername()) ? userEntity.getUsername() : userInfo.getUsername())
                .setUpdateTime(LocalDateTimeUtil.now());
        userRepository.update(userEntity);
    }
}
