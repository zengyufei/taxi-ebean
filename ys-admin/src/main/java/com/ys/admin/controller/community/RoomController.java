package com.ys.admin.controller.community;

import com.ys.admin.base.annotations.RestFullController;
import com.ys.admin.base.control.BaseController;
import com.ys.admin.base.control.UnitBaseController;
import com.ys.common.entitys.community.Room;
import com.zyf.result.Msg;
import io.ebean.Ebean;
import io.swagger.annotations.Api;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@Api(value = "房间", description = "房间管理", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestFullController("room")
@Update(Room.class)
@Insert(Room.class)
@RemoveById(Room.class)
@DeleteById(Room.class)
@QueryAll(Room.class)
@QueryById(Room.class)
public class RoomController extends UnitBaseController<Room> {


	@GetMapping("queryByUnitId")
	public Msg queryByUnitId(@Min(value = 1, message = "查询的 id 不能为空")
	                             @NotNull(message = "id 不能为空")
			                             Long id) {
		List<Room> buildings = Ebean.find(Room.class)
				.where()
				.eq("unitId", id)
				.findList();
		return Msg.ok(buildings);
	}

	@GetMapping("queryByBuildingId")
	public Msg queryByBuildingId(@Min(value = 1, message = "查询的 id 不能为空")
	                             @NotNull(message = "id 不能为空")
			                             Long id) {
		List<Room> buildings = Ebean.find(Room.class)
				.where()
				.eq("buildingId", id)
				.findList();
		return Msg.ok(buildings);
	}

	@GetMapping("queryByCommunityId")
	public Msg queryByCommunityId(@Min(value = 1, message = "查询的 id 不能为空")
	                              @NotNull(message = "id 不能为空")
			                              Long id) {
		List<Room> buildings = Ebean.find(Room.class)
				.where()
				.eq("communityId", id)
				.findList();
		return Msg.ok(buildings);
	}
}
