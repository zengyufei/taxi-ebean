package com.ys.common.entitys.rbac;

import com.ys.admin.validate.annotation.Matche;
import com.ys.admin.validate.annotation.Matches;
import com.ys.common.annotations.OptionField;
import com.ys.common.annotations.OptionFieldLike;
import com.ys.common.base.entiry.BaseEntity;
import com.ys.common.enums.SexEnum;
import com.zyf.valid.Insert;
import com.zyf.valid.Update;
import io.ebean.annotation.DbComment;
import io.ebean.annotation.Formula;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Table;

// @Cache(enableQueryCache = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "t_sys_member")
@Matches({
		@Matche(field = "account", notBlank = true, message = "账号不能为空", groups = { Insert.class, Update.class }),
		@Matche(field = "password", notBlank = true, message = "密码不能为空", groups = { Insert.class, Update.class }),
		@Matche(field = "realName", notBlank = true, message = "真实姓名不能为空", groups = { Insert.class, Update.class }),
		@Matche(field = "identity", notBlank = true, message = "身份证不能为空", groups = { Insert.class, Update.class }),
		@Matche(field = "mobile", notBlank = true, message = "手机不能为空", groups = { Insert.class, Update.class }),
		@Matche(field = "sex", notNull = true, enumType = SexEnum.class, message = "性别错误", groups = { Insert.class, Update.class }),
		@Matche(field = "roleId", notNull = true, min = 1, message = "角色不能为空", groups = { Insert.class, Update.class }),
		@Matche(field = "orgNo", notBlank = true, message = "组织机构编号不能为空", groups = { Insert.class, Update.class }),
})
@Accessors(chain=true)
@Data
@DbComment("后台用户")
public class SysMember extends BaseEntity {

	@OptionFieldLike
	@DbComment("账号")
	String account;
	@DbComment("密码")
	String password;
	@DbComment("真实姓名")
	@OptionField
	String realName;
	@DbComment("身份证")
	@OptionField
	String identity;
	@DbComment("手机号")
	@OptionField
	String mobile;
	@DbComment("邮箱")
	String email;
	@DbComment("QQ")
	String qq;
	@DbComment("备注")
	String remark;
	@OptionField
	@DbComment("性别")
	SexEnum sexEnum;
	@OptionField
	@DbComment("角色 id")
	Long roleId;
	@OptionField
	@DbComment("组织机构编号")
	String orgNo;

	/*
		1. ${ta} 默认代表this。
		2. coalesce相当于或判断，如果不用，会null，给它 '' 默认值。
		3. 必须用left join 否则查不到数据，整个this 都为空。
	*/
	// @Formula(select = "(select t1.role_name from t_sys_role t1 where t1.id = ${ta}.role_id)") // 这种写法为空只能null
	@Formula(select = "coalesce(t1.role_name, '')", join = "left join t_sys_role t1 on t1.id = ${ta}.role_id")
	String roleName;

	/*
		1. ${ta} 默认代表this。
		2. coalesce相当于或判断，如果不用，会null，给它 '' 默认值。
		3. 必须用left join 否则查不到数据，整个this 都为空。
	*/
	@Formula(select = "coalesce(t.org_name, '')", join = "left join t_sys_org t on t.org_no = ${ta}.org_no")
	String orgName;
}
