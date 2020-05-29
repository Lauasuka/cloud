package io.amoe.cloud.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Amoe
 */
@Getter
@AllArgsConstructor
public enum BizResponseStatus {
    OK(200, "请求成功"),
    ERROR(500, "服务异常"),

    /** 4XX：客户端错误，请求包含语法错误或无法完成请求 */
    PARAM_ERROR(400, "参数错误"),
    USER_ERROR(400, "账号异常"),
    USER_ACCOUNT_EXIST(400, "账号已存在"),
    NOT_FOUND(404, "您所请求的资源无法找到"),
    UNSUPPORTED_MEDIA_TYPE(415, "不支持的请求媒体格式"),

    /** 5XX：服务器错误，服务器在处理请求的过程中发生了错误*/
    BIZ_ERROR(500, "业务异常")
    ;

    private final Integer code;
    private final String message;
}
