package com.zzsim.taxi.admin.interceptor;

import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 请求支持跨域
 */
public class AllowOriginFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// 支持测试环境的跨域请求
		if(request.getLocalAddr().startsWith("192.168") || request.getLocalAddr().equals("127.0.0.1")) {
			HttpServletResponse resp = (HttpServletResponse) response;
			resp.addHeader("Access-Control-Allow-Origin", "*");
			resp.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
			resp.addHeader("Access-Control-Allow-Headers", "Content-Type,Accept,token");
			resp.addHeader("Access-Control-Max-Age", "1800");//30 min
		}

		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}
}
