package com.asksword.ai.common.utils;

import com.asksword.ai.common.constant.CommonConstant;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

    /**
     * ===================== 配置区 =====================
     */

    // ⚠️ 生产必须放配置中心 / 环境变量
    private static final String SECRET = "sword0928";

    // Access Token：12 小时
    private static final long ACCESS_TOKEN_EXPIRE = 60 * 60 * 12 * 1000L;

    // Refresh Token：7 天
    private static final long REFRESH_TOKEN_EXPIRE = 7 * 24 * 60 * 60 * 1000L;

    private static final Algorithm ALGORITHM = Algorithm.HMAC256(SECRET);

    /**
     * ===================== Token 类型 =====================
     */

    public static final String TOKEN_TYPE_ACCESS = "ACCESS";
    public static final String TOKEN_TYPE_REFRESH = "REFRESH";

    /**
     * ===================== 生成 Token =====================
     */

    public static String generateAccessToken(Map<String, Object> userInfoMap) {
        return generateToken(userInfoMap, TOKEN_TYPE_ACCESS, ACCESS_TOKEN_EXPIRE);
    }

    public static String generateRefreshToken(Map<String, Object> userInfoMap) {
        return generateToken(userInfoMap, TOKEN_TYPE_REFRESH, REFRESH_TOKEN_EXPIRE);
    }

    private static String generateToken(Map<String, Object> userInfoMap, String type, long expireMillis) {
        long now = System.currentTimeMillis();
        return JWT.create()
                .withSubject(type)
                .withClaim("userInfo", userInfoMap)
                .withExpiresAt(new Date(now + expireMillis))
                .sign(ALGORITHM);
    }

    /**
     * ===================== 解析 & 校验 =====================
     */

    public static DecodedJWT verify(String token) {
        JWTVerifier verifier = JWT.require(ALGORITHM).build();
        return verifier.verify(token);
    }

    public static boolean isExpired(String token) {
        try {
            verify(token);
            return false;
        } catch (TokenExpiredException e) {
            return true;
        }
    }

    /**
     * ===================== 读取 Claim =====================
     */

    public static Long getUserId(String token) {
        return (Long) verify(token).getClaim("userInfo").asMap().get(CommonConstant.USER_ID);
    }

    public static String getUsername(String token) {
        return (String) verify(token).getClaim("userInfo").asMap().get("userName");
    }

    public static String getTokenType(String token) {
        return verify(token).getSubject();
    }

    /** ===================== 刷新 Token ===================== */

    /**
     * 使用 Refresh Token 刷新 Access Token
     */
    public static String refreshAccessToken(String refreshToken) {
        DecodedJWT jwt = verify(refreshToken);

        if (!TOKEN_TYPE_REFRESH.equals(jwt.getSubject())) {
            throw new RuntimeException("非法 Refresh Token");
        }
        Map<String, Object> userInfo = jwt.getClaim("userInfo").asMap();
        return generateAccessToken(userInfo);
    }

    /**
     * ===================== 一次性返回 Token 对 =====================
     */

    public static Map<String, String> generateTokenPair(Long userId, String username) {
        HashMap<String, Object> userInfo = new HashMap<>();
        userInfo.put(CommonConstant.USER_ID, userId);
        userInfo.put("userName", username);
        Map<String, String> tokenMap = new HashMap<>();
        tokenMap.put("accessToken", generateAccessToken(userInfo));
        tokenMap.put("refreshToken", generateRefreshToken(userInfo));
        return tokenMap;
    }
}
