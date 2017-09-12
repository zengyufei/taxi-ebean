package com.zzsim.taxi.admin.controller.rbac;

import com.zzsim.taxi.admin.base.BaseController;
import com.zzsim.taxi.core.common.base.Msg;
import com.zzsim.taxi.core.common.entitys.rbac.SysOrg;
import com.zzsim.taxi.core.common.options.rbac.SysOrgOption;
import io.ebean.Ebean;
import io.ebean.PagedList;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

@RestController
@RequestMapping("sysOrg")
public class SysOrgController extends BaseController {

	@GetMapping("queryById")
	public Msg queryById(Long id) {
		return Msg.ok(Ebean.find(SysOrg.class, id));
	}

	@GetMapping("queryAll")
	public Msg queryAll() {
		return Msg.ok(Ebean.find(SysOrg.class).findList());
	}

	@GetMapping("queryPage")
	public Msg queryPage(SysOrgOption option, int pageNo, int pageSize) {
		return Msg.ok(setPage(setParams(SysOrg.class, option), pageNo, pageSize));
	}

	@PostMapping("insert")
	public Msg insert(SysOrg sysOrg) {
		try {
			chechArg(sysOrg);
		} catch (Exception e) {
			return Msg.fail("新增失败，" + e.getMessage());
		}
		sysOrg.insert();
		return Msg.ok("新增成功");
	}

	@PostMapping("update")
	public Msg<String> update(SysOrg sysOrg) {
		try {
			checkArgument(checkNotNull(sysOrg.getId(), "id 不能为空") > 0, "id 不能为空");
			chechArg(sysOrg);
		} catch (Exception e) {
			return Msg.fail("新增失败，" + e.getMessage());
		}
		sysOrg.update();
		return Msg.ok("修改成功");
	}

	@GetMapping("deleteById")
	public Msg deleteById(Long id) {
		try {
			checkArgument(checkNotNull(id, "id 不能为空") > 0, "id 不能为空");
		} catch (Exception e) {
			return Msg.fail("新增失败，" + e.getMessage());
		}
		Ebean.delete(SysOrg.class, id);
		return Msg.ok("删除成功");
	}

	private void chechArg(SysOrg sysOrg) throws Exception {
		checkArgument(StringUtils.isNotBlank(sysOrg.getOrgNo()), "组织机构不能为空");
		checkArgument(StringUtils.isNotBlank(sysOrg.getParentOrgNo()), "上级组织机构不能为空");
		checkArgument(StringUtils.isNotBlank(sysOrg.getOrgName()), "组织机构名称不能位空");
		checkArgument(Optional.ofNullable(sysOrg.getProvince()).orElse(0) > 0, "省份不能为空");
		checkArgument(Optional.ofNullable(sysOrg.getCity()).orElse(0) > 0, "城市不能为空");
	}

}
