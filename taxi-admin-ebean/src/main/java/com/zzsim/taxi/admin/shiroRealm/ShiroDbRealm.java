package com.zzsim.taxi.admin.shiroRealm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;

public class ShiroDbRealm extends AuthorizingRealm {


	public ShiroDbRealm() {
		setCredentialsMatcher(new CustomCredentialsMatcher());
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(
			PrincipalCollection principals) {
		SimpleAuthorizationInfo info = (SimpleAuthorizationInfo) SecurityUtils.getSubject().getSession().getAttribute("authorizationInfo");
		if (info != null) {
			return info;
		}
		info = new SimpleAuthorizationInfo();
		return info;
	}

	/**
	 * 认证回调函数,登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		return new SimpleAuthenticationInfo("admin", "admin", getName());
	}

	private void setSession(Object key, Object value) {
		Subject currentUser = SecurityUtils.getSubject();
		if (null != currentUser) {
			Session session = currentUser.getSession(true);
			if (null != session) {
				session.setTimeout(3600000);//一小时
				session.setAttribute(key, value);
			}
		}
	}

}

