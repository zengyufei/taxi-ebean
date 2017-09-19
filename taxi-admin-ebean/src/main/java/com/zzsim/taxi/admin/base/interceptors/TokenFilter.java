package com.zzsim.taxi.admin.base.interceptors;

import com.zzsim.taxi.admin.util.AESSecret;
import com.zzsim.taxi.admin.util.TockenUtil;
import com.zzsim.taxi.core.common.entitys.rbac.SysMember;
import io.ebean.Ebean;
import io.ebeaninternal.server.util.Md5;
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
        
        String token = req.getHeader("token");
        if(StringUtils.isBlank(token) || "null".equals(token)){
        	token = req.getParameter("token");
        }
        if(StringUtils.isBlank(token)){
            //返回401未授权状态码
            WebUtils.toHttp(res).sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        try {
            String content = AESSecret.decrypt(token);
            if(StringUtils.isBlank(token)){
                //返回401未授权状态码
                WebUtils.toHttp(res).sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
            long memberId = Long.valueOf(StringUtils.split(content, "|")[0]);
            long expireTime = Long.valueOf(StringUtils.split(content, "|")[1]);
            String account = StringUtils.split(content, "|")[2];
            String md5Code = StringUtils.split(content, "|")[3];
            if(!md5Code.equals(Md5.hash(memberId+expireTime+account))){//MD5校对，防止伪装token
                //返回401未授权状态码
                WebUtils.toHttp(res).sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
            
            long nowTime = Instant.now().toEpochMilli();
            if(nowTime > expireTime){
                //返回401未授权状态码
                WebUtils.toHttp(res).sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }

            SysMember member = Ebean.find(SysMember.class, memberId);
            if(member == null){
                //返回401未授权状态码
                WebUtils.toHttp(res).sendError(HttpServletResponse.SC_UNAUTHORIZED);
                return false;
            }
            
            if (expireTime - nowTime < TockenUtil.liveTime) {//时间小于过期时间的一半才刷新token
                res.setHeader("token", TockenUtil.getToken(member));//刷新token
            }else{
            	res.setHeader("token", token);
            }
            
            WebDelegatingSubject subject = getWebSubject(req, res, member);//刷新cookie
            ThreadContext.bind(subject);
        } catch (Exception e) {
            e.printStackTrace();
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
