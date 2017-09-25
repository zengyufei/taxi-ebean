package com.zzsim.taxi.admin.controller.rbac;

import com.zzsim.taxi.admin.base.annotations.RestFullController;
import com.zzsim.taxi.admin.base.control.D.DRUCQ;
import com.zzsim.taxi.core.common.base.Msg;
import com.zzsim.taxi.core.common.entitys.rbac.SysMember;
import com.zzsim.taxi.core.common.entitys.rbac.SysRole;
import io.ebean.Ebean;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@Api(value = "角色", description = "角色管理", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestFullController("sysRole")
public class SysRoleController extends DRUCQ<SysRole> {

	@GetMapping("getRole")
	public Msg getRole() {
		log.info("用户登录成功后查询角色信息");
		SysMember sysMember = (SysMember) SecurityUtils.getSubject().getPrincipal();
		SysRole sysRole = Ebean.find(SysRole.class, sysMember.getRoleId());
		if(sysRole == null){
			Msg.fail("角色不存在");
		}
		return Msg.ok(sysRole);
	}

	@GetMapping("queryRoleIdAndRoleName")
	public Msg queryRoleIdAndRoleName() {
		List<SysRole> list = Ebean.find(SysRole.class)
				.setDisableLazyLoading(true)
				.select("id, roleName")
				.findList();
		return Msg.ok(list);
	}

}
