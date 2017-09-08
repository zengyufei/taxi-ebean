package ebean.curd;

import com.zzsim.taxi.admin.Application;
import ebean.entity.TestSysMember;
import io.ebean.EbeanServer;
import io.ebean.annotation.Transactional;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CurdTest {

	@Autowired
	private EbeanServer server;

	public TestSysMember getMember() {
		TestSysMember testSysMember = new TestSysMember();
		testSysMember.setAccount("admin");
		testSysMember.setEmail("312636208@qq.com");
		testSysMember.setFlag(true);
		testSysMember.setIdentity("441361199101122411");
		testSysMember.setMobile("13480555121");
		return testSysMember;
	}

	@Before
	public void before(){
		TestSysMember testSysMember = server.find(TestSysMember.class)
				.setIncludeSoftDeletes()
				.where()
				.eq("id", "1")
				.findUnique();
		if(testSysMember == null){
			getMember().insert();
		}
	}

	@Test
	public void test_001_update() {
		TestSysMember temp = server.find(TestSysMember.class)
				.setIncludeSoftDeletes()
				.where()
				.eq("id", "1")
				.findUnique();
		Assert.assertNotNull(temp);

		temp.setMobile("1111111111");
		temp.update();
	}

	@Test
	public void test_002_delete() {
		int effect = server.delete(TestSysMember.class, 1);
		Assert.assertTrue(effect > 0);

		int count = server.find(TestSysMember.class)
				.setIncludeSoftDeletes()
				.where()
				.eq("id", "1")
				.findCount();
		Assert.assertTrue(count > 0);

		server.deletePermanent(TestSysMember.class, 1);
		count = server.find(TestSysMember.class)
				.setIncludeSoftDeletes()
				.where()
				.eq("id", "1")
				.findCount();
		Assert.assertTrue(count == 0);
	}

	@Test(expected = ArithmeticException.class)
	@Transactional
	public void test_003_insert_() {
		getMember().insert();
		int i = 1/0;
		// see log : io.ebean.TXN - txn[1005] Rollback error: java.lang.ArithmeticException: / by zero
	}

}
