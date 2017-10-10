package com.ys.admin.controller.rbac;

import com.ys.admin.base.control.BaseController;
import com.ys.admin.base.annotations.RestFullController;
import com.zyf.result.Msg;
import com.ys.common.entitys.rbac.SysMember;
import com.ys.common.entitys.rbac.SysRole;
import io.ebean.Ebean;
import io.swagger.annotations.Api;
import lombok.DeleteById;
import lombok.Insert;
import lombok.QueryAll;
import lombok.Update;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@Api(value = "角色", description = "角色管理", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestFullController("sysRole")
@Insert(SysRole.class)
@Update(SysRole.class)
@DeleteById(SysRole.class)
@QueryAll(SysRole.class)
public class SysRoleController extends BaseController<SysRole> {

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
