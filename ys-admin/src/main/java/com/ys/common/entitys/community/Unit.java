package com.ys.common.entitys.community;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.google.common.collect.Lists;
import com.ys.admin.validate.annotation.Matche;
import com.ys.admin.validate.annotation.Matches;
import com.ys.common.annotations.OptionField;
import com.ys.common.annotations.VoFieldIn;
import com.ys.common.base.entiry.AbstractEntity;
import com.ys.common.base.entiry.AbstractVoEntity;
import com.zyf.valid.Insert;
import com.zyf.valid.Update;
import io.ebean.annotation.DbComment;
import io.ebean.annotation.Formula;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "t_unit")
@Accessors(chain = true)
@Data
@DbComment("单元")
public class Unit extends AbstractEntity {

	@Excel(name = "房间", height = 10, width = 10)
	@DbComment("单元名称，罗马数字")
	String name;
	@Excel(name = "房间别名", height = 10, width = 10)
	@DbComment("单元别名，中文名")
	String alias;

	@OptionField
	@DbComment("所属楼栋编号")
	Long buildingId;
	@OptionField
	@DbComment("所属小区编号")
	Long communityId;

	@Excel(name = "所属小区", height = 20, width = 20)
	@Formula(select = "coalesce(t1.name, '')", join = "left join t_community t1 on t1.id = ${ta}.community_id") // 注解用法参照 sysMember
	String communityName;
	@Excel(name = "所属楼栋", height = 20, width = 20)
	@Formula(select = "coalesce(t2.name, '')", join = "left join t_building t2 on t2.id = ${ta}.building_id") // 注解用法参照 sysMember
	String buildingName;

	@Data
	@Matches({
			@Matche(field = "name", notBlank = true, message = "小区名称不能为空", groups = {Insert.class, Update.class}),
			@Matche(field = "alias", notBlank = true, message = "小区名称不能为空", groups = {Insert.class, Update.class}),
			@Matche(field = "buildingId", notNull = true, min = 1, message = "楼栋编号不能为空", groups = {Insert.class, Update.class}),
			@Matche(field = "communityId", notNull = true, min = 1, message = "所属小区编号不能为空", groups = {Insert.class, Update.class}),
	})
	public static class Vo extends AbstractVoEntity {
		String name;
		String alias;
		Long buildingId;
		Long communityId;

		@VoFieldIn(fieldName = "buildingId")
		List<Long> buildingIds = Lists.newArrayList();
	}

}
