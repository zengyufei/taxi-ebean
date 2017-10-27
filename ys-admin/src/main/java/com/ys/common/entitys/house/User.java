package com.ys.common.entitys.house;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.ys.admin.validate.annotation.Matche;
import com.ys.admin.validate.annotation.Matches;
import com.ys.common.base.entiry.AbstractEntity;
import com.ys.common.base.entiry.AbstractVoEntity;
import com.zyf.valid.Insert;
import com.zyf.valid.Update;
import io.ebean.annotation.DbComment;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author zengyufei
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "t_user")
@Accessors(chain = true)
@Data
@DbComment("普通用户")
public class User extends AbstractEntity {

	@DbComment("用户名")
	String userName;
	@DbComment("密码")
	String password;
	@Excel(name = "电话_userInfo", isImportField = "true")
	@DbComment("电话")
	String phone;
	@DbComment("手机 mac 地址")
	String phoneMac;
	@Excel(name = "短信验证_userInfo", replace = {"是_true", "否_false"})
	@DbComment("是否通过短信验证")
	Boolean activeFlag;
	@Excel(name = "真实姓名_userInfo", isImportField = "true")
	@DbComment("姓名")
	String realName;

	@Excel(name = "身份证_userInfo", isImportField = "true")
	@DbComment("身份证")
	String idCard;
	@DbComment("身份证照片")
	String idCardImg;

	@EqualsAndHashCode(callSuper = true)
	@Data
	@Matches({
			@Matche(field = "userName", notBlank = true, message = "用户名不能为空", groups = {Insert.class, Update.class}),
			@Matche(field = "password", notBlank = true, message = "密码不能为空", groups = {Insert.class, Update.class}),
			@Matche(field = "phone", notBlank = true, message = "电话不能为空", groups = {Insert.class, Update.class}),
			@Matche(field = "realName", notBlank = true, message = "姓名不能为空", groups = {Insert.class, Update.class}),
			@Matche(field = "idCard", notBlank = true, message = "身份证不能为空", groups = {Insert.class, Update.class}),
			@Matche(field = "idCardImg", notBlank = true, message = "身份证上传图片不能为空", groups = {Insert.class, Update.class}),
	})
	public static class Vo extends AbstractVoEntity {
		String userName;
		String password;
		String phone;
		String phoneMac;
		Boolean activeFlag;
		String realName;
		String idCard;
		String idCardImg;
	}

}
