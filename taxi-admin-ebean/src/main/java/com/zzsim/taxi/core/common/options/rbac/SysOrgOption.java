package com.zzsim.taxi.core.common.options.rbac;

public class SysOrgOption {

	private String orgNo;
	private String parentOrgNo;
	private String orgName;

	private Integer province;
	private Integer city;

	public String getOrgNo() {
		return orgNo;
	}

	public void setOrgNo(String orgNo) {
		this.orgNo = orgNo;
	}

	public String getParentOrgNo() {
		return parentOrgNo;
	}

	public void setParentOrgNo(String parentOrgNo) {
		this.parentOrgNo = parentOrgNo;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public Integer getProvince() {
		return province;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public Integer getCity() {
		return city;
	}

	public void setCity(Integer city) {
		this.city = city;
	}
}
