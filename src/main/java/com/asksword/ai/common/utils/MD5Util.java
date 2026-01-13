package com.asksword.ai.common.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5 工具类
 * <p>
 * 提供字符串、字节数组的 MD5 加密
 * 支持 32 位 / 16 位输出
 */
public class MD5Util {

    private MD5Util() {
        // 工具类私有构造
    }

    // ==================== 1. 字符串 MD5 ====================

    /**
     * MD5 加密，返回 32 位小写
     */
    public static String md5(String input) {
        if (input == null) return null;
        return md5(input.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * MD5 加密，返回 32 位小写
     */
    public static String md5(byte[] bytes) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] digest = md.digest(bytes);
            return bytesToHex(digest);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("MD5 算法不存在", e);
        }
    }

    /**
     * MD5 加密，返回 16 位小写
     */
    public static String md5_16(String input) {
        String full = md5(input);
        return full == null ? null : full.substring(8, 24);
    }

    /**
     * MD5 加密，返回 16 位小写
     */
    public static String md5_16(byte[] bytes) {
        String full = md5(bytes);
        return full == null ? null : full.substring(8, 24);
    }

    // ==================== 2. 辅助方法：字节数组转十六进制 ====================

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            // & 0xFF 防止负数
            String hex = Integer.toHexString(b & 0xFF);
            if (hex.length() == 1) {
                sb.append('0'); // 补 0
            }
            sb.append(hex);
        }
        return sb.toString();
    }

}
