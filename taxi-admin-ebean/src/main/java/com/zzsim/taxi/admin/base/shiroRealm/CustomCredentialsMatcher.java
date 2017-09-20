package com.zzsim.taxi.admin.base.shiroRealm;

import io.ebeaninternal.server.util.Md5;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

public class CustomCredentialsMatcher extends SimpleCredentialsMatcher {
	
	  @Override  
    public boolean doCredentialsMatch(AuthenticationToken token,  
            AuthenticationInfo info) {  
		  
		UsernamePasswordToken usertoken = (UsernamePasswordToken) token;
		// String tokenCredentials = Md5.hash(usertoken.getUsername()+String.valueOf(usertoken.getPassword()));
		String tokenCredentials = String.valueOf(usertoken.getPassword());
		String accountCredentials = (String)info.getCredentials();
		return equals(tokenCredentials, accountCredentials);
	
    }

}