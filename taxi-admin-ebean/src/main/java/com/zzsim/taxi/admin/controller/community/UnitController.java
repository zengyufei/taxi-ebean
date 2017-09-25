package com.zzsim.taxi.admin.controller.community;

import com.zzsim.taxi.admin.base.annotations.RestFullController;
import com.zzsim.taxi.admin.base.control.BaseController;
import com.zzsim.taxi.core.common.entitys.community.Community;
import com.zzsim.taxi.core.common.entitys.community.Unit;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

@Slf4j
@Api(value = "单元", description = "单元管理", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestFullController("unit")
public class UnitController extends BaseController<Unit> {

}
