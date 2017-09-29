package com.ys.common.entitys.community;

import com.google.common.collect.Lists;
import com.ys.admin.validate.annotation.Matche;
import com.ys.admin.validate.annotation.Matches;
import com.ys.common.base.entiry.DateTimeAbstractEntity;
import com.zyf.valid.Insert;
import com.zyf.valid.Update;
import io.ebean.annotation.DbComment;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@Entity
@Table(name = "t_phone_permission")
@Matches({
		@Matche(field = "userId", notNull = true, min = 1, message = "设备名称不能为空", groups = {Insert.class, Update.class}),
})
@Accessors(chain = true)
@Data
@DbComment("手机开门权限")
public class PhonePermission extends DateTimeAbstractEntity {

	@DbComment("用户编号")
	Long userId;
	@DbComment("开门权限")
	List<Long> unitIds = Lists.newArrayList();
}
