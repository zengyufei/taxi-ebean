package com.ys.admin.controller.community;

import com.ys.admin.base.control.BaseController;
import com.ys.admin.base.annotations.RestFullController;
import com.ys.common.entitys.device.Device;
import io.swagger.annotations.Api;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

@Slf4j
@Api(value = "设备", description = "在线设备管理", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestFullController("device")
@QueryAll(Device.class)
public class DeviceController extends BaseController<Device> {
}
