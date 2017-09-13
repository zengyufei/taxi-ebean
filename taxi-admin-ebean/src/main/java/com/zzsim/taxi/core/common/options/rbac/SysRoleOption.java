package com.zzsim.taxi.core.common.options.rbac;

import com.zzsim.taxi.core.common.base.BaseEntity;

public class SysRoleOption extends BaseEntity {

    private String roleName;
    private String orgNo;

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getOrgNo() {
        return orgNo;
    }

    public void setOrgNo(String orgNo) {
        this.orgNo = orgNo;
    }
}
