package com.zzsim.taxi.admin.base.interceptors;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 请求支持跨域
 */
@WebFilter
public class AllowOriginFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// 支持测试环境的跨域请求
		if (request.getLocalAddr().startsWith("192.168")
				|| request.getLocalAddr().startsWith("127.0")
				|| request.getLocalAddr().startsWith("local")
				|| request.getLocalAddr().startsWith("0:0:0")
				) {
			HttpServletResponse resp = (HttpServletResponse) response;
			resp.addHeader("Access-Control-Allow-Credentials", "true");
			resp.addHeader("Access-Control-Allow-Origin", "*");
			resp.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
			resp.addHeader("Access-Control-Request-Headers", "token");
			resp.addHeader("Access-Control-Expose-Headers", "token");
			resp.addHeader("Access-Control-Allow-Headers", "DNT,X-Mx-ReqToken,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Authorization,Accept,token");
			resp.addHeader("Access-Control-Max-Age", "1800");//30 min

			resp.addHeader("Content-Type", "application/json"); //返回数据格式是json
		}

		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}
}
