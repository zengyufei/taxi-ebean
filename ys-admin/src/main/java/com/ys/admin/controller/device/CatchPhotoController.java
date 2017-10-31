package com.ys.admin.controller.device;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.ys.admin.base.annotations.RestFullController;
import com.ys.admin.base.control.AbstractCommunityController;
import com.ys.admin.util.DownloadFileUtil;
import com.ys.common.entitys.device.CatchPhoto;
import com.zyf.result.Msg;
import io.ebean.Ebean;
import io.swagger.annotations.*;
import lombok.QueryAll;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Slf4j
@Api(value = "留影记录", description = "留影记录管理", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestFullController("catchPhoto")
@QueryAll(CatchPhoto.class)
public class CatchPhotoController extends AbstractCommunityController<CatchPhoto, CatchPhoto> {

	@ApiOperation(value = "导出", notes = "导出", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = Msg.SUCCESS_CODE, message = "导出", response = Msg.class),
			@ApiResponse(code = Msg.ERROR_CODE, message = "系统错误", response = Msg.class)
	})
	@GetMapping("export")
	public void export(HttpServletResponse resp) throws Exception {
		List<CatchPhoto> list = Ebean.find(entityClass).findList();
		Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("留影记录","留影记录"),
				entityClass, list);
		DownloadFileUtil.download("留影记录", workbook, resp);
	}

	@ApiOperation(value = "条件查询分页，不包括删除", notes = "条件查询分页，不包括删除", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = Msg.SUCCESS_CODE, message = "查询成功", response = Msg.class),
			@ApiResponse(code = Msg.ERROR_CODE, message = "系统错误", response = Msg.class)
	})
	@ApiImplicitParams({
			@ApiImplicitParam(value = "条件", name = "vo", paramType = "query"),
			@ApiImplicitParam(value = "当前页", name = "pageNo", defaultValue = "1", paramType = "query", dataType = "int"),
			@ApiImplicitParam(value = "每页页数", name = "pageSize", defaultValue = "1", paramType = "query", dataType = "int"),
	})
	@GetMapping("queryPage")
	public Msg queryPage(CatchPhoto vo,
	                     @RequestParam(required = false, defaultValue = "1") int pageNo,
	                     @RequestParam(required = false, defaultValue = "10") int pageSize) {
		return Msg.ok(setPage(setParams(vo), pageNo, pageSize));
	}
}
