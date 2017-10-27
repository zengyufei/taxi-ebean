package com.ys.admin.base.interceptors;

import com.ys.admin.base.realm.Token;
import com.ys.admin.util.TokenUtils;
import com.ys.common.entitys.rbac.SysMember;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.subject.support.WebDelegatingSubject;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;

public class TokenFilter extends AccessControlFilter {

	@Override
	protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object obj) throws Exception {
		return false;
	}

	@Override
	protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
		HttpServletRequest req = (HttpServletRequest) servletRequest;
		HttpServletResponse res = (HttpServletResponse) servletResponse;

		String optionsMethod = "OPTIONS";
		if (optionsMethod.equalsIgnoreCase(req.getMethod())) {
			return true;
		}

		String tokenName = "token";
		String headerToken = req.getHeader(tokenName);
		if (StringUtils.isBlank(headerToken) || "null".equals(headerToken)) {
			headerToken = req.getParameter(tokenName);
		}
		if (StringUtils.isBlank(headerToken)) {
			//返回401未授权状态码
			WebUtils.toHttp(res).sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return false;
		}
		try {
			Token token = TokenUtils.getToken(headerToken);
			if (token == null) {
				//返回401未授权状态码
				WebUtils.toHttp(res).sendError(HttpServletResponse.SC_UNAUTHORIZED);
				return false;
			}

			SysMember member = TokenUtils.getMember(token);
			if (member == null) {
				//返回401未授权状态码
				WebUtils.toHttp(res).sendError(HttpServletResponse.SC_UNAUTHORIZED);
				return false;
			}

			long nowTime = Instant.now().toEpochMilli();
			if (token.getExpireTime() - nowTime < TokenUtils.liveTime) {//时间小于过期时间的一半才刷新token
				res.setHeader(tokenName, TokenUtils.getTokenStr(member));//刷新token
			} else {
				res.setHeader(tokenName, headerToken);
			}

			WebDelegatingSubject subject = getWebSubject(req, res, member);
			ThreadContext.bind(subject);
		} catch (Exception e) {
			e.printStackTrace();
			//返回401未授权状态码
			WebUtils.toHttp(res).sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return false;
		}
		return true;
	}

	private WebDelegatingSubject getWebSubject(ServletRequest request, ServletResponse response, SysMember sysMember) {
		SimplePrincipalCollection principal = new SimplePrincipalCollection(sysMember, sysMember.getAccount());
		return new WebDelegatingSubject(principal, true, request.getRemoteHost(), null, false, request, response, SecurityUtils.getSecurityManager());
	}


}
