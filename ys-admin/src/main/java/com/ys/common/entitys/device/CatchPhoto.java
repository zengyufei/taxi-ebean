package com.ys.common.entitys.device;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.ys.common.base.entiry.BaseEntity;
import com.ys.common.base.entiry.BaseVoEntity;
import com.ys.common.enums.CardEnum;
import com.ys.common.enums.CatchPhotoEnum;
import io.ebean.annotation.DbComment;
import io.ebean.annotation.Formula;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_catch_photo")
@Accessors(chain = true)
@Data
@DbComment("留影记录")
public class CatchPhoto extends BaseEntity {

	@Excel(name = "设备号", replace = {"刷卡开门_CardOpen", "手机开门_PhoneOpen", "密码开门_PasswordOpen", "呼叫留影_CallOpen",
			"移动侦测_MoveOpen", "按钮开门_ButtonOpen", "人脸开门_FaceOpen"},
			height = 10, width = 10)
	@DbComment("留影类型")
	CatchPhotoEnum catchPhotoEnum;
	@DbComment("主叫设备")
	Long deviceId;
	@DbComment("物理卡，卡号开门")
	Long cardId;
	@DbComment("用户，手机开门")
	Long userId;
	@DbComment("住户，手机开门")
	Long userInfoId;

	@Excel(name = "设备名称", height = 10, width = 10)
	@Formula(select = "coalesce(t2.name, '')", join = "left join t_device t2 on t2.id = ${ta}.device_id") // 注解用法参照 sysMember
	String deviceName;
	@Excel(name = "用户", height = 10, width = 10)
	@Formula(select = "coalesce(t5.real_name, '')", join = "left join t_user t5 on t5.id = ${ta}.user_id") // 注解用法参照 sysMember
	String realName;
	@Excel(name = "卡号", height = 10, width = 10)
	@Formula(select = "coalesce(t7.card_no, '')", join = "left join t_card t7 on t7.id = ${ta}.card_id") // 注解用法参照 sysMember
	String cardNo;
	@Excel(name = "卡类型", replace = {"住户卡_UserCard", "物管卡_ManageCard"}, height = 10, width = 10)
	@Formula(select = "coalesce(t6.card_enum, '')", join = "left join t_card t6 on t6.id = ${ta}.card_id") // 注解用法参照 sysMember
	CardEnum cardEnum;

	@Data
	public static class Vo extends BaseVoEntity {
		CatchPhotoEnum catchPhotoEnum;
		Long deviceId;
		Long cardId;
		Long userId;
		Long userInfoId;
	}
}
