package com.zzsim.taxi.admin.base.control.R;

import com.zzsim.taxi.admin.base.control.U.UCQall;
import com.zzsim.taxi.admin.validate.annotation.IdCheck;
import com.zzsim.taxi.core.common.base.Msg;
import com.zzsim.taxi.core.common.base.entiry.IdAbstractEntity;
import io.ebean.Ebean;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;

@Validated
public abstract class RUCQall<T extends IdAbstractEntity> extends UCQall<T> {

	@ApiOperation(value = "删除", notes = "删除", httpMethod = "GET", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@ApiResponse(code = Msg.SUCCESS_CODE, message = "删除成功", response = Msg.class)
	@ApiImplicitParam(value = "序号", required = true, name = "id", paramType = "query")
	@GetMapping("remoteById")
	public Msg remoteById(@IdCheck Long id) {
		Ebean.delete(entityClass, id);
		return Msg.ok("删除成功");
	}

	@ApiOperation(value = "删除", notes = "删除", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiResponse(code = Msg.SUCCESS_CODE, message = "删除成功", response = Msg.class)
	@ApiImplicitParam(value = "序号", required = true, name = "id")
	@DeleteMapping("remoteById")
	public Msg remoteByIdForDeleteMapping(@IdCheck Long id) {
		Ebean.delete(entityClass, id);
		return Msg.ok("删除成功");
	}
}
