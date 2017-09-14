package com.zzsim.taxi.core.common.enums;

import com.zzsim.taxi.core.common.base.DescriptionID;

/**
 * 性别
 */
public enum Sex implements DescriptionID {

	Free(0, "不限"),
    Male(1, "男"),
    Female(2, "女");

    private int index;
    private String description;

    // 构造方法
    Sex(int index, String description) {
        this.index = index;
        this.description = description;
    }

    @Override
    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}