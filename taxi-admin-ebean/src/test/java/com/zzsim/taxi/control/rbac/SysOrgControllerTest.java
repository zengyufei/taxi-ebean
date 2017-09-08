package com.zzsim.taxi.control.rbac;

import com.zzsim.taxi.admin.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SysOrgControllerTest {

	@Autowired
	private TestRestTemplate testRestTemplate;

	@Test
	public void test_001_queryAll() {
		String result = testRestTemplate.getForObject("/sysOrg/queryAll", String.class);
		System.out.println(result);
	}

}
