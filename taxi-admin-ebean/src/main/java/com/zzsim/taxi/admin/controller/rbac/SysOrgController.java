package com.zzsim.taxi.admin.controller.rbac;

import com.zzsim.taxi.core.common.entitys.rbac.SysOrg;
import com.zzsim.taxi.core.common.options.rbac.SysOrgOption;
import io.ebean.Ebean;
import io.ebean.ExpressionList;
import io.ebean.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("sysOrg")
public class SysOrgController {

	@GetMapping("queryAll")
	public ResponseEntity queryAll(){
		return ResponseEntity.status(200).body(Ebean.find(SysOrg.class).findList());
	}

	@GetMapping("queryPage")
	public ResponseEntity queryPage(SysOrgOption option){
		ExpressionList<SysOrg> where = Ebean.find(SysOrg.class).where();
		return ResponseEntity.status(200).body(where.findList());
	}

}
