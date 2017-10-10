package com.ys.common.entitys.community;

import com.ys.admin.validate.annotation.Matche;
import com.ys.admin.validate.annotation.Matches;
import com.ys.common.annotations.OptionField;
import com.ys.common.base.entiry.BaseEntity;
import com.zyf.valid.Insert;
import com.zyf.valid.Update;
import io.ebean.annotation.DbComment;
import io.ebean.annotation.Formula;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "t_room")
@Matches({
		@Matche(field = "name", notBlank = true, message = "小区名称不能为空", groups = {Insert.class, Update.class}),
		@Matche(field = "alias", notBlank = true, message = "小区名称不能为空", groups = {Insert.class, Update.class}),
		@Matche(field = "unitId", notNull = true, min = 1, message = "单元编号不能为空", groups = {Insert.class, Update.class}),
		@Matche(field = "buildingId", notNull = true, min = 1, message = "楼栋编号不能为空", groups = {Insert.class, Update.class}),
		@Matche(field = "communityId", notNull = true, min = 1, message = "所属小区编号不能为空", groups = {Insert.class, Update.class}),
})
@Accessors(chain = true)
@Data
@DbComment("房间")
public class Room extends BaseEntity {

	@DbComment("房间名称，罗马数字")
	String name;
	@DbComment("房间别名，中文名")
	String alias;

	@OptionField
	@DbComment("所属单元编号")
	Long unitId;
	@OptionField
	@DbComment("所属楼栋编号")
	Long buildingId;
	@OptionField
	@DbComment("所属小区编号")
	Long communityId;

	@Formula(select = "coalesce(t1.name, '')", join = "left join t_community t1 on t1.id = ${ta}.community_id") // 注解用法参照 sysMember
	String communityName;
	@Formula(select = "coalesce(t2.name, '')", join = "left join t_building t2 on t2.id = ${ta}.building_id") // 注解用法参照 sysMember
	String buildingName;
	@Formula(select = "coalesce(t3.name, '')", join = "left join t_unit t3 on t3.id = ${ta}.unit_id") // 注解用法参照 sysMember
	String unitName;
}
