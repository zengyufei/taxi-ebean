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
@Table(name = "t_building")
@Matches({
		@Matche(field = "name", notBlank = true, message = "小区名称不能为空", groups = {Insert.class, Update.class}),
		@Matche(field = "alias", notBlank = true, message = "小区名称不能为空", groups = {Insert.class, Update.class}),
})
@Accessors(chain = true)
@Data
@DbComment("楼栋")
public class Building extends BaseEntity {

	@DbComment("楼栋名称，罗马数字")
	String name;
	@DbComment("楼栋别名，中文名")
	String alias;
	@OptionField
	@DbComment("所属小区编号")
	Long communityId;

	@Formula(select = "coalesce(t1.name, '')", join = "left join t_community t1 on t1.id = ${ta}.community_id") // 注解用法参照 sysMember
	String communityName;
}
