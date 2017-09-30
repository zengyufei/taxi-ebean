package com.ys.common.options;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ys.admin.validate.annotation.Matche;
import com.ys.admin.validate.annotation.Matches;
import com.ys.common.base.entiry.BaseEntity;
import com.ys.common.enums.AuthEnum;
import com.ys.common.enums.StayEnum;
import com.zyf.valid.Insert;
import com.zyf.valid.Update;
import io.ebean.annotation.DbComment;
import io.ebean.annotation.Formula;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
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
public class UserOption extends BaseEntity {

	@DbComment("所属小区编号")
	Long communityId;
	@DbComment("所属楼栋编号")
	Long buildingId;
	@DbComment("所属单元编号")
	Long UnitId;

	@DbComment("用户名")
	String userName;
	@DbComment("密码")
	String password;
	@DbComment("电话")
	String phone;
	@DbComment("是否通过短信验证")
	Boolean activeFlag;
	@DbComment("姓名")
	String realName;
	@DbComment("身份证")
	String idCard;
	@DbComment("身份证照片")
	String idCardImg;
	@DbComment("已绑定房间号")
	String roomNo;
	@DbComment("认证")
	Boolean authFlag;
	@DbComment("绑定电话呼叫")
	Boolean bindCallFlag;
	@DbComment("详情")
	String mark;

	@DbComment("常住")
	StayEnum stayEnum;
	@DbComment("状态")
	AuthEnum authEnum;

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@DbComment("申请时间")
	LocalDateTime applicationTime;

	@Formula(select = "coalesce(t1.name, '')", join = "left join t_community t1 on t1.id = ${ta}.community_id") // 注解用法参照 sysMember
	String communityName;
	@Formula(select = "coalesce(t2.name, '')", join = "left join t_building t2 on t2.id = ${ta}.building_id") // 注解用法参照 sysMember
	String buildingName;
	@Formula(select = "coalesce(t3.name, '')", join = "left join t_unit t3 on t3.id = ${ta}.unit_id") // 注解用法参照 sysMember
	String unitName;

}
