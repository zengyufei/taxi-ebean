package com.ys.admin.base.control;

import com.ys.common.base.entiry.AbstractIdEntity;
import com.zyf.result.Msg;
import io.ebean.Ebean;
import io.swagger.annotations.*;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

public abstract class AbstractUnitController<T extends AbstractIdEntity, S extends AbstractIdEntity> extends AbstractController<T> {

	@GetMapping("queryByCommunityId")
	public Msg queryByCommunityId(@Min(value = 1, message = "查询的 id 不能为空")
	                              @NotNull(message = "id 不能为空")
			                              Long id) {
		List<T> result = Ebean.find(entityClass)
				.where()
				.eq("communityId", id)
				.findList();
		return Msg.ok(result);
	}

	@GetMapping("queryByBuildingId")
	public Msg queryByBuildingId(@Min(value = 1, message = "查询的 id 不能为空")
	                             @NotNull(message = "id 不能为空")
			                             Long id) {
		List<T> result = Ebean.find(entityClass)
				.where()
				.eq("buildingId", id)
				.findList();
		return Msg.ok(result);
	}

	@GetMapping("queryByUnitId")
	public Msg queryByUnitId(@Min(value = 1, message = "查询的 id 不能为空")
	                         @NotNull(message = "id 不能为空")
			                         Long id) {
		List<T> result = Ebean.find(entityClass)
				.where()
				.eq("unitId", id)
				.findList();
		return Msg.ok(result);
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
	public Msg queryPage(S vo,
	                     @RequestParam(required = false, defaultValue = "1") int pageNo,
	                     @RequestParam(required = false, defaultValue = "10") int pageSize) {
		return Msg.ok(setPage(setParams(vo), pageNo, pageSize));
	}
}
