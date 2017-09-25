package com.zzsim.taxi.admin.base.shiroRealm;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.authc.AuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Desc 用于自定义过滤器，过滤用户请求时是否是登录状态
 * @author dingjunxiong
 *
 */
public class CustomAuthenticationFilter extends AuthenticationFilter   {

    private String unauthorizedUrl;


    public String getUnauthorizedUrl() {
        return unauthorizedUrl;
    }


    public void setUnauthorizedUrl(String unauthorizedUrl) {
        this.unauthorizedUrl = unauthorizedUrl;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
    	
    	 HttpServletRequest httpRequest = (HttpServletRequest) request;  
    	 //为 null，则为传统同步请求，为 XMLHttpRequest，则为 Ajax 异步请求。
         String head = httpRequest.getHeader("X-Requested-With");
         boolean isAjax =false;
         if(!org.apache.commons.lang3.StringUtils.isBlank(head)){
        	   if(head.equalsIgnoreCase("XMLHttpRequest")){
        		   isAjax = true;
               }
         }
    	
        Subject subject = getSubject(request, response);
        if (subject.getPrincipal() == null) {
        	if(!isAjax){
        		// saveRequestAndRedirectToLogin(request, response);
        	}else{
        		String loginfailure = "/";
    		    WebUtils.issueRedirect(request, response, loginfailure);
        	}
        } else {
            String unauthorizedUrl = getUnauthorizedUrl();
            if (StringUtils.hasText(unauthorizedUrl)) {
                WebUtils.issueRedirect(request, response, unauthorizedUrl);
            } else {
                WebUtils.toHttp(response).sendError(HttpServletResponse.SC_UNAUTHORIZED);
            }
        }
        return false;
    }

}
