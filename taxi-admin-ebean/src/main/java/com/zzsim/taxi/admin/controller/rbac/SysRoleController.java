package com.zzsim.taxi.admin.controller.rbac;

import com.zzsim.taxi.admin.base.BaseController;
import com.zzsim.taxi.admin.base.annotations.RestFullController;
import com.zzsim.taxi.core.common.base.Msg;
import com.zzsim.taxi.core.common.entitys.rbac.SysRole;
import io.ebean.Ebean;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Api(value = "角色", description = "角色管理", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestFullController("sysRole")
public class SysRoleController extends BaseController<SysRole> {

	@RequestMapping("queryRoleIdAndRoleName")
	public Msg queryRoleIdAndRoleName() {
		List<SysRole> list = Ebean.find(SysRole.class)
				.setDisableLazyLoading(true)
				.select("id, roleName")
				.findList();
		return Msg.ok(list);
	}

}
