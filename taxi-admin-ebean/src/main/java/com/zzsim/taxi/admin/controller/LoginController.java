package com.zzsim.taxi.admin.controller;

import com.zzsim.taxi.admin.util.TokenUtils;
import com.zzsim.taxi.core.common.base.Msg;
import com.zzsim.taxi.core.common.entitys.rbac.SysMember;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class LoginController {

	@PostMapping("login")
	public Msg login(String account, String password) throws AuthenticationException {
		log.info("用户登录 账户 {} 密码 {}", account, password);
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken accountAndPassword = new UsernamePasswordToken(account, password);
		subject.login(accountAndPassword);
		SysMember member = (SysMember) subject.getPrincipal();
		try {
			String token = TokenUtils.getTokenStr(member);
			return Msg.ok("登录成功", token);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Msg.fail("token 生成异常");
	}


	@GetMapping("logout")
	public Msg logout() throws AuthenticationException {
		Subject subject = SecurityUtils.getSubject();
		try {
			subject.logout();
		} finally {
			log.info("用户注销");
			throw new AuthenticationException("注销成功");
		}
	}
}
