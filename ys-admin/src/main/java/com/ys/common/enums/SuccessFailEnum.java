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

    Success(1, "成功"),
    Fail(2, "失败");

    @Setter
    @Getter
    private int index;
    @Setter
    @Getter
    private String mark;

}