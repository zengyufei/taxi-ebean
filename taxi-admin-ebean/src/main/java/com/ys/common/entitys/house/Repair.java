package com.ys.common.entitys.house;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ys.admin.validate.annotation.Matche;
import com.ys.admin.validate.annotation.Matches;
import com.ys.common.base.entiry.BaseEntity;
import com.ys.common.enums.AuthEnum;
import com.ys.common.enums.RepairEnum;
import com.zyf.valid.Insert;
import com.zyf.valid.Update;
import io.ebean.annotation.DbComment;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.time.LocalDateTime;

@Entity
@Table(name = "t_repair")
@Matches({
		@Matche(field = "userId", notNull = true, min = 1, message = "报修人不能为空", groups = {Insert.class, Update.class}),
})
@Accessors(chain = true)
@Data
@DbComment("报修")
public class Repair extends BaseEntity {

	@DbComment("报修类型")
	RepairEnum repairEnum;
	@DbComment("如报修类型不够，则字符串辅助")
	String repairType;

	@DbComment("报修人")
	Long userId;

	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@Temporal(TemporalType.TIMESTAMP)
	@DbComment("业主约定处理时间")
	LocalDateTime promiseTime;
}
