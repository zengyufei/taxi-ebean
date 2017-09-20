package com.zzsim.taxi.dao.rbac;

import com.zzsim.taxi.admin.Application;
import com.zzsim.taxi.core.common.entitys.rbac.SysRole;
import io.ebean.EbeanServer;
import io.ebean.annotation.Transactional;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SysRoleTest {

	@Autowired
	private EbeanServer server;

	public SysRole getSysRole() {
		SysRole sysRole = new SysRole();
		sysRole.setRoleName("管理员");
		sysRole.setOrgNo("sz-001");
		sysRole.setDescription("adsassdfsdfsdf");
		List<String> resourceList = sysRole.getResourceList();
		resourceList.add("all");
		// sysRole.setResourceList("1,2,3,4");
		return sysRole;
	}

	@Before
	public void before(){
		SysRole sysRole = server.find(SysRole.class)
				.setIncludeSoftDeletes()
				.where()
				.eq("id", "1")
				.findUnique();
		if(sysRole == null){
			getSysRole().insert();
		}
	}

	@Test
	public void test_001_update() {
		SysRole temp = server.find(SysRole.class)
				.setIncludeSoftDeletes()
				.where()
				.eq("id", "1")
				.findUnique();
		Assert.assertNotNull(temp);

		temp.setRoleName("中智管理员");
		temp.update();
	}

	@Test
	public void test_002_delete() {
		int effect = server.delete(SysRole.class, 1);
		Assert.assertTrue(effect > 0);

		int count = server.find(SysRole.class)
				.setIncludeSoftDeletes()
				.where()
				.eq("id", "1")
				.findCount();
		Assert.assertTrue(count > 0);

		server.deletePermanent(SysRole.class, 1);
		count = server.find(SysRole.class)
				.setIncludeSoftDeletes()
				.where()
				.eq("id", "1")
				.findCount();
		Assert.assertTrue(count == 0);
	}

	@Test(expected = ArithmeticException.class)
	@Transactional
	public void test_003_insert_() {
		getSysRole().insert();
		int i = 1/0;
		// see log : io.ebean.TXN - txn[1005] Rollback error: java.lang.ArithmeticException: / by zero
	}

}
