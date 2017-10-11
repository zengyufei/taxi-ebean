package com.ys.common.entitys.device;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ys.common.base.entiry.AbstractEntity;
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
public class OpenDoorLog extends AbstractEntity {

	@DbComment("所属小区编号")
	Long communityId;

	@DbComment("门口机")
	Long deviceId;
	@DbComment("类型")
	CardEnum cardEnum;
	@DbComment("用户")
	Long userId;
	@DbComment("结果")
	SuccessFailEnum successFailEnum;

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@DbComment("开门时间")
	LocalDateTime openDoorTime;

	@Formula(select = "coalesce(t1.name, '')", join = "left join t_community t1 on t1.id = ${ta}.community_id") // 注解用法参照 sysMember
	String communityName;
	@Formula(select = "coalesce(t2.name, '')", join = "left join t_device t2 on t2.id = ${ta}.device_id") // 注解用法参照 sysMember
	String deviceName;
	@Formula(select = "coalesce(t5.real_name, '')", join = "left join t_user t5 on t5.id = ${ta}.user_id") // 注解用法参照 sysMember
	String userName;
}
