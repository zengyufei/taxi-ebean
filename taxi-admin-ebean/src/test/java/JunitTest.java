import com.ys.admin.util.AESSecret;
import org.junit.Test;

/**
 * Created by admin on 2017/9/19.
 */
public class JunitTest {

	@Test
	public void test01() {
		String val = "002";
		int i = Integer.parseInt(val);
		System.out.println(i);
	}

	@Test
	public void test02() throws Exception {
		String abc = AESSecret.encrypt("abc");
		String decrypt = AESSecret.decrypt("1|1505878598846|admin|a17ed13ae8d2eb291a2ef804fb14dee6");
		// String decrypt = AESSecret.decrypt(Hex.hexStr2Bytes("1F8FF748C8800CE3F2325F61D8E261F48D1103690287AC2CBE1B02B64888E8C48992D746FC1B7C83CB42C78894BE01F8F2525C08BE67BFC5C53112F97A421433"), AESKey.secretKey());
		System.out.println(decrypt);
	}

}
