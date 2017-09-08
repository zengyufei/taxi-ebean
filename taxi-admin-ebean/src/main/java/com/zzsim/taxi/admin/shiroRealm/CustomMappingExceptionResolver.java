package com.zzsim.taxi.admin.shiroRealm;

import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 继承至SimpleMappingExceptionResolver的自定义的统一异常处理
 *
 * @author zengyufei
 */
public class CustomMappingExceptionResolver extends SimpleMappingExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		if (ex instanceof UnauthorizedException) {
			//返回403未授权状态码
			try {
				WebUtils.toHttp(response).sendError(HttpServletResponse.SC_FORBIDDEN);
			} catch (IOException e) {
			}finally {
				return new ModelAndView();
			}
		} else if (ex instanceof UnauthenticatedException) {
			//返回401未授权状态码
			try {
				WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
			} catch (IOException e) {
			}finally {
				return new ModelAndView();
			}
		} else {
			return super.resolveException(request, response, handler, ex);
		}

	}


}