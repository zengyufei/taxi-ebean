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

    Leaking(0, "漏水"),
    PowerOff(1, "断电"),
    LightBad(2, "灯坏"),
    ToLeak(3, "地漏堵"),
    BadWindowsAndDoors(4, "门窗坏"),
    LoadedLights(5, "装灯"),
    RepairToilet(6, "修马桶"),
    RepairWaterPipe(7, "修水管"),
    RepairWlectrical(8, "修电器"),
    Dredge(9, "疏通");

    @Setter
    @Getter
    private int index;
    @Setter
    @Getter
    private String mark;

}