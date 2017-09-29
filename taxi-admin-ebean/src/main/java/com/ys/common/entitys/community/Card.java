package com.ys.common.entitys.community;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.common.collect.Lists;
import com.ys.admin.validate.annotation.Matche;
import com.ys.admin.validate.annotation.Matches;
import com.ys.common.base.entiry.BaseEntity;
import com.ys.common.enums.CardEnum;
import com.zyf.valid.Insert;
import com.zyf.valid.Update;
import io.ebean.annotation.DbComment;
import io.ebean.annotation.Formula;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "t_card_permission")
@Matches({
		@Matche(field = "cardNo", notBlank = true, message = "卡号不能为空", groups = {Insert.class, Update.class}),
		@Matche(field = "cardEnum", notNull = true, enumType = CardEnum.class, message = "设备类型不能为空", groups = {Insert.class, Update.class}),
})
@Accessors(chain = true)
@Data
@DbComment("刷卡开门的卡")
public class Card  extends BaseEntity {

	@DbComment("所属小区编号")
	Long communityId;

	@DbComment("卡号")
	String cardNo;
	@DbComment("卡类型")
	CardEnum cardEnum;

	@DbComment("持卡人，与 realName 互斥")
	Long userId;
	@DbComment("持卡人，与 userId 互斥")
	String realName;
	@DbComment("身份证，与 userId 互斥")
	String idCard;
	@DbComment("电话，与 userId 互斥")
	String phone;
	@DbComment("房间号，与 userId 互斥")
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
	List<Long> unitIds = Lists.newArrayList();

	@Formula(select = "coalesce(t1.name, '')", join = "left join t_community t1 on t1.id = ${ta}.community_id") // 注解用法参照 sysMember
	String communityName;
}
