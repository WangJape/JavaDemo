package com.jape.springweblab.vaildation;

import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 全局一场处理类
 */
@RestControllerAdvice
public class BindExceptionHandler {

    /**
     * 处理 字段校验异常
     */
    @ExceptionHandler(BindException.class)
    public String handleBindException(HttpServletRequest request, BindException exception) {
        BindingResult bindingResult = exception.getBindingResult();//绑定结果，也可得到fieldErrors
        List<FieldError> fieldErrors = exception.getFieldErrors();

        Map<String, String> fieldErrorMap = fieldErrors.stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage, (v1, v2) -> v1 + "," + v2));
        String fieldErrorStr = fieldErrorMap.entrySet().stream()
                .map(entry -> entry.getKey() + ":" + entry.getValue()).collect(Collectors.joining("|"));

        System.out.println(fieldErrorStr);
        return fieldErrorStr;
    }


}
