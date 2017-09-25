package com.zzsim.taxi.admin.base.control.Q;

import com.zzsim.taxi.admin.base.control.AbstractController;
import com.zzsim.taxi.admin.validate.annotation.IdCheck;
import com.zzsim.taxi.core.common.base.Msg;
import com.zzsim.taxi.core.common.base.entiry.IdAbstractEntity;
import io.ebean.Ebean;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

@Validated
public abstract class QidAndAll<T extends IdAbstractEntity> extends AbstractController<T> {

	@ApiOperation(value = "查询单个，不包括删除", notes = "查询单个，不包括删除", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = Msg.SUCCESS_CODE, message = "查询成功", response = Msg.class),
			@ApiResponse(code = Msg.ERROR_CODE, message = "系统错误", response = Msg.class)
	})
	@ApiImplicitParam(value = "查询的 id", required = true, name = "id", defaultValue = "1", paramType = "query", dataType = "long")
	@GetMapping("queryById")
	public Msg queryById(@IdCheck Long id) {
		return Msg.ok(Ebean.find(entityClass, id));
	}

	@ApiOperation(value = "查询单个，包括删除", notes = "查询单个，包括删除", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = Msg.SUCCESS_CODE, message = "查询成功", response = Msg.class),
			@ApiResponse(code = Msg.ERROR_CODE, message = "系统错误", response = Msg.class)
	})
	@ApiImplicitParam(value = "查询的 id", required = true, name = "id", defaultValue = "1", paramType = "query", dataType = "long")
	@GetMapping("queryByIded")
	public Msg queryByIded(@IdCheck Long id) {
		T unique = Ebean.find(entityClass).setIncludeSoftDeletes().setId(id).findUnique();
		return Msg.ok(unique);
	}

	@ApiOperation(value = "查询所有，不包括删除", notes = "查询单个，不包括删除", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = Msg.SUCCESS_CODE, message = "查询成功", response = Msg.class),
			@ApiResponse(code = Msg.ERROR_CODE, message = "系统错误", response = Msg.class)
	})
	@GetMapping("queryAll")
	public Msg queryAll() {
		return Msg.ok(Ebean.find(entityClass).findList());
	}

	@ApiOperation(value = "查询所有，包括删除", notes = "查询所有，包括删除", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = Msg.SUCCESS_CODE, message = "查询成功", response = Msg.class),
			@ApiResponse(code = Msg.ERROR_CODE, message = "系统错误", response = Msg.class)
	})
	@GetMapping("queryAlled")
	public Msg queryAlled() {
		return Msg.ok(Ebean.find(entityClass).setIncludeSoftDeletes().findList());
	}


}

