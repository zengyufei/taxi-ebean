package com.ys.admin.controller.other;

import com.ys.admin.base.annotations.RestFullController;
import com.ys.admin.base.control.BaseController;
import com.ys.common.entitys.other.Sms;
import io.swagger.annotations.Api;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

@Slf4j
@Api(value = "发送短信", description = "发送短信管理", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestFullController("sms")
@Update(Sms.class)
@Insert(Sms.class)
@RemoveById(Sms.class)
@DeleteById(Sms.class)
@QueryById(Sms.class)
public class SmsController extends BaseController<Sms> {

}
