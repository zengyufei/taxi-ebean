package com.ys.common.enums;

import com.ys.common.base.MarkId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 用户类型
 */
@AllArgsConstructor
public enum UserEnum implements MarkId {

    Tourists(0, "游客"),
    Member(1, "注册用户"),
    Owner(2, "业主");

    @Setter
    @Getter
    private int index;
    @Setter
    @Getter
    private String mark;

}