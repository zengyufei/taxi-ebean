package com.zzsim.taxi.admin.base;

import com.zzsim.taxi.core.common.entitys.rbac.SysMember;
import io.ebean.config.CurrentUserProvider;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Component;

@Component
public class CurrentUser implements CurrentUserProvider {

  @Override
  public Object currentUser() {
	  SysMember sysMember = (SysMember) SecurityUtils.getSubject().getPrincipal();
	  return sysMember.getAccount();
  }
}