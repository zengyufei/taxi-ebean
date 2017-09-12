package com.zzsim.taxi.core.common.base;

import io.ebean.config.CurrentUserProvider;
import org.springframework.stereotype.Component;

@Component
public class CurrentUser implements CurrentUserProvider {

  @Override
  public Object currentUser() {
	  return "test";
  }
}