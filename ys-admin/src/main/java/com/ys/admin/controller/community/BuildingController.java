package com.ys.admin.controller.community;

import com.ys.admin.base.annotations.RestFullController;
import com.ys.admin.base.control.AbstractCommunityController;
import com.ys.common.entitys.community.Building;
import io.swagger.annotations.Api;
import lombok.QueryById;
import lombok.QueryByIds;
import lombok.Update;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

@Slf4j
@Api(value = "楼栋", description = "楼栋管理", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestFullController("building")
@Update(value = Building.class, vo = Building.Vo.class)
@QueryById(Building.class)
@QueryByIds(Building.class)
public class BuildingController extends AbstractCommunityController<Building, Building.Vo> {

}
