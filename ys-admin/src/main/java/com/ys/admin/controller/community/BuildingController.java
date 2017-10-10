package com.ys.admin.controller.community;

import com.ys.admin.base.control.BaseController;
import com.ys.admin.base.annotations.RestFullController;
import com.ys.admin.base.control.CommunityBaseController;
import com.zyf.result.Msg;
import com.ys.common.entitys.community.Building;
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
@Api(value = "楼栋", description = "楼栋管理", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestFullController("building")
@Update(Building.class)
@Insert(Building.class)
@RemoveById(Building.class)
@DeleteById(Building.class)
@QueryAll(Building.class)
@QueryById(Building.class)
public class BuildingController extends CommunityBaseController<Building> {


}
