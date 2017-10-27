package com.ys.common.enums;

import com.ys.common.base.MarkId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 设备
 */
@AllArgsConstructor
public enum EquEnum implements MarkId {

    DoorMachine(0, "门口机"),
    CenterMachine(1, "中心机"),
    WallMachine(2, "围墙机"),
    IndoorMachine(3, "室内机");

    @Setter
    @Getter
    private int index;
    @Setter
    @Getter
    private String mark;

}