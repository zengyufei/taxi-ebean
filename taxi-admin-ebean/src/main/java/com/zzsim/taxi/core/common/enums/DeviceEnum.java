package com.zzsim.taxi.core.common.enums;

import com.zzsim.taxi.core.common.base.MarkId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 设备
 */
@AllArgsConstructor
public enum DeviceEnum implements MarkId {

    DoorMachine(1, "门口机"),
    CenterMachine(2, "中心机"),
    WallMachine(3, "围墙机"),
    IndoorMachine(4, "室内机");

    @Setter
    @Getter
    private int index;
    @Setter
    @Getter
    private String mark;

}