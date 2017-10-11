package com.ys.common.entitys.house;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ys.admin.validate.annotation.Matche;
import com.ys.admin.validate.annotation.Matches;
import com.ys.common.base.entiry.BaseEntity;
import com.ys.common.enums.AuthEnum;
import com.zyf.valid.Insert;
import com.zyf.valid.Update;
import io.ebean.annotation.DbComment;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.*;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "t_user")
@Matches({
		@Matche(field = "communityId", notNull = true, min = 1, message = "所属小区不能为空", groups = {Insert.class, Update.class}),
		@Matche(field = "userName", notBlank = true, message = "用户名不能为空", groups = {Insert.class, Update.class}),
		@Matche(field = "realName", notBlank = true, message = "姓名不能为空", groups = {Insert.class, Update.class}),
		@Matche(field = "phone", notBlank = true, message = "电话不能为空", groups = {Insert.class, Update.class}),
		@Matche(field = "idCard", notBlank = true, message = "身份证不能为空", groups = {Insert.class, Update.class}),
		@Matche(field = "roomNo", notBlank = true, message = "房间号不能为空", groups = {Insert.class, Update.class}),
		@Matche(field = "authEnum", notNull = true, enumType = AuthEnum.class, message = "状态不能为空", groups = {Insert.class, Update.class}),
})
@Accessors(chain = true)
@Data
@DbComment("普通用户")
public class User extends BaseEntity {

	@DbComment("用户名")
	String userName;
	@DbComment("密码")
	String password;
	@DbComment("电话")
	String phone;
	@DbComment("手机 mac 地址")
	String phoneMac;
	@DbComment("是否通过短信验证")
	Boolean activeFlag;
	@DbComment("姓名")
	String realName;

	@DbComment("身份证")
	String idCard;
	@DbComment("身份证照片")
	String idCardImg;

	@JsonIgnore
	@JSONField(serialize=false)
	@DbComment("关联的住户信息")
	@OneToOne(optional = true, cascade = CascadeType.ALL, mappedBy="user")
	UserInfo userInfo;

}
