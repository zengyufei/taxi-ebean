package com.ys.admin.controller.rbac;

import com.ys.admin.base.control.BaseController;
import com.ys.admin.base.annotations.RestFullController;
import com.ys.admin.service.rbac.SysOrgService;
import com.zyf.result.Msg;
import com.ys.common.entitys.rbac.SysOrg;
import io.ebean.Ebean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

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

	void insertBefore(SysOrg obj) {
		String createOrgNo = sysOrgService.createOrgNo(obj.getParentOrgNo());
		obj.setOrgNo(createOrgNo);
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
