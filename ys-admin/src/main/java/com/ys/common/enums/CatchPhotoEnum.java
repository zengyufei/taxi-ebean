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

    CardOpen(1, "刷卡开门"),
    PhoneOpen(2, "手机开门"),
    PasswordOpen(3, "密码开门"),
    CallOpen(4, "呼叫留影"),
    MoveOpen(5, "移动侦测"),
    ButtonOpen(6, "按钮开门"),
    FaceOpen(7, "人脸开门");

    @Setter
    @Getter
    private int index;
    @Setter
    @Getter
    private String mark;

}