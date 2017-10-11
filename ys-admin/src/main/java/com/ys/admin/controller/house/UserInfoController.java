package com.ys.admin.controller.house;

import com.ys.admin.base.annotations.RestFullController;
import com.ys.admin.base.control.BaseController;
import com.ys.common.entitys.house.UserInfo;
import io.swagger.annotations.Api;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

@Slf4j
@Api(value = "住户", description = "小区住户管理", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestFullController("userInfo")
@Update(UserInfo.class)
@Insert(UserInfo.class)
@RemoveById(UserInfo.class)
@DeleteById(UserInfo.class)
@QueryById(UserInfo.class)
public class UserInfoController extends BaseController<UserInfo> {

}
