package com.ys.admin.controller.rbac;

import com.ys.admin.base.control.BaseController;
import com.ys.common.entitys.rbac.SysMember;
import com.ys.admin.base.annotations.RestFullController;
import io.swagger.annotations.Api;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

@Slf4j
@Api(value = "用户", description = "后台用户管理", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestFullController("sysMember")
@Update(SysMember.class)
@Insert(SysMember.class)
@RemoveById(SysMember.class)
@DeleteById(SysMember.class)
@QueryById(SysMember.class)
public class SysMemberController extends BaseController<SysMember> {

}
