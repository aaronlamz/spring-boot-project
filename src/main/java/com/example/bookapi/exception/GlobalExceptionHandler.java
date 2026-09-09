package com.example.bookapi.exception;

import com.example.bookapi.common.ApiResponse;
import com.example.bookapi.common.ErrorCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(BusinessException.class)
    public ApiResponse<Void> handleBusinessException(BusinessException exception) {
        log.warn("业务异常，code={}，message={}", exception.getErrorCode().getCode(), exception.getMessage());
        return ApiResponse.fail(exception.getErrorCode(), exception.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Map<String, String>> handleValidationError(MethodArgumentNotValidException exception) {
        Map<String, String> fieldErrors = new LinkedHashMap<String, String>();
        for (FieldError fieldError : exception.getBindingResult().getFieldErrors()) {
            fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage());
        }
        log.warn("参数校验失败，fieldErrors={}", fieldErrors);
        return ApiResponse.fail(ErrorCode.PARAM_INVALID, ErrorCode.PARAM_INVALID.getMessage(), fieldErrors);
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, MethodArgumentTypeMismatchException.class})
    public ApiResponse<Void> handleUnreadableRequest(Exception exception) {
        log.warn("请求内容无法解析，{}", exception.getMessage());
        return ApiResponse.fail(ErrorCode.PARAM_INVALID);
    }

    @ExceptionHandler(Exception.class)
    public ApiResponse<Void> handleUnexpectedException(Exception exception) {
        log.error("未预期的异常", exception);
        return ApiResponse.fail(ErrorCode.SERVER_ERROR);
    }
}
