package com.ys.admin.base.shiroRealm;

import org.apache.shiro.authz.Permission;
import org.apache.shiro.authz.permission.WildcardPermission;

public class CustomPermssion implements Permission{
	
	private String strPermission;
	
	public CustomPermssion(String strPermission){
		this.strPermission = strPermission;
	}

	@Override
	public boolean implies(Permission p) {
		WildcardPermission wp = (WildcardPermission)p;
		WildcardPermission currentWp = new WildcardPermission(strPermission);
		return wp.implies(currentWp);
	}

}
