package com.zzsim.taxi.core.common.entitys.rbac;

import com.zzsim.taxi.admin.validate.annotation.Matche;
import com.zzsim.taxi.admin.validate.annotation.Matches;
import com.zzsim.taxi.admin.validate.groups.Insert;
import com.zzsim.taxi.admin.validate.groups.Update;
import com.zzsim.taxi.core.common.annotations.OptionField;
import com.zzsim.taxi.core.common.annotations.OptionFieldLike;
import com.zzsim.taxi.core.common.base.entiry.BaseEntity;
import io.ebean.annotation.DbComment;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 支持 NamedQuery 和 Finder 查询类，用意是将 sql 相关查询放在实体中，不需要放在 dao 层
 */
// @Cache(enableQueryCache = true)
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "t_sys_org")
@Matches({
		@Matche(field = "orgName", notBlank = true, message = "组织机构名称不能为空", groups = {Insert.class, Update.class}),
		/*
		@Matche(field = "province", notNull = true, min = 1, message = "省份不能为空", groups = {Insert.class, Update.class}),
		@Matche(field = "city", notNull = true, min = 1, message = "城市不能为空", groups = {Insert.class, Update.class}),
		*/
		@Matche(field = "address", notNull = true, min = 6, max = 30, message = "地址应该在6-30字符之间", groups = {Insert.class, Update.class}),
})
@Accessors(chain=true)
@Data
@DbComment("组织机构")
public class SysOrg extends BaseEntity implements Serializable {

	static final long serialVersionUID = -2049682312143380490L;

	@DbComment("上级组织机构")
	@OptionField
	String parentOrgNo;
	@DbComment("组织机构")
	@OptionField
	String orgNo;
	@DbComment("组织机构名称")
	@OptionFieldLike
	String orgName;
	@DbComment("省份")
	@OptionField
	Integer province;
	@DbComment("城市")
	@OptionField
	Integer city;
	@DbComment("地址")
	String address;
	@DbComment("描述")
	String description;

}