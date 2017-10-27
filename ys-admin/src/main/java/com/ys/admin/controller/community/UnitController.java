package com.ys.admin.controller.community;

import com.ys.admin.base.annotations.RestFullController;
import com.ys.admin.base.control.AbstractBuildingController;
import com.ys.common.entitys.community.Unit;
import io.swagger.annotations.Api;
import lombok.QueryById;
import lombok.QueryByIds;
import lombok.Update;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

@Slf4j
@Api(value = "单元", description = "单元管理", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestFullController("unit")
@Update(value = Unit.class, vo = Unit.Vo.class)
@QueryById(Unit.class)
@QueryByIds(Unit.class)
public class UnitController extends AbstractBuildingController<Unit, Unit.Vo> {

}
