package com.asksword.ai.common.utils;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * LocalDateTime 工具类
 * <p>
 * 统一处理 LocalDateTime、LocalDate、LocalTime、Instant、Date 之间的转换，
 * 提供格式化、解析、日期运算、比较等常用方法。
 * <p>
 * JDK 8+ / JDK 21 通用
 */
public class LocalDateTimeUtil {

    // 默认格式
    public static final String DEFAULT_DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";
    public static final String DEFAULT_TIME_FORMAT = "HH:mm:ss";

    private LocalDateTimeUtil() {
        // 工具类私有构造
    }

    // ==================== 1. LocalDateTime 与 String ====================

    /**
     * LocalDateTime 转 String（默认格式 yyyy-MM-dd HH:mm:ss）
     */
    public static String toString(LocalDateTime dateTime) {
        return toString(dateTime, DEFAULT_DATE_TIME_FORMAT);
    }

    /**
     * LocalDateTime 转 String（自定义格式）
     */
    public static String toString(LocalDateTime dateTime, String pattern) {
        if (dateTime == null) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return dateTime.format(formatter);
    }

    /**
     * String 转 LocalDateTime（默认格式 yyyy-MM-dd HH:mm:ss）
     */
    public static LocalDateTime parse(String text) {
        return parse(text, DEFAULT_DATE_TIME_FORMAT);
    }

    /**
     * String 转 LocalDateTime（自定义格式）
     */
    public static LocalDateTime parse(String text, String pattern) {
        if (text == null || text.isEmpty()) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(text, formatter);
    }

    // ==================== 2. LocalDateTime 与 Date ====================

    /**
     * LocalDateTime -> java.util.Date
     */
    public static Date toDate(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = dateTime.atZone(zone).toInstant();
        return Date.from(instant);
    }

    /**
     * java.util.Date -> LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        if (date == null) return null;
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    // ==================== 3. LocalDateTime 与 LocalDate / LocalTime ====================

    public static LocalDate toLocalDate(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        return dateTime.toLocalDate();
    }

    public static LocalTime toLocalTime(LocalDateTime dateTime) {
        if (dateTime == null) return null;
        return dateTime.toLocalTime();
    }

    public static LocalDateTime from(LocalDate date, LocalTime time) {
        if (date == null) return null;
        return LocalDateTime.of(date, time == null ? LocalTime.MIDNIGHT : time);
    }

    // ==================== 4. 获取当前时间 ====================

    public static LocalDateTime now() {
        return LocalDateTime.now();
    }

    public static LocalDate today() {
        return LocalDate.now();
    }

    public static LocalTime currentTime() {
        return LocalTime.now();
    }

    // ==================== 5. 日期运算 ====================

    public static LocalDateTime plusYears(LocalDateTime dateTime, long years) {
        return dateTime == null ? null : dateTime.plusYears(years);
    }

    public static LocalDateTime plusMonths(LocalDateTime dateTime, long months) {
        return dateTime == null ? null : dateTime.plusMonths(months);
    }

    public static LocalDateTime plusDays(LocalDateTime dateTime, long days) {
        return dateTime == null ? null : dateTime.plusDays(days);
    }

    public static LocalDateTime plusHours(LocalDateTime dateTime, long hours) {
        return dateTime == null ? null : dateTime.plusHours(hours);
    }

    public static LocalDateTime plusMinutes(LocalDateTime dateTime, long minutes) {
        return dateTime == null ? null : dateTime.plusMinutes(minutes);
    }

    public static LocalDateTime plusSeconds(LocalDateTime dateTime, long seconds) {
        return dateTime == null ? null : dateTime.plusSeconds(seconds);
    }

    public static LocalDateTime minusYears(LocalDateTime dateTime, long years) {
        return dateTime == null ? null : dateTime.minusYears(years);
    }

    public static LocalDateTime minusMonths(LocalDateTime dateTime, long months) {
        return dateTime == null ? null : dateTime.minusMonths(months);
    }

    public static LocalDateTime minusDays(LocalDateTime dateTime, long days) {
        return dateTime == null ? null : dateTime.minusDays(days);
    }

    public static LocalDateTime minusHours(LocalDateTime dateTime, long hours) {
        return dateTime == null ? null : dateTime.minusHours(hours);
    }

    public static LocalDateTime minusMinutes(LocalDateTime dateTime, long minutes) {
        return dateTime == null ? null : dateTime.minusMinutes(minutes);
    }

    public static LocalDateTime minusSeconds(LocalDateTime dateTime, long seconds) {
        return dateTime == null ? null : dateTime.minusSeconds(seconds);
    }

    // ==================== 6. 日期比较 ====================

    public static boolean isBefore(LocalDateTime dt1, LocalDateTime dt2) {
        if (dt1 == null || dt2 == null) return false;
        return dt1.isBefore(dt2);
    }

    public static boolean isAfter(LocalDateTime dt1, LocalDateTime dt2) {
        if (dt1 == null || dt2 == null) return false;
        return dt1.isAfter(dt2);
    }

    public static boolean isEqual(LocalDateTime dt1, LocalDateTime dt2) {
        if (dt1 == null || dt2 == null) return false;
        return dt1.isEqual(dt2);
    }

    // ==================== 7. 日期差计算 ====================

    public static long betweenDays(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) return 0;
        return ChronoUnit.DAYS.between(start, end);
    }

    public static long betweenHours(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) return 0;
        return ChronoUnit.HOURS.between(start, end);
    }

    public static long betweenMinutes(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) return 0;
        return ChronoUnit.MINUTES.between(start, end);
    }

    public static long betweenSeconds(LocalDateTime start, LocalDateTime end) {
        if (start == null || end == null) return 0;
        return ChronoUnit.SECONDS.between(start, end);
    }

    // ==================== 8. 格式化辅助方法 ====================

    public static String formatDate(LocalDateTime dateTime) {
        return toString(dateTime, DEFAULT_DATE_FORMAT);
    }

    public static String formatTime(LocalDateTime dateTime) {
        return toString(dateTime, DEFAULT_TIME_FORMAT);
    }

    public static String formatDateTime(LocalDateTime dateTime) {
        return toString(dateTime, DEFAULT_DATE_TIME_FORMAT);
    }

    // ==================== 9. 其他辅助 ====================

    /**
     * 获取今天开始时间（00:00:00）
     */
    public static LocalDateTime todayStart() {
        return LocalDate.now().atStartOfDay();
    }

    /**
     * 获取今天结束时间（23:59:59）
     */
    public static LocalDateTime todayEnd() {
        return LocalDateTime.of(LocalDate.now(), LocalTime.MAX);
    }

    /**
     * 获取明天开始时间
     */
    public static LocalDateTime tomorrowStart() {
        return LocalDate.now().plusDays(1).atStartOfDay();
    }

    /**
     * 获取本周第一天（周一）开始时间
     */
    public static LocalDateTime weekStart() {
        return LocalDate.now().with(DayOfWeek.MONDAY).atStartOfDay();
    }

    /**
     * 获取本周最后一天（周日）结束时间
     */
    public static LocalDateTime weekEnd() {
        return LocalDate.now().with(DayOfWeek.SUNDAY).atTime(LocalTime.MAX);
    }

}
