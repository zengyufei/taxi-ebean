package com.ys.common.enums;

import com.ys.common.base.MarkId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 性别
 */
@AllArgsConstructor
public enum SexEnum implements MarkId {

	Free(0, "不限"),
    Male(1, "男"),
    Female(2, "女");

    @Setter
    @Getter
    private int index;
    @Setter
    @Getter
    private String mark;

}