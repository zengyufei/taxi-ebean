package com.ys.admin.controller.house;

import com.ys.admin.base.annotations.RestFullController;
import com.ys.admin.base.control.BaseController;
import com.ys.common.entitys.house.Complain;
import com.ys.common.entitys.house.Repair;
import io.swagger.annotations.Api;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

@Slf4j
@Api(value = "报修", description = "报修管理", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestFullController("repair")
@Update(Repair.class)
@Insert(Repair.class)
@RemoveById(Repair.class)
@DeleteById(Repair.class)
@QueryById(Repair.class)
public class RepairController extends BaseController<Repair> {

}
