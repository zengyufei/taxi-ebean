package com.ys.common.entitys.community;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.google.common.collect.Lists;
import com.ys.admin.validate.annotation.Matche;
import com.ys.admin.validate.annotation.Matches;
import com.ys.common.annotations.VoFieldIn;
import com.ys.common.base.entiry.AbstractVoEntity;
import com.ys.common.base.entiry.BaseEntity;
import com.zyf.valid.Insert;
import com.zyf.valid.Update;
import io.ebean.annotation.DbComment;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "t_room")
@Accessors(chain = true)
@Data
@DbComment("房间")
public class Room extends BaseEntity {

	@Excel(name = "房间", height = 10, width = 10)
	@DbComment("房间名称，罗马数字")
	String name;
	@Excel(name = "房间别名", height = 10, width = 10)
	@DbComment("房间别名，中文名")
	String alias;

	@Data
	@Matches({
			@Matche(field = "name", notBlank = true, message = "小区名称不能为空", groups = {Insert.class, Update.class}),
			@Matche(field = "alias", notBlank = true, message = "小区名称不能为空", groups = {Insert.class, Update.class}),
			@Matche(field = "unitId", notNull = true, min = 1, message = "单元编号不能为空", groups = {Insert.class, Update.class}),
			@Matche(field = "buildingId", notNull = true, min = 1, message = "楼栋编号不能为空", groups = {Insert.class, Update.class}),
			@Matche(field = "communityId", notNull = true, min = 1, message = "所属小区编号不能为空", groups = {Insert.class, Update.class}),
	})
	public static class Vo extends AbstractVoEntity {
		String name;
		String alias;

		Long unitId;
		Long buildingId;
		Long communityId;

		@VoFieldIn(fieldName = "buildingId")
		List<Long> buildingIds = Lists.newArrayList();
		@VoFieldIn(fieldName = "unitId")
		List<Long> unitIds = Lists.newArrayList();
	}
}
