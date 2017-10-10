package com.ys.admin.base.interceptors;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 请求支持跨域
 */
@WebFilter
@Component
public class AllowOriginFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		// 支持测试环境的跨域请求
			HttpServletResponse resp = (HttpServletResponse) response;
			resp.addHeader("Access-Control-Allow-Origin", "*");
			resp.addHeader("Access-Control-Allow-Credentials", "true");
			resp.addHeader("Access-Control-Allow-Methods", "*");
			resp.addHeader("Access-Control-Request-Headers", "*");
			resp.addHeader("Access-Control-Expose-Headers", "*");
			resp.addHeader("Access-Control-Allow-Headers", "Origin, No-Cache, X-Requested-With, If-Modified-Since, " +
					"Pragma, Last-Modified, Cache-Control, Expires, " +
					"Content-Type, X-E4M-With.DNT,X-Mx-ReqToken,Keep-Alive," +
					"Cache-Control,Host,Origin,Referer,User-Agent,Content-Length," +
					"Connection,Content-Type,Authorization,Accept,Accept-Encoding,Accept-Language,token");
			resp.addHeader("Access-Control-Max-Age", "1800");//30 min

			//resp.addHeader("Content-Type", "application/json"); //返回数据格式是json

		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}
}
