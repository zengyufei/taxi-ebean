package com.zzsim.taxi.core.common.entitys.rbac;

import com.zzsim.taxi.core.common.base.BaseEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name="t_sys_org")
public class SysOrg extends BaseEntity implements Serializable {

    private static final long serialVersionUID = -2049682312143380490L;

    private String orgNo;
    private String parentOrgNo;
    private String orgName;

    private Integer province;
    private Integer city;
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