package ebean.connect;

/**
 * Created by zengyufei on 2017/7/20.
 */

import com.zzsim.taxi.admin.Application;
import io.ebean.EbeanServer;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class ConnectTest {

	@Autowired
	private EbeanServer server;

	@Test
	public void testHello() {
		Assert.assertNotNull(server);
	}
}
