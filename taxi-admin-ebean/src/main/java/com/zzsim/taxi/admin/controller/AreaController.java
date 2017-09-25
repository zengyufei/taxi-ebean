package com.zzsim.taxi.admin.controller;

import com.zzsim.taxi.admin.base.control.Q.QidAndAll;
import com.zzsim.taxi.core.common.entitys.Area;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Api(value = "省市查询接口", description = "省市查询", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestController("area")
public class AreaController extends QidAndAll<Area> {

}
