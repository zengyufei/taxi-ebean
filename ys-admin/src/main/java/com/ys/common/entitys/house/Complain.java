package com.ys.common.entitys.house;

import com.ys.admin.validate.annotation.Matche;
import com.ys.admin.validate.annotation.Matches;
import com.ys.common.base.entiry.BaseEntity;
import com.ys.common.base.entiry.BaseVoEntity;
import com.ys.common.enums.ComplainEnum;
import com.zyf.valid.Insert;
import com.zyf.valid.Update;
import io.ebean.annotation.DbComment;
import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.*;

@Entity
@Table(name = "t_complain")
@Accessors(chain = true)
@Data
@DbComment("投诉")
public class Complain extends BaseEntity {

	@DbComment("住户")
	Long userInfoId;

	@DbComment("内容")
	String content;
	@DbComment("投诉类型")
	ComplainEnum complainEnum;
	@DbComment("业主反馈")
	String userMark;

	@ManyToOne(optional = false, cascade = CascadeType.REFRESH)
	@PrimaryKeyJoinColumn
	UserInfo userInfo;

	@Data
	@Matches({
			@Matche(field = "userInfoId", notNull = true, min = 1, message = "住户不能为空", groups = {Insert.class, Update.class}),
			@Matche(field = "content", notBlank = true, message = "内容不能为空", groups = {Insert.class, Update.class}),
			@Matche(field = "complainEnum", notNull = true, enumType = ComplainEnum.class, message = "投诉类型不能为空", groups = {Insert.class, Update.class}),
	})
	public static class Vo extends BaseVoEntity {
		String content;
		ComplainEnum complainEnum;
		String userMark;
		Long userInfoId;
	}

}
