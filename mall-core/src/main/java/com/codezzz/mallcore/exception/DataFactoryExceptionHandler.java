package com.codezzz.mallcore.exception;


import com.codezzz.mallcore.model.dto.RespDTO;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.path.PathImpl;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.text.MessageFormat;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 统一的异常处理器
 *
 * @author felixu
 * @since 2021.05.10
 */
@Slf4j
@RestControllerAdvice
@Order(1)
public class DataFactoryExceptionHandler {

    public DataFactoryExceptionHandler() {
    }

    /*------------------------------------------------- DataFactoryException 和 BusinessException 的处理-----------------------------------------------------------------*/

    @ExceptionHandler(DataFactoryException.class)
    public ResponseEntity<RespDTO<?>> dataFactoryExceptionHandler(DataFactoryException ex, HttpServletRequest request) {
        log.error("业务异常：{} {}", request.getMethod(), request.getRequestURI(), ex);
        ErrorCode error = ex.getError();
        String message = error.getMessage();
        if (Objects.nonNull(ex.getArgs()))
            message = MessageFormat.format(message, ex.getArgs());
        return new ResponseEntity<>(RespDTO.onFail(error.getCode(), message), HttpStatus.OK);
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<RespDTO<?>> bizExceptionHandler(BaseException ex, HttpServletRequest request) {
        log.error("业务异常：{} {}", request.getMethod(), request.getRequestURI(), ex);
        BaseErrorCode error = ex.getError();
        String message = error.getMessage();
        if (Objects.nonNull(ex.getArgs()))
            message = MessageFormat.format(message, ex.getArgs());
        return new ResponseEntity<>(RespDTO.onFail(error.getCode(), message), HttpStatus.OK);
    }

    /*------------------------------------------------- 参数校验出错的处理 -----------------------------------------------------------------*/

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<RespDTO<?>> missingParameterExceptionHandler(MissingServletRequestParameterException ex, HttpServletRequest request) {
        String parameterName = ex.getParameterName();
        String parameterType = ex.getParameterType();
        log.error("缺少必要参数：{}({})，{} {}", parameterName, parameterType, request.getMethod(), request.getRequestURI(), ex);
        ErrorCode error = ErrorCode.MISSING_SERVLET_REQUEST_PARAMETER;
        String message = parameterName + ": " + error.getMessage();
        return new ResponseEntity<>(RespDTO.onFail(error.getCode(), message), HttpStatus.OK);
    }

    @ExceptionHandler(BindException.class)
    public ResponseEntity<RespDTO<?>> bindExceptionHandler(BindException be, HttpServletRequest request) {
        return fromFieldErrors(be.getFieldErrors(), request);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RespDTO<?>> methodArgumentNotValidHandler(MethodArgumentNotValidException e, HttpServletRequest request) {
        return fromFieldErrors(e.getBindingResult().getFieldErrors(), request);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<RespDTO<?>> constraintViolationExceptionHandler(ConstraintViolationException e, HttpServletRequest request) {
        Set<ConstraintViolation<?>> violations = e.getConstraintViolations();
        Map<String, String> result = new HashMap<>(violations.size());
        for (ConstraintViolation<?> violation : violations) {
            String fieldName = ((PathImpl) violation.getPropertyPath()).getLeafNode().getName();
            result.put(fieldName, violation.getMessage());
        }
        log.debug("入参校验未通过：{} {}，{}", request.getMethod(), request.getRequestURI(), result);
        BindException exception = new BindException(e, "exception");
        result.forEach((key, value) -> exception.addError(new FieldError(exception.getObjectName(), key, value)));
        return fromFieldErrors(exception.getBindingResult().getFieldErrors(), request);
    }

    /*------------------------------------------------- 没有登录 -----------------------------------------------------------------*/

//    @ExceptionHandler(AuthenticationException.class)
//    public ResponseEntity<RespDTO<?>> authenticationExceptionHandler() {
//        return new ResponseEntity<>(RespDTO.onFail(ErrorCode.LOGIN_REQUIRED), HttpStatus.UNAUTHORIZED);
//    }
//
//    /*------------------------------------------------- 没有权限 -----------------------------------------------------------------*/
//
//    @ExceptionHandler(AuthorizationException.class)
//    public ResponseEntity<RespDTO<?>> authorityExceptionHandler() {
//        return new ResponseEntity<>(RespDTO.onFail(ErrorCode.AUTHORIZATION_REQUIRED), HttpStatus.FORBIDDEN);
//    }

    /*------------------------------------------------- 其他异常 -----------------------------------------------------------------*/

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public ResponseEntity<RespDTO<?>> argumentExHandler(MethodArgumentTypeMismatchException ex, HttpServletRequest request) {
        log.error("系统异常：{} {}", request.getMethod(), request.getRequestURI(), ex);
        return new ResponseEntity<>(RespDTO.onFail(ErrorCode.PATH_PARAM_DATA_TYPE_ERROR), HttpStatus.OK);
    }

    @ExceptionHandler(value = HttpMessageNotReadableException.class)
    public ResponseEntity<RespDTO<?>> enumArgumentExHandler(HttpMessageNotReadableException ex, HttpServletRequest request) {
        log.error("系统异常：{} {}", request.getMethod(), request.getRequestURI(), ex);
        return new ResponseEntity<>(RespDTO.onFail(ErrorCode.INPUT_PARAM_ILLEGAL), HttpStatus.OK);
    }

    @ExceptionHandler(value = Throwable.class)
    public ResponseEntity<RespDTO<?>> exHandler(Throwable ex, HttpServletRequest request) {
        log.error("系统异常：{} {}", request.getMethod(), request.getRequestURI(), ex);
        ErrorCode error = ErrorCode.FAIL;
        return new ResponseEntity<>(RespDTO.onFail(error.getCode(), ex.getLocalizedMessage()), HttpStatus.OK);
    }

    private ResponseEntity<RespDTO<?>> fromFieldErrors(List<FieldError> errors, HttpServletRequest request) {
        String result = errors.stream().map(error -> error.getField() + ": " + error.getDefaultMessage()).collect(Collectors.joining("; "));
        log.debug("入参 Bean 校验未通过：{} {}，{}", request.getMethod(), request.getRequestURI(), result);
        return new ResponseEntity<>(RespDTO.onFail(ErrorCode.PARAM_ERROR.getCode(), result), HttpStatus.OK);
    }
}

