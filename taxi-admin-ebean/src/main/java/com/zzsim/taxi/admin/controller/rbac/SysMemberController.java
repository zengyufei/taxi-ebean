package com.zzsim.taxi.admin.controller.rbac;

import com.zzsim.taxi.admin.base.BaseController;
import com.zzsim.taxi.admin.base.annotations.RestFullController;
import com.zzsim.taxi.core.common.entitys.rbac.SysMember;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

@Slf4j
@Api(value = "用户", description = "后台用户管理", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestFullController("sysMember")
public class SysMemberController extends BaseController<SysMember> {

}
