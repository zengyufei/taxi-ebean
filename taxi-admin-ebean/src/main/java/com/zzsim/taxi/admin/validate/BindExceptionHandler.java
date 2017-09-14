package com.zzsim.taxi.admin.validate;

import com.zzsim.taxi.core.common.base.Msg;
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
@ControllerAdvice
@Component
public class BindExceptionHandler {
	// private static final Logger logger = Logger.getLogger(BindExceptionHandler.class);

    @ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED)
    @ExceptionHandler(BindException.class)
    public @ResponseBody Msg handleBindException(BindException e) {
        return Msg.fail(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }

    @ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED)
    @ExceptionHandler(ConstraintViolationException.class)
    public @ResponseBody Msg handleConstraintViolationException(ConstraintViolationException e) {
        return Msg.fail(e.getConstraintViolations().iterator().next().getMessageTemplate());
    }

    @ResponseStatus(value = HttpStatus.NOT_IMPLEMENTED)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public @ResponseBody Msg handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return Msg.fail(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }
}