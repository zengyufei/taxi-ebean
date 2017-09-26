package com.zzsim.taxi.core.common.enums;

import com.zzsim.taxi.core.common.base.MarkId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 性别
 */
@AllArgsConstructor
public enum SexEnum implements MarkId {

	Free(1, "不限"),
    Male(2, "男"),
    Female(3, "女");

    @Setter
    @Getter
    private int index;
    @Setter
    @Getter
    private String mark;

}