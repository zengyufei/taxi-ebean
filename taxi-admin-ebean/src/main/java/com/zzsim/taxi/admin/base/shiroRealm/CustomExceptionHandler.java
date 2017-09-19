package com.zzsim.taxi.admin.base.shiroRealm;

import com.zzsim.taxi.core.common.base.Msg;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolationException;

@ControllerAdvice
@Component
public class CustomExceptionHandler {

	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(UnauthorizedException.class)
	public @ResponseBody
	Msg handleUnauthorizedException(UnauthorizedException e) {
		return Msg.fail(e.getMessage());
	}

	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	@ExceptionHandler(UnauthenticatedException.class)
	public @ResponseBody
	Msg handleUnauthenticatedException(UnauthenticatedException e) {
		return Msg.fail(e.getMessage());
	}

}