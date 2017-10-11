package com.ys.common.entitys.device;

import com.ys.admin.validate.annotation.Matche;
import com.ys.admin.validate.annotation.Matches;
import com.ys.common.annotations.OptionField;
import com.ys.common.annotations.OptionFieldIn;
import com.ys.common.base.entiry.BaseEntity;
import com.ys.common.enums.EquEnum;
import com.ys.common.enums.OnlineEnum;
import com.zyf.valid.Insert;
import com.zyf.valid.Update;
import io.ebean.annotation.DbComment;
import io.ebean.annotation.Formula;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Table;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "t_device")
@Matches({
		@Matche(field = "name", notBlank = true, message = "设备名称不能为空", groups = {Insert.class, Update.class}),
		@Matche(field = "equEnum", notNull = true, enumType = EquEnum.class, message = "设备类型不能为空", groups = {Insert.class, Update.class}),
		@Matche(field = "onlineEnum", notNull = true, enumType = OnlineEnum.class, message = "在线情况不能为空", groups = {Insert.class, Update.class}),
})
@Accessors(chain = true)
@Data
@DbComment("设备")
public class Device extends BaseEntity {

	@DbComment("设备号")
	String equNo;
	@DbComment("设备名称")
	String name;
	@DbComment("设备类型")
	EquEnum equEnum;
	@DbComment("Ip地址")
	String ip;
	@DbComment("Mac地址")
	String mac;
	@DbComment("密码")
	String password;
	@DbComment("在线情况")
	OnlineEnum onlineEnum;
	@DbComment("IOS开门延时")
	Long times;
	@DbComment("蓝牙开门灵敏度")
	Integer keenNumb;

	@OptionField
	@DbComment("所属小区编号")
	Long communityId;
	@DbComment("所属楼栋编号")
	Long buildingId;
	@DbComment("所属单元编号")
	Long unitId;
	@DbComment("所属房间编号，如果有")
	Long roomId;

	@Formula(select = "coalesce(t1.name, '')", join = "left join t_community t1 on t1.id = ${ta}.community_id") // 注解用法参照 sysMember
	String communityName;
	@Formula(select = "coalesce(t2.name, '')", join = "left join t_building t2 on t2.id = ${ta}.building_id") // 注解用法参照 sysMember
	String buildingName;
	@Formula(select = "coalesce(t3.name, '')", join = "left join t_unit t3 on t3.id = ${ta}.unit_id") // 注解用法参照 sysMember
	String unitName;
	@Formula(select = "coalesce(t4.name, '')", join = "left join t_room t4 on t4.id = ${ta}.room_id") // 注解用法参照 sysMember
	String roomName;


	@Accessors(chain = true)
	@Data
	public static class Option {
		@OptionFieldIn(fieldName = "buildingId")
		String buildingIds;
		@OptionFieldIn(fieldName = "unitId")
		String unitIds;
		@OptionFieldIn(fieldName = "roomId")
		String roomIds;
	}

}
