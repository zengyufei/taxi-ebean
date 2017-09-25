package com.zzsim.taxi.admin.base.control.D;

import com.zzsim.taxi.admin.base.control.R.RUCQpage;
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
public abstract class DRUCQpage<T extends IdAbstractEntity> extends RUCQpage<T> {

	@ApiOperation(value = "物理删除", notes = "物理删除", httpMethod = "GET", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@ApiResponse(code = Msg.SUCCESS_CODE, message = "物理删除成功", response = Msg.class)
	@ApiImplicitParam(value = "序号", required = true, name = "id", paramType = "query")
	@GetMapping("deleteByIded")
	public Msg deleteByIded(@IdCheck Long id) {
		Ebean.deletePermanent(entityClass, id);
		return Msg.ok("物理删除成功");
	}

	@ApiOperation(value = "物理删除", notes = "物理删除", httpMethod = "DELETE", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiResponse(code = Msg.SUCCESS_CODE, message = "物理删除成功", response = Msg.class)
	@ApiImplicitParam(value = "序号", required = true, name = "id")
	@DeleteMapping("deleteByIded")
	public Msg deleteByIdForDeleteMapping(@IdCheck Long id) {
		Ebean.deletePermanent(entityClass, id);
		return Msg.ok("物理删除成功");
	}
}
