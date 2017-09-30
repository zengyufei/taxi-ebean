package com.ys.common.entitys.device;

import com.ys.common.base.entiry.AbstractEntity;
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
public class CatchPhoto extends AbstractEntity {

	@DbComment("所属小区编号")
	Long communityId;

	@DbComment("留影类型")
	CatchPhotoEnum catchPhotoEnum;
	@DbComment("主叫设备")
	Long deviceId;
	@DbComment("房间/卡号")
	Long cardId;

	@Formula(select = "coalesce(t1.name, '')", join = "left join t_community t1 on t1.id = ${ta}.community_id") // 注解用法参照 sysMember
	String communityName;
	@Formula(select = "coalesce(t2.name, '')", join = "left join t_device t2 on t2.id = ${ta}.device_id") // 注解用法参照 sysMember
	String deviceName;
	@Formula(select = "coalesce(t3.card_no, '')", join = "left join t_card t3 on t3.id = ${ta}.card_id") // 注解用法参照 sysMember
	String cardNo;

}
