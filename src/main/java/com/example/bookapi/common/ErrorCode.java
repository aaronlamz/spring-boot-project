package com.example.bookapi.common;

/**
 * 业务返回码。0 表示成功，其余表示各种失败原因。
 */
public enum ErrorCode {

    SUCCESS(0, "成功"),
    PARAM_INVALID(1001, "请求参数不正确"),
    BOOK_NOT_FOUND(1002, "图书不存在"),
    SERVER_ERROR(9999, "服务器内部错误");

    private final int code;
    private final String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
