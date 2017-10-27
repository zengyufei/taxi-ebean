package com.ys.admin.controller.rbac;

import com.ys.admin.base.annotations.RestFullController;
import com.ys.admin.base.control.BaseController;
import com.ys.admin.service.rbac.SysOrgService;
import com.ys.common.entitys.rbac.SysOrg;
import com.zyf.result.Msg;
import io.ebean.Ebean;
import io.swagger.annotations.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Api(value = "组织机构", description = "组织机构管理", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestFullController("sysOrg")
@Update(SysOrg.class)
@Insert(SysOrg.class)
@RemoveById(SysOrg.class)
@DeleteById(SysOrg.class)
@QueryAll(SysOrg.class)
@QueryById(SysOrg.class)
public class SysOrgController extends BaseController<SysOrg> {

	@Autowired
	private SysOrgService sysOrgService;

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
	public Msg queryPage(SysOrg.Vo vo,
	                     @RequestParam(required = false, defaultValue = "1") int pageNo,
	                     @RequestParam(required = false, defaultValue = "10") int pageSize) {
		return Msg.ok(setPage(setParams(vo), pageNo, pageSize));
	}

	String insertBefore(SysOrg obj) {
		return "";
		//String createOrgNo = sysOrgService.createOrgNo(obj.getParentOrgNo());
		//obj.setOrgNo(createOrgNo);
	}

	@ApiOperation(value = "查询组织机构编号与组织机构名称", notes = "查询组织机构编号与组织机构名称", httpMethod = "GET", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@ApiResponses({
			@ApiResponse(code = Msg.SUCCESS_CODE, message = "查询成功", response = Msg.class),
			@ApiResponse(code = Msg.ERROR_CODE, message = "查询失败", response = Msg.class),
	})
	@GetMapping("queryOrgNoAndOrgName")
	public Msg queryOrgNoAndOrgName() {
		List<SysOrg> list = Ebean.find(SysOrg.class)
				.setDisableLazyLoading(true)
				.select("orgNo, orgName")
				.findList();
		return Msg.ok(list);
	}

}
