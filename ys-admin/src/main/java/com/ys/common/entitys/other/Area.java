/*
package com.ys.common.entitys.other;

import com.ys.common.base.entiry.IdAbstractEntity;
import io.ebean.annotation.Cache;
import io.ebean.annotation.DbComment;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "t_area")
@EqualsAndHashCode(callSuper = true)
@Entity
@Cache(enableQueryCache = true)
@Value
@Accessors(chain=true)
@DbComment("省市区域")
public class Area extends IdAbstractEntity {

	@DbComment("编号")
	Integer code;
	@DbComment("名称")
	String name;
	@DbComment("上级编号")
	Integer parentCode;
	@DbComment("上级名称")
	String parentName;
	@DbComment("全称")
	String fullName;

}
*/
