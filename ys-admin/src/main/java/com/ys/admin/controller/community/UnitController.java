package com.ys.admin.controller.community;

import com.ys.admin.base.control.BaseController;
import com.ys.admin.base.annotations.RestFullController;
import com.ys.admin.base.control.BuildingBaseController;
import com.zyf.result.Msg;
import com.ys.common.entitys.community.Building;
import com.ys.common.entitys.community.Unit;
import io.ebean.Ebean;
import io.swagger.annotations.Api;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@Api(value = "单元", description = "单元管理", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestFullController("unit")
@Update(Unit.class)
@Insert(Unit.class)
@RemoveById(Unit.class)
@DeleteById(Unit.class)
@QueryAll(Unit.class)
@QueryById(Unit.class)
public class UnitController extends BuildingBaseController<Unit> {



}
