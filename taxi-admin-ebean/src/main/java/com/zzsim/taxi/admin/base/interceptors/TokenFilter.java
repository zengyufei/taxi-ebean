package com.zzsim.taxi.admin.base.interceptors;

import com.zzsim.taxi.admin.base.shiroRealm.Token;
import com.zzsim.taxi.admin.util.AESSecret;
import com.zzsim.taxi.admin.util.TokenUtils;
import com.zzsim.taxi.core.common.entitys.rbac.SysMember;
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

        if("OPTIONS".equalsIgnoreCase(req.getMethod())){
            return true;
        }
        
        String headerToken = req.getHeader("token");
        if(StringUtils.isBlank(headerToken) || "null".equals(headerToken)){
            headerToken = req.getParameter("token");
        }
        if(StringUtils.isBlank(headerToken)){
            //返回401未授权状态码
            WebUtils.toHttp(res).sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        try {
            Token token = TokenUtils.getToken(headerToken);
            if(token == null){
                //返回401未授权状态码
                WebUtils.toHttp(res).sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }

            SysMember member = TokenUtils.getMember(token);
            if(member == null){
                //返回401未授权状态码
                WebUtils.toHttp(res).sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }

            long nowTime = Instant.now().toEpochMilli();
            if (token.getExpireTime() - nowTime < TokenUtils.liveTime) {//时间小于过期时间的一半才刷新token
                res.setHeader("token", TokenUtils.getTokenStr(member));//刷新token
            }else{
            	res.setHeader("token", headerToken);
            }
            
            WebDelegatingSubject subject = getWebSubject(req, res, member);
            ThreadContext.bind(subject);
        } catch (Exception e) {
            //返回401未授权状态码
            WebUtils.toHttp(res).sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        return true;
    }
    
    private WebDelegatingSubject getWebSubject(ServletRequest request, ServletResponse response, SysMember sysMember) {
        SimplePrincipalCollection principal = new SimplePrincipalCollection(sysMember, sysMember.getAccount());
        return new WebDelegatingSubject(principal, true, request.getRemoteHost(), null, false, request, response, SecurityUtils.getSecurityManager());
    }


}
