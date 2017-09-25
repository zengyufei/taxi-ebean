package com.zzsim.taxi.admin.base.control.Q;

import com.zzsim.taxi.admin.base.control.AbstractController;
import com.zzsim.taxi.admin.validate.annotation.IdCheck;
import com.zzsim.taxi.core.common.base.Msg;
import com.zzsim.taxi.core.common.base.entiry.IdAbstractEntity;
import io.ebean.Ebean;
import io.swagger.annotations.*;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Validated
public abstract class Q<T extends IdAbstractEntity> extends AbstractController<T> {

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

	@ApiOperation(value = "批量查询，不包括删除", notes = "批量查询，不包括删除", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = Msg.SUCCESS_CODE, message = "查询成功", response = Msg.class),
			@ApiResponse(code = Msg.ERROR_CODE, message = "系统错误", response = Msg.class)
	})
	@ApiImplicitParam(value = "id 集合", name = "ids", defaultValue = "1,2,3", paramType = "query", dataType = "string")
	@GetMapping("queryByIds")
	public Msg queryByIds(@NotBlank(message = "id 集合不能为空") String ids) {
		List<T> list = Ebean.find(entityClass)
				.where()
				.idIn(Arrays.asList(ids.split(",")))
				.findList();
		return Msg.ok(list);
	}

	@ApiOperation(value = "批量查询，包括删除", notes = "批量查询，包括删除", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = Msg.SUCCESS_CODE, message = "查询成功", response = Msg.class),
			@ApiResponse(code = Msg.ERROR_CODE, message = "系统错误", response = Msg.class)
	})
	@ApiImplicitParam(value = "id 集合", name = "ids", defaultValue = "1,2,3", paramType = "query", dataType = "string")
	@GetMapping("queryByIdsed")
	public Msg queryByIdsed(@NotBlank(message = "id 集合不能为空") String ids) {
		List<T> list = Ebean.find(entityClass)
				.setIncludeSoftDeletes()
				.where()
				.idIn(Arrays.asList(ids.split(",")))
				.findList();
		return Msg.ok(list);
	}

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
