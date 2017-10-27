package com.ys.common.enums;

import com.ys.common.base.MarkId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 成功失败枚举
 */
@AllArgsConstructor
public enum SuccessFailEnum implements MarkId {

    Fail(0, "失败"),
    Success(1, "成功");

    @Setter
    @Getter
    private int index;
    @Setter
    @Getter
    private String mark;

}