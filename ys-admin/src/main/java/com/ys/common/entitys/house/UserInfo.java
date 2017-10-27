package com.ys.common.entitys.house;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.ys.admin.validate.annotation.Matche;
import com.ys.admin.validate.annotation.Matches;
import com.ys.common.base.entiry.BaseEntity;
import com.ys.common.base.entiry.BaseVoEntity;
import com.ys.common.enums.AuthEnum;
import com.ys.common.enums.StayEnum;
import com.zyf.valid.Insert;
import com.zyf.valid.Update;
import io.ebean.annotation.DbArray;
import io.ebean.annotation.DbComment;
import io.ebean.annotation.Formula;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "t_user_info")
@Accessors(chain = true)
@Data
@DbComment("住户信息")
public class UserInfo extends BaseEntity {

	@DbComment("所属单房间号")
	Long roomId;

	@DbComment("认证")
	Boolean authFlag;
	@DbComment("绑定电话呼叫")
	Boolean bindCallFlag;
	@DbComment("详情")
	String mark;
	@DbComment("开门权限")
	@DbArray
	List<Long> permission = Lists.newArrayList();

	@Excel(name = "常住", replace = {"住户_Resident", "临时_Temporary"},
			height = 10, width = 10, isImportField = "true")
	@DbComment("常住")
	StayEnum stayEnum;
	@Excel(name = "状态", replace = {"待审核_Pending", "未通过_NotPass", "已通过_Pass"},
			height = 10, width = 10, isImportField = "true")
	@DbComment("状态")
	AuthEnum authEnum;

	@Excel(name = "申请时间", replace = {"\\s_T"},
			exportFormat = "yyyyMMdd HH:mm:ss",
			importFormat = "yyyyMMdd HH:mm:ss",
			databaseFormat = "yyyyMMdd HH:mm:ss",
			format = "yyyy-MM-dd HH:mm:ss", width = 10)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@DbComment("申请时间")
	LocalDateTime applicationTime;

	@Excel(name = "所属房间", height = 20, width = 20)
	@Formula(select = "coalesce(t4.name, '')", join = "left join t_room t4 on t4.id = ${ta}.room_id") // 注解用法参照 sysMember
	String roomName;

	@ExcelEntity(id = "userInfo")
	@OneToOne(optional = false, cascade = CascadeType.REFRESH)
	@PrimaryKeyJoinColumn(columnDefinition = "user_id")
	User user;

	@Data
	@Matches({
			@Matche(field = "communityId", notNull = true, min = 1, message = "所属小区不能为空", groups = {Insert.class, Update.class}),
			@Matche(field = "userName", notBlank = true, message = "用户名不能为空", groups = {Insert.class, Update.class}),
			@Matche(field = "realName", notBlank = true, message = "姓名不能为空", groups = {Insert.class, Update.class}),
			@Matche(field = "phone", notBlank = true, message = "电话不能为空", groups = {Insert.class, Update.class}),
			@Matche(field = "idCard", notBlank = true, message = "身份证不能为空", groups = {Insert.class, Update.class}),
			@Matche(field = "roomNo", notBlank = true, message = "房间号不能为空", groups = {Insert.class, Update.class}),
			@Matche(field = "authEnum", notNull = true, enumType = AuthEnum.class, message = "状态不能为空", groups = {Insert.class, Update.class}),
	})
	public static class Vo extends BaseVoEntity {
		Long userId;
		Long roomId;

		Boolean authFlag;
		Boolean bindCallFlag;
		String mark;
		List<Long> permission = Lists.newArrayList();

		StayEnum stayEnum;
		AuthEnum authEnum;
		@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
		@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime applicationTime;

	}

}
