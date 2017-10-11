package com.ys.admin.controller.rbac;

import com.google.common.collect.Lists;
import com.ys.admin.base.annotations.RestFullController;
import com.ys.admin.base.control.BaseController;
import com.ys.common.entitys.rbac.SysMember;
import com.ys.common.entitys.rbac.SysResource;
import com.ys.common.entitys.rbac.SysRole;
import com.zyf.result.Msg;
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
	public Msg getRole() {
		log.info("用户登录成功后查询资源权限信息");
		SysMember sysMember = (SysMember) SecurityUtils.getSubject().getPrincipal();
		SysRole sysRole = Ebean.find(SysRole.class, sysMember.getRoleId());
		if(sysRole == null){
			Msg.fail("角色不存在");
		}
		List<String> resourceList = sysRole.getResourceList();
		List<SysResource> sysResources = null;
		if(resourceList != null && resourceList.size() == 1 && "all".equalsIgnoreCase(resourceList.get(0))){
			sysResources = Ebean.find(SysResource.class).findList();
		}else if(resourceList != null){
			sysResources = Ebean.find(SysResource.class)
					.where()
					.idIn(resourceList)
					.findList();
		}
		List<String> ids = Lists.newArrayList();
		if(sysResources != null && !sysResources.isEmpty()){
			ids = sysResources.stream()
					.map(e -> e.getPermission())
					.collect(Collectors.toList());
		}
		return Msg.ok(ids);
	}
}
