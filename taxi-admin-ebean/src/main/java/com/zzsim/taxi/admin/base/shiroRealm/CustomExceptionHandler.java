package com.zzsim.taxi.admin.base.shiroRealm;

import com.zzsim.taxi.core.common.base.Msg;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
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
	@ExceptionHandler(AuthenticationException.class)
	public @ResponseBody
	Msg handleUnauthorizedException(AuthenticationException e) {
		return Msg.status(401).msg(e.getMessage()).build();
	}

	@ResponseStatus(value = HttpStatus.FORBIDDEN)
	@ExceptionHandler(AuthorizationException.class)
	public @ResponseBody
	Msg handleUnauthenticatedException(AuthorizationException e) {
		return Msg.status(403).msg(e.getMessage()).build();
	}

}