package com.ys.common.entitys.rbac;

import com.google.common.collect.Lists;
import com.ys.admin.validate.annotation.Matche;
import com.ys.admin.validate.annotation.Matches;
import com.ys.common.annotations.OptionField;
import com.ys.common.annotations.VoFieldLike;
import com.ys.common.base.entiry.AbstractVoEntity;
import com.ys.common.base.entiry.BaseEntity;
import com.zyf.valid.DeleteById;
import com.zyf.valid.Insert;
import com.zyf.valid.QueryById;
import com.zyf.valid.Update;
import io.ebean.annotation.DbArray;
import io.ebean.annotation.DbComment;
import io.ebean.annotation.Formula;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import java.util.List;

// @Cache(enableQueryCache = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "t_sys_role")
@Accessors(chain=true)
@Data
@DbComment("角色")
public class SysRole extends BaseEntity {

	@VoFieldLike
	@DbComment("角色名称")
	String roleName;
	@DbComment("描述")
	String description;
	@OptionField
	@DbComment("组织机构编号")
	String orgNo;
	@DbComment("权限集合")
	@DbArray
	List<String> resourceList = Lists.newArrayList();

	@DbComment("父id")
	// @Min(value = 1, message = "id 不能为空", groups = { QueryById.class, Update.class, DeleteById.class })
	Long parentId;

	/*
		1. ${ta} 默认代表this。
		2. coalesce相当于或判断，如果不用，会null，给它 '' 默认值。
		3. 必须用left join 否则查不到数据，整个this 都为空。
	*/
	@Formula(select = "coalesce(t.org_name, '')", join = "left join t_sys_org t on t.org_no = ${ta}.org_no")
	String orgName;

	@Data
	@Matches({
			@Matche(field = "roleName", notBlank = true, message = "角色名称不能为空", groups = {Insert.class, Update.class}),
			@Matche(field = "orgNo", notBlank = true, message = "组织机构编号不能为空", groups = {Insert.class, Update.class}),
	})
	public static class Vo extends AbstractVoEntity {
		String roleName;
		String description;
		String orgNo;
		List<String> resourceList = Lists.newArrayList();
		Long parentId;
	}

}
