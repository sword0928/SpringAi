package com.asksword.ai.common.utils;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * 实体拷贝工具类
 * <p>
 * 支持：
 * 1️⃣ 单对象拷贝
 * 2️⃣ 列表批量拷贝
 * 3️⃣ 支持 null 值控制（可选择是否拷贝 null）
 * 4️⃣ JDK 8+ / JDK 21 兼容
 */
public class BeanCopyUtil {

    private BeanCopyUtil() {
        // 工具类私有构造
    }

    // ==================== 1. 单对象拷贝 ====================

    /**
     * 拷贝单个对象（包括 null 属性）
     */
    public static <T, R> R copy(T source, Class<R> targetClass) {
        if (source == null) return null;
        try {
            R target = targetClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, target);
            return target;
        } catch (Exception e) {
            throw new RuntimeException("实体拷贝失败", e);
        }
    }

    /**
     * 拷贝单个对象（可选择是否拷贝 null 属性）
     */
    public static <T, R> R copy(T source, Class<R> targetClass, boolean ignoreNull) {
        if (!ignoreNull) return copy(source, targetClass);
        if (source == null) return null;

        try {
            R target = targetClass.getDeclaredConstructor().newInstance();
            BeanUtils.copyProperties(source, target, getNullPropertyNames(source));
            return target;
        } catch (Exception e) {
            throw new RuntimeException("实体拷贝失败", e);
        }
    }

    // ==================== 2. 列表批量拷贝 ====================

    /**
     * 批量拷贝列表
     */
    public static <T, R> List<R> copyList(List<T> sourceList, Class<R> targetClass) {
        if (sourceList == null || sourceList.isEmpty()) return new ArrayList<>();
        List<R> result = new ArrayList<>(sourceList.size());
        for (T source : sourceList) {
            R target = copy(source, targetClass);
            result.add(target);
        }
        return result;
    }

    /**
     * 批量拷贝列表（可选择忽略 null 属性）
     */
    public static <T, R> List<R> copyList(List<T> sourceList, Class<R> targetClass, boolean ignoreNull) {
        if (sourceList == null || sourceList.isEmpty()) return new ArrayList<>();
        List<R> result = new ArrayList<>(sourceList.size());
        for (T source : sourceList) {
            R target = copy(source, targetClass, ignoreNull);
            result.add(target);
        }
        return result;
    }

    // ==================== 3. 扩展：拷贝并自定义构造器 ====================

    /**
     * 拷贝对象并使用提供者构造目标对象
     */
    public static <T, R> R copy(T source, Supplier<R> targetSupplier) {
        if (source == null) return null;
        R target = targetSupplier.get();
        BeanUtils.copyProperties(source, target);
        return target;
    }

    /**
     * 拷贝列表并使用提供者构造目标对象
     */
    public static <T, R> List<R> copyList(List<T> sourceList, Supplier<R> targetSupplier) {
        if (sourceList == null || sourceList.isEmpty()) return new ArrayList<>();
        List<R> result = new ArrayList<>(sourceList.size());
        for (T source : sourceList) {
            R target = copy(source, targetSupplier);
            result.add(target);
        }
        return result;
    }

    // ==================== 4. 私有方法：获取 null 属性名 ====================

    private static String[] getNullPropertyNames(Object source) {
        var wrapper = new org.springframework.beans.BeanWrapperImpl(source);
        var properties = wrapper.getPropertyDescriptors();

        List<String> nullPropertyNames = new ArrayList<>();
        for (var pd : properties) {
            Object value = wrapper.getPropertyValue(pd.getName());
            if (value == null) {
                nullPropertyNames.add(pd.getName());
            }
        }
        return nullPropertyNames.toArray(new String[0]);
    }

}
