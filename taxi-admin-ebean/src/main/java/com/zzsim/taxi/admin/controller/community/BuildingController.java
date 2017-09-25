package com.zzsim.taxi.admin.controller.community;

import com.zzsim.taxi.admin.base.annotations.RestFullController;
import com.zzsim.taxi.admin.base.control.BaseController;
import com.zzsim.taxi.core.common.entitys.community.Building;
import com.zzsim.taxi.core.common.entitys.community.Community;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

@Slf4j
@Api(value = "楼栋", description = "楼栋管理", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestFullController("building")
public class BuildingController extends BaseController<Building> {

	@Override
	protected void insertBefore(Building obj) {
		super.insertBefore(obj);
	}
}
