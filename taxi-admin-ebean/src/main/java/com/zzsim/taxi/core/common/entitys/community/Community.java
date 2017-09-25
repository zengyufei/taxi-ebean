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
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "t_community")
@Matches({
		@Matche(field = "name", notBlank = true, message = "小区名称不能为空", groups = {Insert.class, Update.class}),
		@Matche(field = "buildMax", notNull = true, min = 1, message = "楼栋数不能为空", groups = {Insert.class, Update.class}),
		@Matche(field = "unitMax", notNull = true, min = 1, message = "单元数不能为空", groups = {Insert.class, Update.class}),
		@Matche(field = "floorMax", notNull = true, min = 1, message = "楼层最大值不能为空", groups = {Insert.class, Update.class}),
		@Matche(field = "roomMax", notNull = true, min = 1, message = "每层房间数最大值不能为空", groups = {Insert.class, Update.class}),
})
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

}
