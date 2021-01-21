package com.rukawa.generate.sql.enumerates;

import java.util.Arrays;
import java.util.Optional;

/**
 * 状态码枚举类
 */
public enum Status {
    ACCEPT(100, "服务器接受请求"),
    SUCCESS(200, "服务器成功返回请求的数据"),
    CLIENT_ERROR(400, "发出的请求有错误"),
    NO_AUTHORITY(401, "用户没有权限"),
    REQUEST_FORBIDDEN(403, "访问被禁止"),
    NOT_FOUND(404, "请求资源不存在"),
    SERVER_ERROR(500, "服务器发生错误"),
    GATEWAY_ERROR(502, "网关错误"),
    SERVER_MAINTENANCE(503, "服务器暂时过载或维护"),
    GATEWAY_TIMEOUT(504, "网关超时");

    private Integer code;

    private String message;

    Status(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static Status getStatus(Integer code) {
        Optional<Status> statusOptional = Arrays.stream(values())
                .filter(value -> value.code.equals(code))
                .findFirst();
        if (statusOptional.isPresent()) {
            return statusOptional.get();
        }
        return SUCCESS;
    }
}
