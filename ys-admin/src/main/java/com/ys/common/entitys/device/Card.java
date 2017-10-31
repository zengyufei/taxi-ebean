package com.ys.common.entitys.device;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.ys.admin.validate.annotation.Matche;
import com.ys.admin.validate.annotation.Matches;
import com.ys.common.base.entiry.AbstractDeletedEntity;
import com.ys.common.base.entiry.AbstractEntity;
import com.ys.common.base.entiry.AbstractVoEntity;
import com.ys.common.entitys.house.User;
import com.ys.common.entitys.house.UserInfo;
import com.ys.common.enums.CardEnum;
import com.zyf.valid.Insert;
import com.zyf.valid.Update;
import io.ebean.annotation.DbArray;
import io.ebean.annotation.DbComment;
import io.ebean.annotation.Formula;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "t_card")
@Accessors(chain = true)
@Data
@DbComment("物理卡")
public class Card  extends AbstractDeletedEntity {

	@DbComment("所属小区编号")
	Long communityId;

	@Excel(name = "卡号", height = 10, width = 10)
	@DbComment("卡号")
	String cardNo;
	@Excel(name = "卡类型", replace = {"住户卡_UserCard", "物管卡_ManageCard"}, height = 10, width = 10)
	@DbComment("卡类型")
	CardEnum cardEnum;

	@DbComment("持卡人，与 userInfoId 互斥")
	String realName;
	@DbComment("身份证")
	String idCard;
	@DbComment("电话")
	String phone;
	@DbComment("房间号")
	String roomNo;

	@JsonFormat(pattern="yyyy-MM-dd")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	@Temporal(TemporalType.TIMESTAMP)
	@DbComment("有效期")
	LocalDate validDate;
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@DbComment("有效时段")
	LocalDateTime validTime;

	@DbComment("开门权限")
	@DbArray
	List<Long> permissions = Lists.newArrayList();

	@DbComment("持卡人，与 realName 互斥")
	@OneToOne(optional = false, cascade = CascadeType.REFRESH)
	@PrimaryKeyJoinColumn(columnDefinition = "userId")
	User user;
	@DbComment("持卡人，与 realName 互斥")
	@OneToOne(optional = false, cascade = CascadeType.REFRESH)
	@PrimaryKeyJoinColumn(columnDefinition = "userInfoId")
	UserInfo userInfo;

	@Formula(select = "coalesce(t1.name, '')", join = "left join t_community t1 on t1.id = ${ta}.community_id") // 注解用法参照 sysMember
	String communityName;

	@Data
	@Matches({
			@Matche(field = "cardNo", notBlank = true, message = "卡号不能为空", groups = {Insert.class, Update.class}),
			@Matche(field = "cardEnum", notNull = true, enumType = CardEnum.class, message = "设备类型不能为空", groups = {Insert.class, Update.class}),
	})
	public static class Vo extends AbstractVoEntity {
		Long communityId;
		String cardNo;
		CardEnum cardEnum;
		Long userId;
		String realName;
		String idCard;
		String phone;
		String roomNo;

		@JsonFormat(pattern="yyyy-MM-dd")
		@DateTimeFormat(pattern = "yyyy-MM-dd")
		LocalDate validDate;
		@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
		@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime validTime;

		List<Long> permissions = Lists.newArrayList();
	}
}
