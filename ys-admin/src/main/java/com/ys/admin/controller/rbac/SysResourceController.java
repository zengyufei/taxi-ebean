package com.ys.admin.controller.rbac;

import com.google.common.collect.Lists;
import com.ys.admin.base.annotations.RestFullController;
import com.ys.admin.base.control.BaseController;
import com.ys.common.entitys.rbac.SysMember;
import com.ys.common.entitys.rbac.SysResource;
import com.ys.common.entitys.rbac.SysRole;
import com.zyf.result.Msg;
import io.ebean.Ebean;
import io.swagger.annotations.*;
import lombok.DeleteById;
import lombok.Insert;
import lombok.QueryAll;
import lombok.Update;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Api(value = "资源权限", description = "资源权限管理", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestFullController("sysResource")
@Insert(SysResource.class)
@Update(SysResource.class)
@DeleteById(SysResource.class)
@QueryAll(SysResource.class)
public class SysResourceController extends BaseController<SysResource> {

	@GetMapping("getResource")
	public Msg getResource() {
		log.info("用户登录成功后查询资源权限信息");
		SysMember sysMember = (SysMember) SecurityUtils.getSubject().getPrincipal();
		SysRole sysRole = Ebean.find(SysRole.class, sysMember.getRoleId());
		if(sysRole == null){
			Msg.fail("角色不存在");
		}
		List<String> ids = Lists.newArrayList();
		List<SysResource> sysResources = null;
		List<String> resourceIds = sysRole.getResourceList();
		String all = "all";
		if(CollectionUtils.isNotEmpty(resourceIds)) {
			String firstResource = resourceIds.get(0);
			if(resourceIds.size() == 1
					&& all.equalsIgnoreCase(firstResource)){
				sysResources = Ebean.find(SysResource.class).findList();
			}else{
				sysResources = Ebean.find(SysResource.class)
						.where()
						.idIn(resourceIds)
						.findList();
			}
		}
		if(CollectionUtils.isNotEmpty(sysResources)){
			ids = sysResources.stream()
					.map(e -> e.getPermission())
					.collect(Collectors.toList());
		}
		return Msg.ok(ids);
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
	public Msg queryPage(SysResource.Vo vo,
	                     @RequestParam(required = false, defaultValue = "1") int pageNo,
	                     @RequestParam(required = false, defaultValue = "10") int pageSize) {
		return Msg.ok(setPage(setParams(vo), pageNo, pageSize));
	}
}
