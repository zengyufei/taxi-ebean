package com.ys.admin.controller.device;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.ys.admin.base.annotations.RestFullController;
import com.ys.admin.base.control.AbstractCommunityController;
import com.ys.admin.util.DownloadFileUtil;
import com.ys.common.entitys.device.Card;
import com.zyf.result.Msg;
import io.ebean.Ebean;
import io.swagger.annotations.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@Api(value = "物理卡", description = "物理卡管理", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestFullController("card")
public class CardController extends AbstractCommunityController<Card, Card.Vo> {

	@ApiOperation(value = "导出", notes = "导出", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = Msg.SUCCESS_CODE, message = "导出", response = Msg.class),
			@ApiResponse(code = Msg.ERROR_CODE, message = "系统错误", response = Msg.class)
	})
	@GetMapping("export")
	public void export(HttpServletResponse resp) throws Exception {
		List<Card> list = Ebean.find(entityClass).findList();
		Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("物理卡","物理卡"),
				entityClass, list);
		DownloadFileUtil.download("物理卡", workbook, resp);
	}

	@PostMapping({"update"})
	@ApiImplicitParam(
			value = "实体",
			paramType = "body",
			name = "obj",
			required = true
	)
	@ApiOperation(
			value = "修改",
			notes = "修改",
			httpMethod = "POST"
	)
	@ApiResponse(
			code = 200,
			message = "修改成功",
			response = Msg.class
	)
	public Msg update(@Validated({Update.class}) Card.Vo t) {
		Card obj = new Card();
		BeanUtils.copyProperties(t, obj);
		Ebean.update(obj);
		return Msg.ok("修改成功");
	}

	@PostMapping({"insert"})
	@ApiImplicitParam(
			value = "实体",
			paramType = "body",
			name = "obj",
			required = true
	)
	@ApiOperation(
			value = "新增",
			notes = "新增",
			httpMethod = "POST"
	)
	@ApiResponse(
			code = 200,
			message = "新增成功",
			response = Msg.class
	)
	public Msg insert(@Validated({Insert.class})Card.Vo t) {
		Card obj = new Card();
		BeanUtils.copyProperties(t, obj);
		Ebean.insert(obj);
		return Msg.ok("新增成功");
	}

	@GetMapping({"removeById"})
	@ApiImplicitParam(
			value = "序号",
			paramType = "query",
			name = "id",
			required = true
	)
	@ApiOperation(
			value = "移除",
			notes = "移除",
			httpMethod = "GET"
	)
	@ApiResponse(
			code = 200,
			message = "移除成功",
			response = Msg.class
	)
	public Msg removeById(@Min(message = "Id 不能为空",value = 1L) @NotNull(message = "Id 不能为空") Long id) {
		Ebean.delete(Card.class, id);
		return Msg.ok("移除成功");
	}

	@GetMapping({"deleteById"})
	@ApiImplicitParam(
			value = "序号",
			paramType = "query",
			name = "id",
			required = true
	)
	@ApiOperation(
			value = "移除",
			notes = "移除",
			httpMethod = "GET"
	)
	@ApiResponse(
			code = 200,
			message = "移除成功",
			response = Msg.class
	)
	public Msg deleteById(@Min(message = "Id 不能为空",value = 1L) @NotNull(message = "Id 不能为空") Long id) {
		Ebean.deletePermanent(Card.class, id);
		return Msg.ok("移除成功");
	}
	@GetMapping({"queryById"})
	@ApiImplicitParam(
			value = "序号",
			paramType = "query",
			name = "id",
			required = true
	)
	@ApiOperation(
			value = "查询单个",
			notes = "查询单个",
			httpMethod = "GET"
	)
	@ApiResponse(
			code = 200,
			message = "查询单个成功",
			response = Msg.class
	)
	public Msg queryById(@Min(message = "Id 不能为空",value = 1L) @NotNull(message = "Id 不能为空") Long id) {
		Card obj = (Card)Ebean.find(Card.class, id);
		return Msg.ok(obj);
	}


}
