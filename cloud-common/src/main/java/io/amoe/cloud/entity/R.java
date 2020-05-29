package io.amoe.cloud.entity;

import io.amoe.cloud.enums.BizResponseStatus;
import lombok.Data;

import java.io.Serializable;
import java.time.Instant;

/**
 * @author Amoe
 */
@Data
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer code;
    private String msg;
    private Long timestamp;
    private T data;

    /**
     * 使用feign需要声明无参构造器
     */
    public R() {}

    public R(Integer code, String msg, T t) {
        this.code = code;
        this.msg = msg;
        this.data = t;
        this.timestamp = Instant.now().toEpochMilli();
    }

    public static <T> R<T> getInstance(Integer code, String msg, T t) {
        return new R<>(code, msg, t);
    }

    public static <T> R<T> getInstance(BizResponseStatus status, T t) {
        return getInstance(status.getCode(), status.getMessage(), t);
    }

    public static <T> R<T> getInstance(BizResponseStatus status) {
        return getInstance(status, null);
    }
}
