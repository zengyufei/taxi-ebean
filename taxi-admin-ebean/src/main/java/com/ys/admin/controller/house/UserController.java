package com.ys.admin.controller.house;

import com.ys.admin.base.control.BaseController;
import com.ys.admin.base.annotations.RestFullController;
import com.ys.common.entitys.house.User;
import io.swagger.annotations.Api;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

@Slf4j
@Api(value = "住户", description = "小区住户管理", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestFullController("user")
@Update(User.class)
@Insert(User.class)
@RemoveById(User.class)
@DeleteById(User.class)
@QueryById(User.class)
public class UserController extends BaseController<User> {

}
