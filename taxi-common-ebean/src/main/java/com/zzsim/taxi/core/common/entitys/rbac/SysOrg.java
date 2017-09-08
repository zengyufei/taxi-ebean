package com.zzsim.taxi.core.common.entitys.rbac;

import com.zzsim.taxi.core.common.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name="t_sys_org")
public class SysOrg extends BaseEntity{

    private String orgNo;
    private String parentOrgNo;
    private String orgName;

    private int province;
    private int city;
    private String address;

    private String description;

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

    public int getProvince() {
        return province;
    }

    public void setProvince(int province) {
        this.province = province;
    }

    public int getCity() {
        return city;
    }

    public void setCity(int city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}