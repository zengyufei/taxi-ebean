package com.ys.admin.controller.device;

import com.ys.admin.base.annotations.RestFullController;
import com.ys.admin.base.control.CommunityBaseController;
import com.ys.common.entitys.device.Card;
import io.swagger.annotations.Api;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;

@Slf4j
@Api(value = "物理卡", description = "物理卡管理", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestFullController("card")
@Update(Card.class)
@Insert(Card.class)
@RemoveById(Card.class)
@DeleteById(Card.class)
@QueryAll(Card.class)
@QueryById(Card.class)
public class CardController extends CommunityBaseController<Card> {

}
