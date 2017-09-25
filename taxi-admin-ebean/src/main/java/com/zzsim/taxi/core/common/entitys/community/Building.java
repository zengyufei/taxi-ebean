package com.zzsim.taxi.core.common.entitys.community;

import com.zzsim.taxi.admin.validate.annotation.Matche;
import com.zzsim.taxi.admin.validate.annotation.Matches;
import com.zzsim.taxi.admin.validate.groups.Insert;
import com.zzsim.taxi.admin.validate.groups.Update;
import com.zzsim.taxi.core.common.base.entiry.BaseEntity;
import io.ebean.annotation.DbComment;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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
	@DbComment("小区")
	Long communityId;
}
