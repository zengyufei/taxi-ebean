package com.ys.common.enums;

import com.ys.common.base.MarkId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 审核
 * @author zengyufei
 */
@AllArgsConstructor
public enum AuthEnum implements MarkId {

    /** 审核通过 */
    Pending(0, "待审核"),
    NotPass(1, "未通过"),
    Pass(2, "已通过");

    @Setter
    @Getter
    private int index;
    @Setter
    @Getter
    private String mark;

}