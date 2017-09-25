package com.zzsim.taxi.admin.base.control.Q;

import com.zzsim.taxi.admin.base.control.AbstractController;
import com.zzsim.taxi.core.common.base.Msg;
import com.zzsim.taxi.core.common.base.entiry.IdAbstractEntity;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Validated
public abstract class Qpage<T extends IdAbstractEntity> extends AbstractController<T> {

	@ApiOperation(value = "条件查询分页，不包括删除", notes = "条件查询分页，不包括删除", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = Msg.SUCCESS_CODE, message = "查询成功", response = Msg.class),
			@ApiResponse(code = Msg.ERROR_CODE, message = "系统错误", response = Msg.class)
	})
	@ApiImplicitParams({
			@ApiImplicitParam(value = "条件", required = false, name = "t", paramType = "query"),
			@ApiImplicitParam(value = "当前页", name = "pageNo", defaultValue = "1", paramType = "query", dataType = "int"),
			@ApiImplicitParam(value = "每页页数", name = "pageSize", defaultValue = "1", paramType = "query", dataType = "int"),
	})
	@GetMapping("queryPage")
	public Msg queryPage(T t,
	                     @RequestParam(required = false, defaultValue = "1") int pageNo,
	                     @RequestParam(required = false, defaultValue = "10") int pageSize) {
		return Msg.ok(setPage(setParams(t), pageNo, pageSize));
	}

	@ApiOperation(value = "条件查询分页，包括删除", notes = "条件查询分页，包括删除", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = Msg.SUCCESS_CODE, message = "查询成功", response = Msg.class),
			@ApiResponse(code = Msg.ERROR_CODE, message = "系统错误", response = Msg.class)
	})
	@ApiImplicitParams({
			@ApiImplicitParam(value = "条件", required = false, name = "t", paramType = "query"),
			@ApiImplicitParam(value = "当前页", name = "pageNo", defaultValue = "1", paramType = "query", dataType = "int"),
			@ApiImplicitParam(value = "每页页数", name = "pageSize", defaultValue = "1", paramType = "query", dataType = "int"),
	})
	@GetMapping("queryPaged")
	public Msg queryPaged(T t,
	                      @RequestParam(required = false, defaultValue = "1") int pageNo,
	                      @RequestParam(required = false, defaultValue = "10") int pageSize) {
		return Msg.ok(setPage(setParams(t).setIncludeSoftDeletes().where(), pageNo, pageSize));
	}


}
