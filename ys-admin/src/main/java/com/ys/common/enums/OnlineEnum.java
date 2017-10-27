package com.ys.common.enums;

import com.ys.common.base.MarkId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 在线状态
 */
@AllArgsConstructor
public enum OnlineEnum implements MarkId {

    Offline(0, "离线"),
    Online(1, "在线");

    @Setter
    @Getter
    private int index;
    @Setter
    @Getter
    private String mark;

}