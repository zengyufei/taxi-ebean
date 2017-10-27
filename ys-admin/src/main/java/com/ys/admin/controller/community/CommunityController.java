package com.ys.admin.controller.community;

import com.google.common.collect.Lists;
import com.ys.admin.base.annotations.RestFullController;
import com.ys.admin.base.control.BaseController;
import com.ys.common.entitys.community.Building;
import com.ys.common.entitys.community.Community;
import com.ys.common.entitys.community.Room;
import com.ys.common.entitys.community.Unit;
import com.zyf.result.Msg;
import io.ebean.Ebean;
import io.swagger.annotations.*;
import lombok.Insert;
import lombok.QueryAll;
import lombok.QueryById;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Slf4j
@Api(value = "小区", description = "小区管理", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestFullController("community")
@Insert(value = Community.class, vo = Community.Vo.class)
@QueryAll(Community.class)
@QueryById(Community.class)
public class CommunityController extends BaseController<Community> {

	@ApiOperation(value = "条件查询分页，不包括删除", notes = "条件查询分页，不包括删除", httpMethod = "GET", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiResponses(value = {
			@ApiResponse(code = Msg.SUCCESS_CODE, message = "查询成功", response = Msg.class),
			@ApiResponse(code = Msg.ERROR_CODE, message = "系统错误", response = Msg.class)
	})
	@ApiImplicitParams({
			@ApiImplicitParam(value = "条件", name = "vo", paramType = "query"),
			@ApiImplicitParam(value = "当前页", name = "pageNo", defaultValue = "1", paramType = "query", dataType = "int"),
			@ApiImplicitParam(value = "每页页数", name = "pageSize", defaultValue = "1", paramType = "query", dataType = "int"),
	})
	@GetMapping("queryPage")
	public Msg queryPage(Community.Vo vo,
	                     @RequestParam(required = false, defaultValue = "1") int pageNo,
	                     @RequestParam(required = false, defaultValue = "10") int pageSize) {
		return Msg.ok(setPage(setParams(vo), pageNo, pageSize));
	}

	@GetMapping("queryDefaultCommunityId")
	public Msg queryDefaultCommunityId() {
		Community first = Ebean.find(Community.class).where().setMaxRows(1).findUnique();
		long id = -1;
		if(first != null){
			id = first.getId();
		}
		return Msg.status(200).body(id);
	}

	String insertAfter(Community community) {
		Long communityId = community.getId();
		Integer buildMax = community.getBuildMax();
		Integer unitMax = community.getUnitMax();
		Integer floorMax = community.getFloorMax();
		Integer roomMax = community.getRoomMax();
		List<Building> batchInsertBuilding = Lists.newArrayList();
		List<Unit> batchInsertUnit = Lists.newArrayList();
		List<Room> batchInsertRoom = Lists.newArrayList();
		// 批量新增楼栋
		for (int i = 1; i <= buildMax; i++) {
			batchInsertBuilding.add(new Building()
					.setName(i + " 栋")
					.setAlias(convert(i) + "栋")
					.setCommunityId(communityId));
		}
		Ebean.insertAll(batchInsertBuilding);

		// 批量新增单元
		for (int i = 0; i < buildMax; i++) {
			Building building = batchInsertBuilding.get(i);
			for (int j = 1; j <= unitMax; j++) {
				batchInsertUnit.add(new Unit()
						.setName(j + " 单元")
						.setAlias(convert(j) + "单元")
						.setBuildingId(building.getId())
						.setCommunityId(communityId));
			}
		}
		Ebean.insertAll(batchInsertUnit);

		// 批量新增房间
		for (int i = 0; i < batchInsertUnit.size(); i++) {
			Unit unit = batchInsertUnit.get(i);
			for (int j = 1; j <= floorMax; j++) {
				for (int k = 1; k <= roomMax; k++) {
					batchInsertRoom.add((Room) new Room()
							.setName(j + "0" + k + " 房")
							.setAlias(convert(Integer.parseInt(j + "0" + k)) + "房")
							.setBuildingId(unit.getBuildingId())
							.setUnitId(unit.getId())
							.setCommunityId(communityId));
				}
			}
		}
		Ebean.insertAll(batchInsertRoom);
		return "";
	}

	private static String convert(double inputMonney) {
		int decimalDigit = 2;//人名币保留2位小数到分
		//汉语中数字大写
		char[] data = {'零', '一', '二', '三', '四', '五', '六', '七', '八', '九'};
		//汉语中货币单位大写，这样的设计类似于占位符
		char[] units = {'分', '角', '元', '十', '百', '千', '万', '十', '百', '千', '亿', '十', '百', '千', '兆', '拾', '佰', '仟'};
		int uint = 0;
		//在这里我不使用系统函数，自己实现四舍五入，原理：如102.345,保留2位并四舍五入,102.3456->102.3456*10^(2+1)=102345.6->去掉小数部分102345->102345%10=5取到保留小数位数的下一位数字，判断舍入
		long money = (long) (inputMonney * Math.pow(10, decimalDigit + 1));
		if (money % 10 > 4) {
			money = (money / 10) + 1;
		} else {
			money = money / 10;
		}
		StringBuffer sbf = new StringBuffer();
		while (money != 0) {
			sbf.insert(0, units[uint++]);//插入人名币单位
			sbf.insert(0, data[(int) (money % 10)]);//插入单位所对应的值
			money = money / 10;
		}
		//使用replaceAll替换掉“零+'人民币单位'”,replaceAll里面的old字符串可以是正则表达式
		return sbf.toString().replaceAll("零[千百拾]", "零")
				.replaceAll("零+万", "万")
				.replaceAll("零+亿", "亿")
				.replaceAll("亿万", "亿零")
				.replaceAll("零+", "零")
				.replaceAll("零元", "元")
				.replaceAll("零[角分]", "")
				.replaceAll("元", "")
				.replaceAll("百零十", "零");
	}
}
