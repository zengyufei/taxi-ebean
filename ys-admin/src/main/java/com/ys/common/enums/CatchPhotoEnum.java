package com.ys.common.enums;

import com.ys.common.base.MarkId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 设备
 */
@AllArgsConstructor
public enum CatchPhotoEnum implements MarkId {

    CardOpen(0, "刷卡开门"),
    PhoneOpen(1, "手机开门"),
    PasswordOpen(2, "密码开门"),
    CallOpen(3, "呼叫留影"),
    MoveOpen(4, "移动侦测"),
    ButtonOpen(5, "按钮开门"),
    FaceOpen(6, "人脸开门");

    @Setter
    @Getter
    private int index;
    @Setter
    @Getter
    private String mark;

}