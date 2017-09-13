package com.zzsim.taxi.admin.validate;

import com.zzsim.taxi.core.common.base.Msg;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * BindExceptionHandler
 */
@ControllerAdvice
@Component
public class BindExceptionHandler {
	// private static final Logger logger = Logger.getLogger(BindExceptionHandler.class);

    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    public @ResponseBody Msg handleBindException(BindException e) {
        return Msg.fail(e.getBindingResult().getAllErrors().get(0).getDefaultMessage());
    }
}