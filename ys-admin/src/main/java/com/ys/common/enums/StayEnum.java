package com.ys.common.enums;

import com.ys.common.base.MarkId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public enum StayEnum implements MarkId {

    Resident(0, "住户"),
    Temporary(1, "临时");

    @Setter
    @Getter
    private int index;
    @Setter
    @Getter
    private String mark;

}