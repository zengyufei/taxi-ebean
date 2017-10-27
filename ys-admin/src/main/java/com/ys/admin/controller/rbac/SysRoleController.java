package com.ys.admin.controller.rbac;

import com.ys.admin.base.annotations.RestFullController;
import com.ys.admin.base.control.BaseController;
import com.ys.common.entitys.rbac.SysMember;
import com.ys.common.entitys.rbac.SysRole;
import com.zyf.result.Msg;
import io.ebean.Ebean;
import io.swagger.annotations.*;
import lombok.DeleteById;
import lombok.Insert;
import lombok.QueryAll;
import lombok.Update;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

	@ApiOperation(value = "条件查询分页，不包括删除", notes = "条件查询分页，不包括删除", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = Msg.SUCCESS_CODE, message = "查询成功", response = Msg.class),
			@ApiResponse(code = Msg.ERROR_CODE, message = "系统错误", response = Msg.class)
	})
	@ApiImplicitParams({
			@ApiImplicitParam(value = "条件", name = "vo", paramType = "query"),
			@ApiImplicitParam(value = "当前页", name = "pageNo", defaultValue = "1", paramType = "query", dataType = "int"),
			@ApiImplicitParam(value = "每页页数", name = "pageSize", defaultValue = "1", paramType = "query", dataType = "int"),
	})
	@GetMapping("queryPage")
	public Msg queryPage(SysRole.Vo vo,
	                     @RequestParam(required = false, defaultValue = "1") int pageNo,
	                     @RequestParam(required = false, defaultValue = "10") int pageSize) {
		return Msg.ok(setPage(setParams(vo), pageNo, pageSize));
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
