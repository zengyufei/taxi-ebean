import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.ys.admin.util.AesSecret;
import com.ys.common.enums.EquEnum;
import ebean.entity.TestSysMember;
import org.apache.poi.ss.usermodel.Workbook;
import org.assertj.core.util.Lists;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

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
		String abc = AesSecret.encrypt("abc");
		String decrypt = AesSecret.decrypt("1|1505878598846|admin|a17ed13ae8d2eb291a2ef804fb14dee6");
		// String decrypt = AesSecret.decrypt(Hex.hexStr2Bytes("1F8FF748C8800CE3F2325F61D8E261F48D1103690287AC2CBE1B02B64888E8C48992D746FC1B7C83CB42C78894BE01F8F2525C08BE67BFC5C53112F97A421433"), AesKey.secretKey());
		System.out.println(decrypt);
	}

	@Test
	public void test03() throws Exception {
		List<TestSysMember> list = Lists.newArrayList();

		TestSysMember testSysMember = new TestSysMember();
		testSysMember.setAccount("admin ").setPassword("admin").setEquEnum(EquEnum.DoorMachine);
		list.add(testSysMember);

		Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("测试","sheetName"),
				TestSysMember.class, list);
		FileOutputStream fos = new FileOutputStream("e:/dowork/test.xls");
		workbook.write(fos);
		fos.close();
	}

	@Test
	public void test04() {
		try {
			ImportParams params = new ImportParams();
			params.setTitleRows(1);
			params.setHeadRows(1);
			params.setNeedVerfiy(true);
			List<TestSysMember> result = ExcelImportUtil.importExcel(
					new File("e:/dowork/test.xls"),
					TestSysMember.class, params);
			for (int i = 0; i < result.size(); i++) {
				System.out.println(result.get(i).getAccount());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
