package com.ys.admin.controller.house;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import cn.afterturn.easypoi.excel.entity.result.ExcelImportResult;
import com.ys.admin.base.annotations.RestFullController;
import com.ys.admin.base.control.AbstractRoomController;
import com.ys.admin.base.poi.UserInfoMapImportHanlder;
import com.ys.admin.util.DownloadFileUtil;
import com.ys.common.entitys.house.User;
import com.ys.common.entitys.house.UserInfo;
import com.ys.common.enums.AuthEnum;
import com.ys.common.enums.StayEnum;
import com.ys.common.utils.EnumUtils;
import com.zyf.result.Msg;
import io.ebean.Ebean;
import io.ebean.Transaction;
import io.swagger.annotations.Api;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Api(value = "住户", description = "小区住户管理", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestFullController("userInfo")
@Update(value = UserInfo.class, vo = UserInfo.Vo.class)
@Insert(value = UserInfo.class, vo = UserInfo.Vo.class)
@RemoveById(UserInfo.class)
@DeleteById(UserInfo.class)
@QueryById(UserInfo.class)
public class UserInfoController extends AbstractRoomController<UserInfo, UserInfo.Vo> {

	@PostMapping("import")
	public Msg importFile(@RequestParam("file") MultipartFile file) throws Exception {
		Transaction tx = Ebean.beginTransaction();
		try {
			UserInfoMapImportHanlder userInfoHanlder = new UserInfoMapImportHanlder();
			ImportParams params = new ImportParams();
			params.setTitleRows(1);
			params.setHeadRows(1);
			params.setDataHanlder(userInfoHanlder);
			params.setNeedVerfiy(true);
			ExcelImportResult<LinkedHashMap<String, Object>> objectExcelImportResult = ExcelImportUtil.importExcelMore(file.getInputStream(),
					Map.class,
					params);
			for (LinkedHashMap<String, Object> map : objectExcelImportResult.getList()) {
				String phone = map.containsKey(userInfoHanlder.realName) ? map.get(userInfoHanlder.phone).toString() : "";
				String realName = map.containsKey(userInfoHanlder.realName) ? map.get(userInfoHanlder.realName).toString() : "";
				String idCard = map.get(userInfoHanlder.idCard) != null ? map.get(userInfoHanlder.idCard).toString() : "";
				int authEnumIndex = map.containsKey(userInfoHanlder.authEnum) ? Integer.parseInt(map.get(userInfoHanlder.authEnum).toString()) : 3;
				int stayEnumIndex = map.containsKey(userInfoHanlder.stayEnum) ? Integer.parseInt(map.get(userInfoHanlder.stayEnum).toString()) : 1;
				AuthEnum authEnum = EnumUtils.getEnum(AuthEnum.class, authEnumIndex);
				StayEnum stayEnum = EnumUtils.getEnum(StayEnum.class, stayEnumIndex);
				Long communityId = map.containsKey(userInfoHanlder.communityId) ? Long.parseLong(map.get(userInfoHanlder.communityId).toString()) : 0;
				Long buildingId = map.containsKey(userInfoHanlder.buildingId) ? Long.parseLong(map.get(userInfoHanlder.buildingId).toString()) : 0;
				Long unitId = map.containsKey(userInfoHanlder.unitId) ? Long.parseLong(map.get(userInfoHanlder.unitId).toString()) : 0;

				User user = new User().setIdCard(idCard)
						.setPhone(phone)
						.setUserName(phone)
						.setPassword("888888")
						.setRealName(realName)
						.setActiveFlag(true);
				user.save();

				UserInfo userInfo = (UserInfo) new UserInfo()
						.setUser(user)
						.setAuthEnum(authEnum)
						.setStayEnum(stayEnum)
						.setAuthFlag(true)
						.setCommunityId(communityId)
						.setBuildingId(buildingId)
						.setUnitId(unitId);
				userInfo.save();
			}
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		}
		tx.commit();
		return Msg.ok("导入成功");
	}

	@GetMapping("export")
	public void export(HttpServletResponse resp) throws Exception {
		List<UserInfo> list = Ebean.find(entityClass).findList();
		Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("住户","住户"),
				entityClass, list);
		DownloadFileUtil.download("住户", workbook, resp);
	}
}
