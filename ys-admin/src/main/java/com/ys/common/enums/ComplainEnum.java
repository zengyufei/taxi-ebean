package com.ys.common.enums;

import com.ys.common.base.MarkId;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * 投诉类型
 */
@AllArgsConstructor
public enum ComplainEnum implements MarkId {

	Theme(0, "主题"),
	Reply(1, "回复");

	@Setter
	@Getter
	private int index;
	@Setter
	@Getter
	private String mark;

}