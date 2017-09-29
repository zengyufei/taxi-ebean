package com.ys.admin.controller.community;

import com.google.common.collect.Lists;
import com.ys.admin.base.control.BaseController;
import com.ys.common.entitys.community.Unit;
import com.ys.admin.base.annotations.RestFullController;
import com.zyf.result.Msg;
import com.ys.common.entitys.community.Building;
import com.ys.common.entitys.community.Community;
import io.ebean.Ebean;
import io.swagger.annotations.Api;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Slf4j
@Api(value = "小区", description = "小区管理", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RestFullController("community")
@Update(Community.class)
@Insert(Community.class)
@QueryAll(Community.class)
@QueryById(Community.class)
public class CommunityController extends BaseController<Community> {

	@GetMapping("queryDefaultCommunityId")
	public Msg queryDefaultCommunityId() {
		Community first = Ebean.find(Community.class).where().setMaxRows(1).findUnique();
		return Msg.ok(first.getId());
	}

	void insertAfter(Community obj) {
		Long communityId = obj.getId();
		Integer buildMax = obj.getBuildMax();
		Integer unitMax = obj.getUnitMax();
		List<Building> batchInsertBuilding = Lists.newArrayList();
		List<Unit> batchInsertUnit = Lists.newArrayList();
		for (int i = 1; i <= buildMax; i++) {
			batchInsertBuilding.add(new Building()
					.setName(i + "栋")
					.setAlias(convert(i) + "栋")
					.setCommunityId(communityId));
		}
		Ebean.insertAll(batchInsertBuilding);
		for (int i = 0; i < buildMax; i++) {
			Building building = batchInsertBuilding.get(i);
			for (int j = 1; j <= unitMax; j++) {
				batchInsertUnit.add(new Unit()
						.setName(j + "单元")
						.setAlias(convert(j) + "单元")
						.setBuildingId(building.getId())
						.setCommunityId(communityId));
			}
		}
		Ebean.insertAll(batchInsertUnit);
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
		return sbf.toString().replaceAll("零[千百拾]", "零").replaceAll("零+万", "万").replaceAll("零+亿", "亿").replaceAll("亿万", "亿零").replaceAll("零+", "零").replaceAll("零元", "元").replaceAll("零[角分]", "").replaceAll("元", "");
	}
}
