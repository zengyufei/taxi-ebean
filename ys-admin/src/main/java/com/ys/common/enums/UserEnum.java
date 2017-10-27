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

    Tourists(0, "待审核"),
    Member(1, "未通过"),
    Owner(2, "已通过");

    @Setter
    @Getter
    private int index;
    @Setter
    @Getter
    private String mark;

}