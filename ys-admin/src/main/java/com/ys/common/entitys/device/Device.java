package com.ys.common.entitys.device;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.ys.admin.validate.annotation.Matche;
import com.ys.admin.validate.annotation.Matches;
import com.ys.common.base.entiry.BaseEntity;
import com.ys.common.base.entiry.BaseVoEntity;
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

/**
 * @author zengyufei
 */
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "t_device")
@Accessors(chain = true)
@Data
@DbComment("设备")
public class Device extends BaseEntity {

	@Excel(name = "设备号", height = 10, width = 10)
	@DbComment("设备号")
	String equNo;
	@Excel(name = "设备名称", height = 10, width = 10)
	@DbComment("设备名称")
	String name;
	@Excel(name = "设备类型",
			replace = {"门口_DoorMachine", "中心_CenterMachine", "围墙_WallMachine", "室内_IndoorMachine"},
			suffix = "机",
			height = 10,
			width = 10)
	@DbComment("设备类型")
	EquEnum equEnum;
	@Excel(name = "Ip地址", height = 10, width = 10)
	@DbComment("Ip地址")
	String ip;
	@Excel(name = "Mac地址", height = 10, width = 10)
	@DbComment("Mac地址")
	String mac;
	@Excel(name = "密码", height = 10, width = 10)
	@DbComment("密码")
	String password;
	@DbComment("在线情况")
	OnlineEnum onlineEnum;
	@DbComment("IOS开门延时")
	Long times;
	@DbComment("蓝牙开门灵敏度")
	Integer keenNumb;

	@DbComment("所属房间编号")
	Long roomId;
	@Formula(select = "coalesce(c4.name, '')", join = "left join t_room c4 on c4.id = ${ta}.room_id") // 注解用法参照 sysMember
	String roomName;

	@Data
	@Matches({
			@Matche(field = "name", notBlank = true, message = "设备名称不能为空", groups = {Insert.class, Update.class}),
			@Matche(field = "equEnum", notNull = true, enumType = EquEnum.class, message = "设备类型不能为空", groups = {Insert.class, Update.class}),
	})
	public static class Vo extends BaseVoEntity {
		String equNo;
		String name;
		EquEnum equEnum;
		String ip;
		String mac;
		String password;
		OnlineEnum onlineEnum;
		Long times;
		Integer keenNumb;
	}


}
