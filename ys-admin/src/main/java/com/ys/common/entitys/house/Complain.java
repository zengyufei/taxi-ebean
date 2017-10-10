package com.ys.common.entitys.house;

import com.ys.admin.validate.annotation.Matche;
import com.ys.admin.validate.annotation.Matches;
import com.ys.common.base.entiry.BaseEntity;
import com.ys.common.enums.AuthEnum;
import com.ys.common.enums.ComplainEnum;
import com.zyf.valid.Insert;
import com.zyf.valid.Update;
import io.ebean.annotation.DbComment;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "t_complain")
@Matches({
		@Matche(field = "userId", notNull = true, min = 1, message = "所属小区不能为空", groups = {Insert.class, Update.class}),
		@Matche(field = "complainEnum", notNull = true, enumType = ComplainEnum.class, message = "投诉类型不能为空", groups = {Insert.class, Update.class}),

})
@Accessors(chain = true)
@Data
@DbComment("投诉")
public class Complain extends BaseEntity {

	@DbComment("业主")
	Long userId;
	@DbComment("内容")
	String content;
	@DbComment("投诉类型")
	ComplainEnum complainEnum;

}
