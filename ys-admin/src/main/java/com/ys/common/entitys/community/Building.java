package com.ys.common.entitys.community;

import com.ys.admin.validate.annotation.Matche;
import com.ys.admin.validate.annotation.Matches;
import com.ys.common.annotations.OptionField;
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

@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@Data
@Entity
@Table(name = "t_building")
@DbComment("楼栋")
public class Building extends AbstractEntity {

	@DbComment("楼栋编号，罗马数字")
	String name;
	@DbComment("楼栋别名，中文名")
	String alias;
	@OptionField
	@DbComment("所属小区编号")
	Long communityId;

	@Formula(select = "coalesce(t1.name, '')", join = "left join t_community t1 on t1.id = ${ta}.community_id") // 注解用法参照 sysMember
	String communityName;

	@Data
	@Matches({
			@Matche(field = "alias", notBlank = true, message = "楼栋别名不能为空", groups = {Insert.class, Update.class}),
	})
	public static class Vo extends AbstractVoEntity {
		String name;
		String alias;
		Long communityId;
	}
}
