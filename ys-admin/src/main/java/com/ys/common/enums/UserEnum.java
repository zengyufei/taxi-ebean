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

    Tourists(1, "待审核"),
    Member(2, "未通过"),
    Owner(3, "已通过");

    @Setter
    @Getter
    private int index;
    @Setter
    @Getter
    private String mark;

}