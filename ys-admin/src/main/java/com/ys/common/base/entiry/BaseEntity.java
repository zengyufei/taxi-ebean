package com.ys.common.base.entiry;

import cn.afterturn.easypoi.excel.annotation.Excel;
import io.ebean.annotation.DbComment;
import io.ebean.annotation.Formula;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.persistence.MappedSuperclass;

@FieldDefaults(level = AccessLevel.PROTECTED)
@MappedSuperclass
@Accessors(chain = true)
@Data
public abstract class BaseEntity<T> extends AbstractDeletedEntity<T> {

	@DbComment("所属小区编号")
	Long communityId;
	@DbComment("所属楼栋编号")
	Long buildingId;
	@DbComment("所属单元编号")
	Long unitId;

	@Excel(name = "所属小区", height = 20, width = 20)
	@Formula(select = "coalesce(c1.name, '')", join = "left join t_community c1 on c1.id = ${ta}.community_id") // 注解用法参照 sysMember
	String communityName;
	@Excel(name = "所属楼栋", height = 20, width = 20)
	@Formula(select = "coalesce(c2.name, '')", join = "left join t_building c2 on c2.id = ${ta}.building_id") // 注解用法参照 sysMember
	String buildingName;
	@Excel(name = "所属单元", height = 20, width = 20)
	@Formula(select = "coalesce(c3.name, '')", join = "left join t_unit c3 on c3.id = ${ta}.unit_id") // 注解用法参照 sysMember
	String unitName;
}