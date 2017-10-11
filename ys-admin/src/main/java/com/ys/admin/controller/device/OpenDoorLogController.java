package com.ys.admin.controller.device;

import com.ys.admin.base.annotations.RestFullController;
import com.ys.admin.base.control.CommunityBaseController;
import com.ys.common.entitys.device.OpenDoorLog;
import io.swagger.annotations.Api;
import lombok.QueryAll;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

@Slf4j
@Api(value = "开门记录", description = "开门记录管理", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestFullController("openDoorLog")
@QueryAll(OpenDoorLog.class)
public class OpenDoorLogController extends CommunityBaseController<OpenDoorLog> {

}
