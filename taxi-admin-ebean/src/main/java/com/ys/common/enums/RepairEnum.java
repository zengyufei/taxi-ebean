package com.ys.common.enums;

import com.ys.common.base.MarkId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 报修类型
 */
@AllArgsConstructor
public enum RepairEnum implements MarkId {

    Leaking(1, "漏水"),
    PowerOff(2, "断电"),
    LightBad(3, "灯坏"),
    ToLeak(4, "地漏堵"),
    BadWindowsAndDoors(5, "门窗坏"),
    LoadedLights(6, "装灯"),
    RepairToilet(7, "修马桶"),
    RepairWaterPipe(8, "修水管"),
    RepairWlectrical(9, "修电器"),
    Dredge(10, "疏通");

    @Setter
    @Getter
    private int index;
    @Setter
    @Getter
    private String mark;

}