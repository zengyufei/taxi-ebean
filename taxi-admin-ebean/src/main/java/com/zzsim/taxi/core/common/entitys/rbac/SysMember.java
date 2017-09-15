package com.zzsim.taxi.core.common.entitys.rbac;

import com.zzsim.taxi.admin.validate.annotation.Matche;
import com.zzsim.taxi.admin.validate.annotation.Matches;
import com.zzsim.taxi.admin.validate.groups.Insert;
import com.zzsim.taxi.admin.validate.groups.Update;
import com.zzsim.taxi.core.common.annotations.OptionField;
import com.zzsim.taxi.core.common.annotations.OptionFieldLike;
import com.zzsim.taxi.core.common.base.BaseEntity;
import com.zzsim.taxi.core.common.enums.Sex;
import io.ebean.annotation.Cache;
import io.ebean.annotation.DbComment;
import io.ebean.annotation.Formula;

import javax.persistence.Entity;
import javax.persistence.Table;

@Cache(enableQueryCache = true)
@Entity
@Table(name = "t_sys_member")
@Matches({
		@Matche(field = "account", notBlank = true, message = "账号不能为空", groups = { Insert.class, Update.class }),
		@Matche(field = "password", notBlank = true, message = "密码不能为空", groups = { Insert.class, Update.class }),
		@Matche(field = "realName", notBlank = true, message = "真实姓名不能为空", groups = { Insert.class, Update.class }),
		@Matche(field = "identity", notBlank = true, message = "身份证不能为空", groups = { Insert.class, Update.class }),
		@Matche(field = "mobile", notBlank = true, message = "手机不能为空", groups = { Insert.class, Update.class }),
		@Matche(field = "sex", notNull = true, enumType = Sex.class, message = "性别错误", groups = { Insert.class, Update.class }),
		@Matche(field = "roleId", notNull = true, min = 1, message = "角色不能为空", groups = { Insert.class, Update.class }),
		@Matche(field = "orgNo", notBlank = true, message = "组织机构编号不能为空", groups = { Insert.class, Update.class }),
})
public class SysMember extends BaseEntity {

	@OptionFieldLike
	@DbComment("账号")
	private String account;
	@DbComment("密码")
	private String password;
	@DbComment("真实姓名")
	@OptionField
	private String realName;
	@DbComment("身份证")
	@OptionField
	private String identity;
	@DbComment("手机号")
	@OptionField
	private String mobile;
	@DbComment("邮箱")
	private String email;
	@DbComment("QQ")
	private String qq;
	@DbComment("备注")
	private String remark;
	@OptionField
	@DbComment("性别")
	private Sex sex;
	@OptionField
	@DbComment("角色 id")
	private Long roleId;
	@OptionField
	@DbComment("组织机构编号")
	private String orgNo;

	/*
		1. ${ta} 默认代表this。
		2. coalesce相当于或判断，如果不用，会null，给它 '' 默认值。
		3. 必须用left join 否则查不到数据，整个this 都为空。
	*/
	// @Formula(select = "(select t1.role_name from t_sys_role t1 where t1.id = ${ta}.role_id)") // 这种写法为空只能null
	@Formula(select = "coalesce(t1.role_name, '')", join = "left join t_sys_role t1 on t1.id = ${ta}.role_id")
	private String roleName;

	/*
		1. ${ta} 默认代表this。
		2. coalesce相当于或判断，如果不用，会null，给它 '' 默认值。
		3. 必须用left join 否则查不到数据，整个this 都为空。
	*/
	@Formula(select = "coalesce(t.org_name, '')", join = "left join t_sys_org t on t.org_no = ${ta}.org_no")
	private String orgName;

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getIdentity() {
		return identity;
	}

	public void setIdentity(String identity) {
		this.identity = identity;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getQq() {
		return qq;
	}

	public void setQq(String qq) {
		this.qq = qq;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getRoleId() {
		return roleId;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public Sex getSex() {
		return sex;
	}

	public void setSex(Sex sex) {
		this.sex = sex;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
}
