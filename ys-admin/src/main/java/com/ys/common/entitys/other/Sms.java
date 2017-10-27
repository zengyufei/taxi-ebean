package com.ys.common.entitys.other;

import com.google.common.collect.Lists;
import com.ys.admin.validate.annotation.Matches;
import com.ys.common.base.entiry.BaseEntity;
import com.ys.common.base.entiry.BaseVoEntity;
import io.ebean.annotation.DbArray;
import io.ebean.annotation.DbComment;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "t_sms")
@Matches({
		//@Matche(field = "name", notBlank = true, message = "设备名称不能为空", groups = {Insert.class, Update.class}),
		//@Matche(field = "equEnum", notNull = true, enumType = EquEnum.class, message = "设备类型不能为空", groups = {Insert.class, Update.class}),
		//@Matche(field = "onlineEnum", notNull = true, enumType = OnlineEnum.class, message = "在线情况不能为空", groups = {Insert.class, Update.class}),
})
@Accessors(chain = true)
@Data
@DbComment("短信下发")
public class Sms extends BaseEntity {

	@DbComment("发送目标")
	@DbArray
	List<String> target = Lists.newArrayList();
	@DbComment("短信内容")
	String content;

	@DbComment("所属用户，如果有")
	Long userId;

	@Data
	public static class Vo extends BaseVoEntity {
		List<String> target = Lists.newArrayList();
		String content;
		Long userId;
	}

}
