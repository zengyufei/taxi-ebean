package ebean.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.ys.common.base.entiry.BaseEntity;
import com.ys.common.enums.EquEnum;
import io.ebean.annotation.DbComment;
import lombok.AccessLevel;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.Table;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Data
@Accessors(chain = true)
@Entity
@Table(name="test_sys_member")
public class TestSysMember extends BaseEntity {

    @Excel(name = "账号", height = 10, width = 20)
    String account;
    @Excel(name = "密码", height = 10, width = 20)
    String password;
    @Excel(name = "设备类型", replace = { "门口_DoorMachine", "中心_CenterMachine", "围墙_WallMachine", "室内_IndoorMachine" }, suffix = "机", height = 10, width = 10)
    @DbComment("设备类型")
    EquEnum equEnum;
    String realName;
    String identity;
    String mobile;
    String email;
    String qq;
    String remark;

    long roleId;
    String roleName;

    long orgNo;
    String orgName;
}