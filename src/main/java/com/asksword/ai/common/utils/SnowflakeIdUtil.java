package com.asksword.ai.common.utils;

/**
 * 雪花算法生成全局唯一ID工具类（单例 + 静态方法调用）
 * <p>
 * 64 位结构：
 * 0 - 41bit时间戳 - 5bit数据中心ID - 5bit机器ID - 12bit序列号
 * <p>
 * 使用：
 * long id = SnowflakeIdUtil.nextId();
 */
public class SnowflakeIdUtil {

    // ==================== 配置 ====================
    private static final long EPOCH = 1672531200000L; // 起始时间戳 2023-01-01 00:00:00

    private static final long WORKER_ID_BITS = 5L;       // 机器ID占用位数
    private static final long DATACENTER_ID_BITS = 5L;   // 数据中心占用位数
    private static final long SEQUENCE_BITS = 12L;       // 序列号占用位数

    private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);        // 31
    private static final long MAX_DATACENTER_ID = ~(-1L << DATACENTER_ID_BITS);// 31

    private static final long WORKER_ID_SHIFT = SEQUENCE_BITS;                     // 12
    private static final long DATACENTER_ID_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS; // 17
    private static final long TIMESTAMP_LEFT_SHIFT = SEQUENCE_BITS + WORKER_ID_BITS + DATACENTER_ID_BITS; // 22

    private static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS); // 4095

    // ==================== 状态 ====================
    private long workerId = 0L;
    private long datacenterId = 0L;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    // ==================== 单例 ====================
    private static final SnowflakeIdUtil INSTANCE = new SnowflakeIdUtil(0, 0);

    private SnowflakeIdUtil(long workerId, long datacenterId) {
        if (workerId < 0 || workerId > MAX_WORKER_ID) {
            throw new IllegalArgumentException("workerId 超出范围 [0," + MAX_WORKER_ID + "]");
        }
        if (datacenterId < 0 || datacenterId > MAX_DATACENTER_ID) {
            throw new IllegalArgumentException("datacenterId 超出范围 [0," + MAX_DATACENTER_ID + "]");
        }
        this.workerId = workerId;
        this.datacenterId = datacenterId;
    }

    // ==================== 生成 ID ====================
    private synchronized Long nextIdInternal() {
        long timestamp = currentTime();

        if (timestamp < lastTimestamp) {
            throw new RuntimeException(
                    "系统时钟回退，拒绝生成ID，差值：" + (lastTimestamp - timestamp) + " 毫秒");
        }

        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & SEQUENCE_MASK;
            if (sequence == 0) {
                timestamp = waitNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return ((timestamp - EPOCH) << TIMESTAMP_LEFT_SHIFT)
                | (datacenterId << DATACENTER_ID_SHIFT)
                | (workerId << WORKER_ID_SHIFT)
                | sequence;
    }

    private long waitNextMillis(long lastTimestamp) {
        long timestamp = currentTime();
        while (timestamp <= lastTimestamp) {
            timestamp = currentTime();
        }
        return timestamp;
    }

    private long currentTime() {
        return System.currentTimeMillis();
    }

    // ==================== 静态方法 ====================

    /**
     * 静态方法直接调用生成全局唯一ID
     */
    public static Long nextId() {
        return INSTANCE.nextIdInternal();
    }

}
