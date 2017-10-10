package com.ys.common.enums;

import com.ys.common.base.MarkId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public enum StayEnum implements MarkId {

    Resident(1, "门口机"),
    Temporary(2, "中心机");

    @Setter
    @Getter
    private int index;
    @Setter
    @Getter
    private String mark;

}