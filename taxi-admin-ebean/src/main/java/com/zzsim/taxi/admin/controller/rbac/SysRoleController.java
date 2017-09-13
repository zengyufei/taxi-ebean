package com.zzsim.taxi.admin.controller.rbac;

import com.zzsim.taxi.admin.base.BaseController;
import com.zzsim.taxi.admin.validate.groups.DeleteById;
import com.zzsim.taxi.admin.validate.groups.Insert;
import com.zzsim.taxi.admin.validate.groups.QueryById;
import com.zzsim.taxi.admin.validate.groups.Update;
import com.zzsim.taxi.core.common.base.Msg;
import com.zzsim.taxi.core.common.entitys.rbac.SysRole;
import com.zzsim.taxi.core.common.options.rbac.SysRoleOption;
import io.ebean.Ebean;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("sysRole")
public class SysRoleController extends BaseController {

	@GetMapping("queryById")
	public Msg queryById(@Validated(value = QueryById.class)Long id) {
		return Msg.ok(Ebean.find(SysRole.class, id));
	}

	@GetMapping("queryAll")
	public Msg queryAll() {
		return Msg.ok(Ebean.find(SysRole.class).findList());
	}

	@GetMapping("queryPage")
	public Msg queryPage(SysRoleOption option,
						 @RequestParam(required = false, defaultValue = "1") int pageNo,
						 @RequestParam(required = false, defaultValue = "10") int pageSize) {
		return Msg.ok(setPage(setParams(SysRole.class, option), pageNo, pageSize));
	}

	@PostMapping("insert")
	public Msg insert(@Validated(value = Insert.class) SysRole sysRole) {
		sysRole.insert();
		return Msg.ok("新增成功");
	}

	@PostMapping("update")
	public Msg<String> update(@Validated(value = Update.class) SysRole sysRole) {
		sysRole.update();
		return Msg.ok("修改成功");
	}

	@GetMapping("deleteById")
	public Msg deleteById(@Validated(value = DeleteById.class) Long id) {
		Ebean.delete(SysRole.class, id);
		return Msg.ok("删除成功");
	}

}
