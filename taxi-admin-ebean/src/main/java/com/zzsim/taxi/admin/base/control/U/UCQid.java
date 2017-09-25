package com.zzsim.taxi.admin.base.control.U;

import com.zzsim.taxi.admin.base.control.C.CQid;
import com.zzsim.taxi.admin.validate.groups.Update;
import com.zzsim.taxi.core.common.base.Msg;
import com.zzsim.taxi.core.common.base.entiry.IdAbstractEntity;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Validated
public abstract class UCQid<T extends IdAbstractEntity> extends CQid<T> {

	protected void updateBefore(T obj) {}

	@ApiOperation(value = "修改", notes = "修改", httpMethod = "POST", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@ApiResponse(code = Msg.SUCCESS_CODE, message = "修改成功", response = Msg.class)
	@ApiImplicitParam(value = "实体", required = true, name = "obj", paramType = "body")
	@PostMapping("update")
	public Msg update(@Validated(value = Update.class) T obj) {
		updateBefore(obj);
		obj.update();
		return Msg.ok("修改成功");
	}

	@ApiOperation(value = "修改", notes = "修改", httpMethod = "PUT", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiResponse(code = Msg.SUCCESS_CODE, message = "修改成功", response = Msg.class)
	@ApiImplicitParam(value = "实体", required = true, name = "obj")
	@PatchMapping("update")
	public Msg updateJSON(@Validated(value = Update.class) @RequestBody T obj) {
		updateBefore(obj);
		obj.update();
		return Msg.ok("修改成功");
	}
}
