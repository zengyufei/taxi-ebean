package com.ys.common.entitys.house;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ys.admin.validate.annotation.Matche;
import com.ys.admin.validate.annotation.Matches;
import com.ys.common.base.entiry.BaseEntity;
import com.ys.common.base.entiry.BaseVoEntity;
import com.ys.common.enums.RepairEnum;
import com.zyf.valid.Insert;
import com.zyf.valid.Update;
import io.ebean.annotation.DbComment;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_repair")
@Accessors(chain = true)
@Data
@DbComment("报修")
public class Repair extends BaseEntity {

	@DbComment("住户")
	Long userInfoId;

	@DbComment("报修类型")
	RepairEnum repairEnum;
	@DbComment("如报修类型不够，则字符串辅助")
	String repairType;
	@DbComment("报修备注")
	String mark;

	@ManyToOne(optional = false, cascade = CascadeType.REFRESH)
	@PrimaryKeyJoinColumn
	UserInfo userInfo;

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@DbComment("业主约定处理时间")
	LocalDateTime promiseTime;

	@Data
	@Matches({
			@Matche(field = "userId", notNull = true, min = 1, message = "报修人不能为空", groups = {Insert.class, Update.class}),
			@Matche(field = "promiseTime", notNull = true, message = "业主约定处理时间不能为空", groups = {Insert.class, Update.class}),
	})
	public static class Vo extends BaseVoEntity {
		RepairEnum repairEnum;
		String repairType;
		String mark;
		Long userInfoId;
		@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
		@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
		LocalDateTime promiseTime;
	}
}
