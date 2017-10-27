package com.ys.dao.rbac;

import com.ys.Application;
import com.ys.common.entitys.rbac.SysOrg;
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

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SysOrgTest {

	@Autowired
	private EbeanServer server;

	public SysOrg getOrg() {
		SysOrg sysOrg = new SysOrg();
		sysOrg.setOrgNo("sz-001");
		sysOrg.setOrgName("中智仿真");
		sysOrg.setDescription("深圳中智仿真");
		sysOrg.setParentOrgNo("-1");
		sysOrg.setProvince(44);
		sysOrg.setCity(4401);
		sysOrg.setAddress("广仁大楼");
		return sysOrg;
	}

	@Before
	public void before(){
		SysOrg sysOrg = server.find(SysOrg.class)
				.setIncludeSoftDeletes()
				.where()
				.eq("id", "1")
				.findUnique();
		if(sysOrg == null){
			getOrg().insert();
		}
	}

	@Test
	public void test_001_update() {
		SysOrg temp = server.find(SysOrg.class)
				.setIncludeSoftDeletes()
				.where()
				.eq("id", "1")
				.findUnique();
		Assert.assertNotNull(temp);

		temp.setDescription("中智");
		temp.update();
	}

	@Test
	public void test_002_delete() {
		int effect = server.delete(SysOrg.class, 1);
		Assert.assertTrue(effect > 0);

		int count = server.find(SysOrg.class)
				.setIncludeSoftDeletes()
				.where()
				.eq("id", "1")
				.findCount();
		Assert.assertTrue(count > 0);

		server.deletePermanent(SysOrg.class, 1);
		count = server.find(SysOrg.class)
				.setIncludeSoftDeletes()
				.where()
				.eq("id", "1")
				.findCount();
		Assert.assertTrue(count == 0);
	}

	@Test(expected = ArithmeticException.class)
	@Transactional
	public void test_003_insert_() {
		getOrg().insert();
		int i = 1/0;
		// see log : io.ebean.TXN - txn[1005] Rollback error: java.lang.ArithmeticException: / by zero
	}

}
