package com.ys.common.entitys.rbac;

import com.ys.admin.validate.annotation.Matche;
import com.ys.admin.validate.annotation.Matches;
import com.ys.common.base.entiry.DateTimeAbstractEntity;
import com.zyf.valid.DeleteById;
import com.zyf.valid.Insert;
import com.zyf.valid.QueryById;
import com.zyf.valid.Update;
import io.ebean.annotation.DbComment;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;

// @Cache(enableQueryCache = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "t_sys_resource")
@Matches({
		@Matche(field = "name", notBlank = true, message = "资源权限名称不能为空", groups = {Insert.class, Update.class}),
		@Matche(field = "level", notNull = true, min = 1, max = 4, message = "权限等级不能为空", groups = {Insert.class, Update.class}),
		@Matche(field = "permission", notBlank = true, message = "资源权限标识不能为空", groups = {Insert.class, Update.class}),
})
@Accessors(chain=true)
@Data
@DbComment("资源权限")
public class SysResource extends DateTimeAbstractEntity {

	@DbComment("权限名称")
	String name;
	@DbComment("菜单等级")
	Integer level;
	@DbComment("权限标识")
	String permission;

	@DbComment("父id")
	@Min(value = 1, message = "id 不能为空", groups = { QueryById.class, Update.class, DeleteById.class })
	Long parentId;
}
