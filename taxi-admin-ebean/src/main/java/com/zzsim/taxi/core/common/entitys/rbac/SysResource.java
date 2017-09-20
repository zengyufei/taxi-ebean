package com.zzsim.taxi.core.common.entitys.rbac;

import com.zzsim.taxi.admin.validate.annotation.Matche;
import com.zzsim.taxi.admin.validate.annotation.Matches;
import com.zzsim.taxi.admin.validate.groups.Insert;
import com.zzsim.taxi.admin.validate.groups.Update;
import com.zzsim.taxi.core.common.base.BaseParentAndChildEntity;
import io.ebean.annotation.DbComment;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;

// @Cache(enableQueryCache = true)
@Entity
@Table(name = "t_sys_resource")
@Data
@Matches({
		@Matche(field = "name", notBlank = true, message = "资源权限名称不能为空", groups = {Insert.class, Update.class}),
		@Matche(field = "level", notNull = true, min = 1, max = 4, message = "权限等级不能为空", groups = {Insert.class, Update.class}),
		@Matche(field = "permission", notBlank = true, message = "资源权限标识不能为空", groups = {Insert.class, Update.class}),
})
public class SysResource extends BaseParentAndChildEntity {

	@DbComment("权限名称")
	private String name;
	@DbComment("菜单等级")
	private Integer level;
	@DbComment("权限标识")
	private String permission;

}
