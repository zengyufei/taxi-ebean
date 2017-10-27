package com.ys.admin.controller.community;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.ys.admin.base.annotations.RestFullController;
import com.ys.admin.base.control.AbstractUnitController;
import com.ys.admin.util.DownloadFileUtil;
import com.ys.common.entitys.community.Room;
import com.zyf.result.Msg;
import io.ebean.Ebean;
import io.swagger.annotations.Api;
import lombok.QueryById;
import lombok.QueryByIds;
import lombok.Update;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Slf4j
@Api(value = "房间", description = "房间管理", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestFullController("room")
@Update(value = Room.class, vo = Room.Vo.class)
@QueryById(Room.class)
@QueryByIds(Room.class)
public class RoomController extends AbstractUnitController<Room, Room.Vo> {

	@GetMapping("export")
	public void export(HttpServletResponse resp) throws Exception {
		List<Room> list = Ebean.find(entityClass).findList();
		Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("房间统计","房间统计"),
				entityClass, list);
		DownloadFileUtil.download("房间统计", workbook, resp);
	}

	@GetMapping("queryByUnitId")
	@Override
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
	@Override
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
	@Override
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
