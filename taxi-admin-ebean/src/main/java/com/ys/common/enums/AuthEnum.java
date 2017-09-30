package com.ys.common.enums;

import com.ys.common.base.MarkId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 设备
 */
@AllArgsConstructor
public enum AuthEnum implements MarkId {

    Pending(1, "待审核"),
    NotPass(2, "未通过"),
    Pass(3, "已通过");

    @Setter
    @Getter
    private int index;
    @Setter
    @Getter
    private String mark;

}