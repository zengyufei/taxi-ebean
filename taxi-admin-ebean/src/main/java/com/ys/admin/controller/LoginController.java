package com.ys.admin.controller;

import com.ys.admin.util.TokenUtils;
import com.zyf.result.Msg;
import com.ys.common.entitys.rbac.SysMember;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(value = "登录接口", description = "登录、注销功能", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController
public class LoginController {

	@ApiOperation(value = "用户登录", notes = "用户登录", httpMethod = "POST", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = Msg.SUCCESS_CODE, message = "登录成功", response = Msg.class),
			@ApiResponse(code = Msg.ERROR_CODE, message = "账号密码错误", response = Msg.class)
	})
	@ApiImplicitParams({
			@ApiImplicitParam(value = "账号", required = true, name = "account", paramType = "body"),
			@ApiImplicitParam(value = "密码", required = true, name = "password", paramType = "body")
	})
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

	@ApiOperation(value = "用户注销", notes = "用户注销", httpMethod = "GET", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
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
