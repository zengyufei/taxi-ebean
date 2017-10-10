package com.ys.dao.rbac;

import com.ys.admin.Application;
import com.ys.common.entitys.rbac.SysMember;
import com.ys.common.enums.SexEnum;
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
public class SysMemberTest {

	@Autowired
	private EbeanServer server;

	public SysMember getOrg() {
		SysMember sysMember = new SysMember();
		sysMember.setAccount("admin");
		sysMember.setPassword("admin");
		sysMember.setRealName("真正管理员");
		sysMember.setIdentity("441381199101122444");
		sysMember.setMobile("13480555111");
		sysMember.setEmail("31263620@qq.com");
		sysMember.setQq("312636208");

		sysMember.setRoleId(1L);

		sysMember.setOrgNo("sz-001");
		sysMember.setSexEnum(SexEnum.Male);
		sysMember.setRemark("adsassdfsdfsdf");
		return sysMember;
	}

	@Before
	public void before(){
		SysMember sysMember = server.find(SysMember.class)
				.setIncludeSoftDeletes()
				.where()
				.eq("id", "1")
				.findUnique();
		if(sysMember == null){
			getOrg().insert();
		}
	}

	@Test
	public void test_001_update() {
		SysMember temp = server.find(SysMember.class)
				.setIncludeSoftDeletes()
				.where()
				.eq("id", "1")
				.findUnique();
		Assert.assertNotNull(temp);

		temp.setRealName("中智");
		temp.update();
	}

	@Test
	public void test_002_delete() {
		int effect = server.delete(SysMember.class, 1);
		Assert.assertTrue(effect > 0);

		int count = server.find(SysMember.class)
				.setIncludeSoftDeletes()
				.where()
				.eq("id", "1")
				.findCount();
		Assert.assertTrue(count > 0);

		server.deletePermanent(SysMember.class, 1);
		count = server.find(SysMember.class)
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
