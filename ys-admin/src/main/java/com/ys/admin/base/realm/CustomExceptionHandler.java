package com.ys.admin.base.realm;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
@Component
public class CustomExceptionHandler {
	/*
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
*/
}
