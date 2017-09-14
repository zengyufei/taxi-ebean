package com.zzsim.taxi.admin.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.AccessControlFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 
 * @project taxi-admin
 * @author chenzhizhao
 * @date 2017年7月11日下午4:31:01
 * Copyright © 2015 www.zzsim.com 粤ICP备13076410号
 */
public class TokenFilter extends AccessControlFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object obj) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        HttpServletResponse res = (HttpServletResponse) servletResponse;
        
        String token = (String) req.getHeader("token");
        if(StringUtils.isBlank(token)){
            return false;
        }
        return true;
    }
    
}
