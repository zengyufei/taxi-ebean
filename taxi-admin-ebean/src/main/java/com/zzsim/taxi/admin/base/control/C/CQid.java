package com.zzsim.taxi.admin.base.control.C;

import com.zzsim.taxi.admin.base.control.Q.Qid;
import com.zzsim.taxi.admin.validate.groups.Insert;
import com.zzsim.taxi.core.common.base.Msg;
import com.zzsim.taxi.core.common.base.entiry.IdAbstractEntity;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Validated
public abstract class CQid<T extends IdAbstractEntity> extends Qid<T> {

	protected void insertBefore(T obj) {}

	@ApiOperation(value = "新增", notes = "新增", httpMethod = "POST", produces = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@ApiResponse(code = Msg.SUCCESS_CODE, message = "新增成功", response = Msg.class)
	@ApiImplicitParam(value = "实体", required = true, name = "obj", paramType = "body")
	@PostMapping("insert")
	public Msg insert(@Validated(value = Insert.class) T obj) {
		insertBefore(obj);
		obj.insert();
		return Msg.ok("新增成功");
	}

	@ApiOperation(value = "新增", notes = "新增", httpMethod = "PUT", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiResponse(code = Msg.SUCCESS_CODE, message = "新增成功", response = Msg.class)
	@ApiImplicitParam(value = "实体", required = true, name = "obj")
	@PutMapping("insert")
	public Msg insertJSON(@Validated(value = Insert.class) @RequestBody T obj) {
		insertBefore(obj);
		obj.insert();
		return Msg.ok("新增成功");
	}
}
