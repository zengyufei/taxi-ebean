package com.zzsim.taxi.admin.base.control.Q;

import com.zzsim.taxi.admin.base.control.AbstractController;
import com.zzsim.taxi.core.common.base.Msg;
import com.zzsim.taxi.core.common.base.entiry.IdAbstractEntity;
import io.ebean.Ebean;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;

@Validated
public abstract class Qids<T extends IdAbstractEntity> extends AbstractController<T> {

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

}





