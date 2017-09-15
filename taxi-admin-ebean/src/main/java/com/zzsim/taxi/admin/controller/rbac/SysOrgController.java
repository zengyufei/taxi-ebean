package com.zzsim.taxi.admin.controller.rbac;

import com.zzsim.taxi.admin.base.BaseController;
import com.zzsim.taxi.admin.base.annotations.RestFullController;
import com.zzsim.taxi.core.common.base.Msg;
import com.zzsim.taxi.core.common.entitys.rbac.SysOrg;
import io.ebean.Ebean;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RestFullController("sysOrg")
public class SysOrgController extends BaseController<SysOrg> {

	@RequestMapping("queryOrgNoAndOrgName")
	public Msg queryOrgNoAndOrgName(){
		List<SysOrg> list = Ebean.find(SysOrg.class)
				.setDisableLazyLoading(true)
				.select("orgNo, orgName")
				.findList();
		return Msg.ok(list);
	}
}
