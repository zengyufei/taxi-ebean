package com.ys.admin.controller.rbac;

import com.ys.admin.base.annotations.RestFullController;
import com.ys.admin.base.control.BaseController;
import com.ys.common.entitys.rbac.SysMember;
import com.zyf.result.Msg;
import io.swagger.annotations.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Api(value = "用户", description = "后台用户管理", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestFullController("sysMember")
@Update(value = SysMember.class, vo = SysMember.Vo.class)
@Insert(value = SysMember.class, vo = SysMember.Vo.class)
@RemoveById(SysMember.class)
@DeleteById(SysMember.class)
@QueryById(SysMember.class)
public class SysMemberController extends BaseController<SysMember> {

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
	public Msg queryPage(SysMember.Vo vo,
	                     @RequestParam(required = false, defaultValue = "1") int pageNo,
	                     @RequestParam(required = false, defaultValue = "10") int pageSize) {
		return Msg.ok(setPage(setParams(vo), pageNo, pageSize));
	}
}
