package com.ys.admin.base.realm;

import com.google.common.collect.Lists;
import com.ys.common.entitys.rbac.SysMember;
import com.ys.common.entitys.rbac.SysResource;
import com.ys.common.entitys.rbac.SysRole;
import io.ebean.Ebean;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.List;

public class ShiroDbRealm extends AuthorizingRealm {

	public ShiroDbRealm() {
		setCredentialsMatcher(new CustomCredentialsMatcher());
	}

	/**
	 * 认证回调函数,登录时调用.
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken authcToken) throws AuthenticationException {
		UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
		SysMember member = Ebean.find(SysMember.class)
				.where()
				.eq("account", token.getUsername())
				.eq("password", token.getPassword())
				.findUnique();
		if(member == null){
			throw new AuthenticationException("登录失败");
		}
		return new SimpleAuthenticationInfo(member, member.getPassword(), getName());
	}

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		SysMember sysMember = (SysMember) SecurityUtils.getSubject().getPrincipal();
		if (sysMember == null) {
			throw new AuthorizationException("没有该用户");
		}

		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		List<String> roles = Lists.newArrayList();
		roles.add(String.valueOf(sysMember.getRoleId()));
		info.addRoles(roles);

		if (sysMember.getRoleId() > 0) {
			SysRole role = getRole(sysMember.getRoleId());
			List<SysResource> resources = getResources(role.getResourceList());
			List<Permission> permissions = Lists.newArrayList();
			for (SysResource sysResource : resources) {
				if (!StringUtils.isBlank(sysResource.getPermission())) {
					Permission p = new CustomPermssion(sysResource.getPermission());
					permissions.add(p);
				}
			}
			info.addObjectPermissions(permissions);
		}
		return info;
	}

	private SysRole getRole(Long roleId) {
		return Ebean.find(SysRole.class, roleId);
	}

	private List<SysResource> getResources(List<String> resourceIds) {
		return Ebean.find(SysResource.class)
				.where()
				.idIn(resourceIds)
				.findList();
	}


}

