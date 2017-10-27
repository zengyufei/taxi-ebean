package com.ys.admin.controller.device;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.ys.admin.base.annotations.RestFullController;
import com.ys.admin.base.control.AbstractCommunityController;
import com.ys.admin.util.DownloadFileUtil;
import com.ys.common.entitys.device.OpenDoorLog;
import io.ebean.Ebean;
import io.swagger.annotations.Api;
import lombok.QueryAll;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@Api(value = "开门记录", description = "开门记录管理", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestFullController("openDoorLog")
@QueryAll(OpenDoorLog.class)
public class OpenDoorLogController extends AbstractCommunityController<OpenDoorLog, OpenDoorLog.Vo> {

	@GetMapping("export")
	public void export(HttpServletResponse resp) throws Exception {
		List<OpenDoorLog> list = Ebean.find(entityClass).findList();
		Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("开门记录","开门记录"),
				entityClass, list);
		DownloadFileUtil.download("开门记录", workbook, resp);
	}
}
