package com.ys.admin.controller;

import com.ys.common.entitys.other.Area;
import com.ys.admin.base.annotations.RestFullController;
import io.swagger.annotations.Api;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

@Slf4j
@Api(value = "省市查询接口", description = "省市查询", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestFullController("area")
@QueryAll(Area.class)
public class AreaController {

}
