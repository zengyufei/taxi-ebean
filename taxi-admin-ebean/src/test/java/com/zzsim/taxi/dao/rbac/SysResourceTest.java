package com.zzsim.taxi.dao.rbac;

import com.zzsim.taxi.admin.Application;
import com.zzsim.taxi.core.common.entitys.rbac.SysResource;
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
public class SysResourceTest {

	@Autowired
	private EbeanServer server;

	public SysResource getSysResource() {
		SysResource SysResource = new SysResource();
		SysResource.setName("系统管理");
		SysResource.setParentId(-1L);
		SysResource.setPermission("sys:*");
		return SysResource;
	}

	@Before
	public void before(){
		SysResource SysResource = server.find(SysResource.class)
				.setIncludeSoftDeletes()
				.where()
				.eq("id", "1")
				.findUnique();
		if(SysResource == null){
			getSysResource().insert();
		}
	}

	@Test
	public void test_001_update() {
		SysResource temp = server.find(SysResource.class)
				.setIncludeSoftDeletes()
				.where()
				.eq("id", "1")
				.findUnique();
		Assert.assertNotNull(temp);

		temp.setName("用户管理");
		temp.update();
	}

	@Test
	public void test_002_delete() {
		int effect = server.delete(SysResource.class, 1);
		Assert.assertTrue(effect > 0);

		int count = server.find(SysResource.class)
				.setIncludeSoftDeletes()
				.where()
				.eq("id", "1")
				.findCount();
		Assert.assertTrue(count > 0);

		server.deletePermanent(SysResource.class, 1);
		count = server.find(SysResource.class)
				.setIncludeSoftDeletes()
				.where()
				.eq("id", "1")
				.findCount();
		Assert.assertTrue(count == 0);
	}

	@Test(expected = ArithmeticException.class)
	@Transactional
	public void test_003_insert_() {
		getSysResource().insert();
		int i = 1/0;
		// see log : io.ebean.TXN - txn[1005] Rollback error: java.lang.ArithmeticException: / by zero
	}

}
