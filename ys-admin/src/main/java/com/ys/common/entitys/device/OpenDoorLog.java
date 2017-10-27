package com.ys.common.entitys.device;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ys.common.base.entiry.BaseEntity;
import com.ys.common.base.entiry.BaseVoEntity;
import com.ys.common.enums.CardEnum;
import com.ys.common.enums.SuccessFailEnum;
import io.ebean.annotation.DbComment;
import io.ebean.annotation.Formula;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_open_door_log")
@Accessors(chain = true)
@Data
@DbComment("开门记录")
public class OpenDoorLog extends BaseEntity {

	@DbComment("门口机")
	Long deviceId;
	@DbComment("物理卡，卡号开门")
	Long cardId;
	@DbComment("用户，手机开门")
	Long userId;
	@DbComment("用户，手机开门")
	Long userInfoId;
	@Excel(name = "结果", replace = {"成功_Success", "失败_Fail"},
			height = 10, width = 10)
	@DbComment("结果")
	SuccessFailEnum successFailEnum;

	@Excel(name = "开门时间", databaseFormat = "yyyyMMdd HH:mm:ss", format = "yyyy-MM-dd HH:mm:ss", width = 10)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@DbComment("开门时间")
	LocalDateTime openDoorTime;

	@Excel(name = "设备名称", height = 10, width = 10)
	@Formula(select = "coalesce(t2.name, '')", join = "left join t_device t2 on t2.id = ${ta}.device_id") // 注解用法参照 sysMember
	String deviceName;
	@Excel(name = "用户", height = 10, width = 10)
	@Formula(select = "coalesce(t5.real_name, '')", join = "left join t_user t5 on t5.id = ${ta}.user_id") // 注解用法参照 sysMember
	String realName;
	@Excel(name = "卡类型", replace = {"住户卡_UserCard", "物管卡_ManageCard"}, height = 10, width = 10)
	@Formula(select = "coalesce(t6.card_enum, '')", join = "left join t_card t6 on t6.id = ${ta}.card_id") // 注解用法参照 sysMember
	CardEnum cardEnum;

	@Data
	public static class Vo extends BaseVoEntity {
		Long deviceId;
		Long cardId;
		Long userId;
		Long userInfoId;
		SuccessFailEnum successFailEnum;
		@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
		@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime openDoorTime;
	}


}
