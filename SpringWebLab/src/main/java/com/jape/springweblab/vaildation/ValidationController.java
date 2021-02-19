package com.jape.springweblab.vaildation;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.ConstraintViolation;
import javax.validation.Path;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 字段校验Demo
 */
@RestController
public class ValidationController {

    /**
     * 直接在controller处理错误信息
     */
    @GetMapping("validTest1")
    public String validTest1(@Validated User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<FieldError> fieldErrors = bindingResult.getFieldErrors();

            //看看有啥东西
            for (FieldError fieldError : fieldErrors) {
                fieldError.getField();//字段
                fieldError.getRejectedValue();//字段值
                fieldError.getDefaultMessage();//错误消息
                System.err.println(fieldError);
            }

            String fieldErrorStr = fieldErrors.stream()
                    .map(e -> e.getField() + ":" + e.getDefaultMessage())
                    .collect(Collectors.joining("|"));
            return fieldErrorStr;
        }
        return user.getEmail();
    }

    /**
     * 使用全局异常处理类来处理
     */
    @GetMapping("validTest2")
    public String validTest2(@Validated User user) {
        System.err.println(user);
        return user.toString();
    }

    /**
     * 手动调用对应API执行校验
     */
    @GetMapping("validTest3")
    public String validTest3(User user) {
        String validationResult = this.doValidation(user);
        System.err.println(validationResult);
        return validationResult;
    }

    /**
     * 手动验证工具类，没有错误则返回""
     */
    private <T> String doValidation(T obj) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<T>> validate = validator.validate(obj);

        for (ConstraintViolation<T> cv : validate) {
            cv.getPropertyPath().toString();//字段
            cv.getMessageTemplate();//没用
            cv.getMessage();//错误信息
            cv.getInvalidValue();//字段值
            System.err.println(cv);
        }

        Map<Path, String> fieldErrorMap = validate.stream().collect(
                Collectors.toMap(ConstraintViolation::getPropertyPath,
                        ConstraintViolation::getMessage,
                        (v1, v2) -> v1 + "," + v2));
        String fieldErrorStr = fieldErrorMap.entrySet().stream()
                .map(entry -> entry.getKey().toString() + ":" + entry.getValue())
                .collect(Collectors.joining("|"));

        return fieldErrorStr;
    }

}
