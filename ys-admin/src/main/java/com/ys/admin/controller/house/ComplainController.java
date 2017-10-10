package com.ys.admin.controller.house;

import com.ys.admin.base.annotations.RestFullController;
import com.ys.admin.base.control.BaseController;
import com.ys.common.entitys.house.Complain;
import com.ys.common.entitys.house.User;
import io.swagger.annotations.Api;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

@Slf4j
@Api(value = "投诉", description = "投诉管理", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestFullController("complain")
@Update(Complain.class)
@Insert(Complain.class)
@RemoveById(Complain.class)
@DeleteById(Complain.class)
@QueryById(Complain.class)
public class ComplainController extends BaseController<Complain> {

}
