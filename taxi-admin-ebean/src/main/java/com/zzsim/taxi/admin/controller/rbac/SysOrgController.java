package com.zzsim.taxi.admin.controller.rbac;

import com.zzsim.taxi.admin.base.BaseController;
import com.zzsim.taxi.admin.base.annotations.RestFullController;
import com.zzsim.taxi.admin.service.rbac.SysOrgService;
import com.zzsim.taxi.admin.validate.groups.Insert;
import com.zzsim.taxi.core.common.base.Msg;
import com.zzsim.taxi.core.common.entitys.rbac.SysOrg;
import io.ebean.Ebean;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Api(value = "组织机构", description = "组织机构管理", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestFullController("sysOrg")
public class SysOrgController extends BaseController<SysOrg> {

	@Autowired
	private SysOrgService sysOrgService;

	@Override
	@RequestMapping("insert")
	public Msg insert(@Validated(value = Insert.class) SysOrg obj) {
		String createOrgNo = sysOrgService.createOrgNo(obj.getParentOrgNo());
		obj.setOrgNo(createOrgNo);
		return super.insert(obj);
	}

	@RequestMapping("queryOrgNoAndOrgName")
	public Msg queryOrgNoAndOrgName() {
		List<SysOrg> list = Ebean.find(SysOrg.class)
				.setDisableLazyLoading(true)
				.select("orgNo, orgName")
				.findList();
		return Msg.ok(list);
	}

}
