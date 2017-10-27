package com.ys.common.entitys.community;

import com.ys.admin.validate.annotation.Matche;
import com.ys.admin.validate.annotation.Matches;
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

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "t_community")
@Accessors(chain = true)
@Data
@DbComment("小区")
public class Community extends BaseEntity {

	@DbComment("小区名称")
	String name;
	@DbComment("小区楼栋数")
	Integer buildMax;
	@DbComment("每栋单元数")
	Integer unitMax;
	@DbComment("每单元楼层最大值")
	Integer floorMax;
	@DbComment("每楼层房间数最大值")
	Integer roomMax;

	@Data
	@Matches({
			@Matche(field = "name", notBlank = true, message = "小区名称不能为空", groups = {Insert.class, Update.class}),
			@Matche(field = "buildMax", notNull = true, min = 1, message = "楼栋数不能为空", groups = {Insert.class, Update.class}),
			@Matche(field = "unitMax", notNull = true, min = 1, message = "单元数不能为空", groups = {Insert.class, Update.class}),
			@Matche(field = "floorMax", notNull = true, min = 1, message = "楼层最大值不能为空", groups = {Insert.class, Update.class}),
			@Matche(field = "roomMax", notNull = true, min = 1, message = "每层房间数最大值不能为空", groups = {Insert.class, Update.class}),
	})
	public static class Vo extends AbstractVoEntity {
		String name;
		Integer buildMax;
		Integer unitMax;
		Integer floorMax;
		Integer roomMax;
	}

}
