package com.ys.admin.validate;

import com.zyf.result.Msg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;

/**
 * BindExceptionHandler
 */
@Slf4j
@ControllerAdvice
@Component
public class BindExceptionHandler {

    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler(BindException.class)
    public @ResponseBody Msg handleBindException(BindException e) {
        return Msg.fail(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler(ConstraintViolationException.class)
    public @ResponseBody Msg handleConstraintViolationException(ConstraintViolationException e) {
        return Msg.fail(e.getConstraintViolations().iterator().next().getMessageTemplate());
    }

    @ResponseStatus(value = HttpStatus.OK)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public @ResponseBody Msg handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return Msg.fail(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }
}