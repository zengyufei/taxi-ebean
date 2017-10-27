package com.ys.admin.base.poi;

import cn.afterturn.easypoi.handler.impl.ExcelDataHandlerDefaultImpl;
import com.ys.common.entitys.community.Building;
import com.ys.common.entitys.community.Community;
import com.ys.common.entitys.community.Unit;
import com.ys.common.enums.AuthEnum;
import com.ys.common.enums.StayEnum;
import com.ys.common.utils.EnumUtils;
import io.ebean.Ebean;

import java.util.Map;

/**
 * @author zengyufei
 */
public class UserInfoMapImportHanlder extends ExcelDataHandlerDefaultImpl<Map<String, Object>> {

	public String stayEnum = "stayEnum";
	public String authEnum = "authEnum";
	public String applicationTime = "applicationTime";
	public String communityId = "communityId";
	public String buildingId = "buildingId";
	public String unitId = "unitId";
	private String roomName = "roomName";
	public String activeFlag = "activeFlag";
	public String phone = "phone";
	public String realName = "realName";
	public String idCard = "idCard";
	private String name = "name";

	@Override
	public void setMapValue(Map<String, Object> map, String originKey, Object value) {
		String realKey = getRealKey(originKey);
		if (stayEnum.equals(realKey)) {
			map.put(realKey, EnumUtils.getEnum(StayEnum.class, value.toString()).getIndex());
		} else if (authEnum.equals(realKey)) {
			map.put(realKey, EnumUtils.getEnum(AuthEnum.class, value.toString()).getIndex());
		} else if (applicationTime.equals(realKey)) {
			map.put(realKey, value);
		}else {
			set(map, realKey, value);
		}
	}

	private String getRealKey(String originKey) {
		String stayEnumColumnName = "常住";
		String authEnumColumnName = "状态";
		String applicationTimeColumnName = "申请时间";
		String roomNameColumnName = "所属房间";
		String phoneColumnName = "电话";
		String activeFlagColumnName = "短信验证";
		String realNameColumnName = "真实姓名";
		String idCardColumnName = "身份证";
		String communityIdColumnName = "所属小区";
		String buildingIdColumnName = "所属楼栋";
		String unitIdColumnName = "所属单元";
		if (stayEnumColumnName.equals(originKey)) {
			return stayEnum;
		} else if (authEnumColumnName.equals(originKey)) {
			return authEnum;
		} else if (applicationTimeColumnName.equals(originKey)) {
			return applicationTime;
		} else if (roomNameColumnName.equals(originKey)) {
			return roomName;
		} else if (phoneColumnName.equals(originKey)) {
			return phone;
		} else if (activeFlagColumnName.equals(originKey)) {
			return activeFlag;
		} else if (realNameColumnName.equals(originKey)) {
			return realName;
		} else if (idCardColumnName.equals(originKey)) {
			return idCard;
		} else if (communityIdColumnName.equals(originKey)) {
			return communityId;
		} else if (buildingIdColumnName.equals(originKey)) {
			return buildingId;
		} else if (unitIdColumnName.equals(originKey)) {
			return unitId;
		} else {
			return originKey;
		}
	}

	private void set(Map<String, Object> map, String realKey, Object value) {
		if (communityId.equals(realKey)) {
			Community community = Ebean.find(Community.class)
					.where()
					.eq(name, value.toString())
					.findUnique();
			map.put(communityId, community.getId());

			boolean hasBuildingId = map.containsKey(buildingId);
			if(hasBuildingId) {
				Building building = Ebean.find(Building.class)
						.where()
						.eq(name, value.toString())
						.eq(communityId, map.get(communityId))
						.findUnique();
				map.remove(buildingId);
				map.put(buildingId, building.getId());

				boolean hasUnitId = map.containsKey(unitId);
				if(hasUnitId) {
					Unit unit = Ebean.find(Unit.class)
							.where()
							.eq(name, value.toString())
							.eq(communityId, map.get(communityId))
							.eq(buildingId, map.get(buildingId))
							.findUnique();
					map.remove(unitId);
					map.put(unitId, unit.getId());
				}
			}
		} else if (buildingId.equals(realKey)) {
			boolean hasCommunityId = map.containsKey(communityId);
			if(hasCommunityId) {
				Building building = Ebean.find(Building.class)
						.where()
						.eq(name, value.toString())
						.eq(communityId, map.get(communityId))
						.findUnique();
				map.put(buildingId, building.getId());

				boolean hasUnitId = map.containsKey(unitId);
				if(hasUnitId) {
					Unit unit = Ebean.find(Unit.class)
							.where()
							.eq(name, value.toString())
							.eq(communityId, map.get(communityId))
							.eq(buildingId, map.get(buildingId))
							.findUnique();
					map.remove(unitId);
					map.put(unitId, unit.getId());
				}
			}else{
				map.put(buildingId, 0);
			}
		} else if (unitId.equals(realKey)) {
			boolean hasCommunityId = map.containsKey(communityId);
			if(hasCommunityId) {
				boolean hasBuildingId = map.containsKey(buildingId);
				if(hasBuildingId) {
					Unit unit = Ebean.find(Unit.class)
							.where()
							.eq(name, value.toString())
							.eq(communityId, map.get(communityId))
							.eq(buildingId, map.get(buildingId))
							.findUnique();
					map.put(unitId, unit.getId());
				} else {
					map.put(unitId, 0);
				}
			} else {
				map.put(unitId, 0);
			}
		} else{
			map.put(realKey, value);
		}
	}
}