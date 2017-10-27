package com.ys.admin.service.rbac;

import com.ys.common.entitys.rbac.SysOrg;
import io.ebean.Ebean;
import io.ebean.Query;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;

@Service("sysOrgService")
public class SysOrgService {

	@NotNull
	public String createOrgNo(String parentOrgNo) {
		Query<SysOrg> sysOrgQuery = Ebean.find(SysOrg.class)
				.setIncludeSoftDeletes()
				.setDisableLazyLoading(true);
		int count = sysOrgQuery.findCount();

		if (count != 0) {
			SysOrg sysOrgMaxOrgNo = sysOrgQuery
					.select("orgNo")
					.where()
					.eq("parentOrgNo", parentOrgNo)
					.orderBy("orgNo desc")
					.setMaxRows(1)
					.findUnique();
			if (sysOrgMaxOrgNo != null) {
				String maxOrgNo = sysOrgMaxOrgNo.getOrgNo();
				if (StringUtils.isNotBlank(maxOrgNo)) {
					int index = maxOrgNo.lastIndexOf("-");
					String prefixNumberStr = maxOrgNo.substring(0, index);
					String lastNumberStr = maxOrgNo.substring(index + 1);
					int number = Integer.parseInt(lastNumberStr);
					number += 1;
					if (number > 99) {
						lastNumberStr = "" + number;
					}else if (number > 9) {
						lastNumberStr = "0" + number;
					} else {
						lastNumberStr = "00" + number;
					}
					return prefixNumberStr.concat("-").concat(lastNumberStr);
				}
			}else{
				return parentOrgNo.concat("-").concat("001");
			}
		}
		return "sz-001";
	}
}
