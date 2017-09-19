package com.zzsim.taxi.admin.controller.rbac;

import com.zzsim.taxi.admin.base.BaseController;
import com.zzsim.taxi.admin.base.annotations.RestFullController;
import com.zzsim.taxi.core.common.entitys.rbac.SysResource;
import io.swagger.annotations.Api;
import org.springframework.http.MediaType;

@Api(value = "资源权限", description = "资源权限管理", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestFullController("sysResource")
public class SysResourceController extends BaseController<SysResource> {

}
