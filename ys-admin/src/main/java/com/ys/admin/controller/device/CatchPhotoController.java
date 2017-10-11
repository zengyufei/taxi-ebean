package com.ys.admin.controller.device;

import com.ys.admin.base.annotations.RestFullController;
import com.ys.admin.base.control.CommunityBaseController;
import com.ys.common.entitys.device.CatchPhoto;
import io.swagger.annotations.Api;
import lombok.QueryAll;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

@Slf4j
@Api(value = "留影记录", description = "留影记录管理", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestFullController("catchPhoto")
@QueryAll(CatchPhoto.class)
public class CatchPhotoController extends CommunityBaseController<CatchPhoto> {

}
