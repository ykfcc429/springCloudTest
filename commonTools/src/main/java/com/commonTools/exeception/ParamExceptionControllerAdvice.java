package com.commonTools.exeception;

import com.commonTools.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

/**
 * "参数校验异常处理"
 *
 * @author yankaifeng
 * 创建日期 2021/5/24
 */
@Slf4j
@RestControllerAdvice(annotations = {RestController.class})
@Order(1)
public class ParamExceptionControllerAdvice {

    /**
     * "参数校验异常处理"
     *
     * @param e 参数校验异常
     * @return 包含异常描述的响应
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<?> methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        log.error("argument verification error", e);
        BindingResult exceptions = e.getBindingResult();
        // 判断异常中是否有错误信息，如果存在就使用异常中的消息，否则使用默认消息
        if (exceptions.hasErrors()) {
            List<ObjectError> errors = exceptions.getAllErrors();
            if (!errors.isEmpty()) {
                // 这里列出了全部错误参数，按正常逻辑，只需要第一条错误即可
                FieldError fieldError = (FieldError) errors.get(0);
                return Result.error(40001,
                        fieldError.getField() + " " +
                                fieldError.getDefaultMessage());
            }
        }
        return Result.error(40001, "参数校验异常处理");
    }

    /**
     * "参数校验异常处理"
     *
     * @param e 参数校验异常
     * @return 包含异常描述的响应
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<?> constraintViolationExceptionHandler(ConstraintViolationException e) {
        log.error("argument verification error", e);
        Set<ConstraintViolation<?>> constraintViolations = e.getConstraintViolations();
        if (CollectionUtils.isEmpty(constraintViolations)) {
            return Result.error(40001,"参数校验异常处理");
        }
        Optional<ConstraintViolation<?>> first = constraintViolations.stream().findFirst();
        ConstraintViolation<?> constraintViolation = first.get();
        String message = constraintViolation.getMessage();
        String path = constraintViolation.getPropertyPath().toString();
        return Result.error(40001,
                path.substring(path.indexOf(".", path.indexOf(".") + 1) + 1) + " " +
                        message);
    }

    /**
     * 忽略参数异常处理器
     *
     * @param e 忽略参数异常
     * @return 包含异常信息的响应
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result<?> parameterMissingExceptionHandler(MissingServletRequestParameterException e) {
        log.error("argument miss error", e);
        return Result.error(40001, "请求参数 " + e.getParameterName() + " 不能为空");
    }


    /**
     * 缺少请求体异常处理器
     *
     * @param e 缺少请求体异常
     * @return 包含异常信息的响应
     */
    @ExceptionHandler({HttpMessageNotReadableException.class,MethodArgumentTypeMismatchException.class})
    public Result<?> parameterBodyMissingExceptionHandler(RuntimeException e) {
        log.error("json serialize error", e);
        return Result.error(40001, "请求转换异常，请检查请求体格式与字段类型。");
    }
}
