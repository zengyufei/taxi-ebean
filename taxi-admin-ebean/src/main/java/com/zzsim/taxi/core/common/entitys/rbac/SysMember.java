package com.zzsim.taxi.core.common.entitys.rbac;

import com.zzsim.taxi.admin.validate.annotation.Matche;
import com.zzsim.taxi.admin.validate.annotation.Matches;
import com.zzsim.taxi.admin.validate.groups.Insert;
import com.zzsim.taxi.admin.validate.groups.Update;
import com.zzsim.taxi.core.common.base.BaseEntity;
import com.zzsim.taxi.core.common.enums.Sex;

import javax.persistence.Entity;
import javax.persistence.Table;

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

	private String account;
	private String password;
	private String realName;
	private String identity;
	private String mobile;
	private String email;
	private String qq;
	private String remark;

	private Sex sex;

	private Long roleId;

	private String orgNo;

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
}
