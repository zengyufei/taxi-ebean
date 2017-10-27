package com.ys.admin.controller.device;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.ys.admin.base.annotations.RestFullController;
import com.ys.admin.base.control.AbstractRoomController;
import com.ys.admin.util.DownloadFileUtil;
import com.ys.common.entitys.device.Device;
import io.ebean.Ebean;
import io.swagger.annotations.Api;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author zengyufei
 */
@Slf4j
@Api(value = "设备", description = "在线设备管理", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestFullController("device")
@Insert(value = Device.class, vo = Device.Vo.class)
@Update(value = Device.class, vo = Device.Vo.class)
@DeleteById(Device.class)
@QueryAll(Device.class)
@QueryById(Device.class)
@QueryByIds(Device.class)
public class DeviceController extends AbstractRoomController<Device, Device.Vo> {

	@GetMapping("export")
	public void export(HttpServletResponse resp) throws Exception {
		List<Device> list = Ebean.find(entityClass).findList();
		Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("设备","设备"),
				entityClass, list);
		DownloadFileUtil.download("设备", workbook, resp);
	}
}
