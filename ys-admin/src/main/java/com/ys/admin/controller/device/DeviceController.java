package com.ys.admin.controller.device;

import com.ys.admin.base.annotations.RestFullController;
import com.ys.admin.base.control.RoomBaseController;
import com.ys.common.entitys.device.Device;
import com.zyf.result.Msg;
import io.swagger.annotations.*;
import lombok.QueryAll;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Slf4j
@Api(value = "设备", description = "在线设备管理", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestFullController("device")
@QueryAll(Device.class)
public class DeviceController extends RoomBaseController<Device> {

	@ApiOperation(value = "条件查询分页，不包括删除", notes = "条件查询分页，不包括删除", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = Msg.SUCCESS_CODE, message = "查询成功", response = Msg.class),
			@ApiResponse(code = Msg.ERROR_CODE, message = "系统错误", response = Msg.class)
	})
	@ApiImplicitParams({
			@ApiImplicitParam(value = "条件", name = "t", paramType = "query"),
			@ApiImplicitParam(value = "当前页", name = "pageNo", defaultValue = "1", paramType = "query", dataType = "int"),
			@ApiImplicitParam(value = "每页页数", name = "pageSize", defaultValue = "1", paramType = "query", dataType = "int"),
	})
	@GetMapping("queryPageByOption")
	public Msg queryPage(Device.Option option,
	                     @RequestParam(required = false, defaultValue = "1") int pageNo,
	                     @RequestParam(required = false, defaultValue = "10") int pageSize) {
		return Msg.ok(setPage(setParams(option), pageNo, pageSize));
	}

}
