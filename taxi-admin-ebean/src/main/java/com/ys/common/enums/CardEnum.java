package com.ys.common.enums;

import com.ys.common.base.MarkId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 刷卡的卡
 */
@AllArgsConstructor
public enum CardEnum implements MarkId {

    UserCard(1, "住户卡"),
    ManageCard(2, "物管卡");

    @Setter
    @Getter
    private int index;
    @Setter
    @Getter
    private String mark;

}