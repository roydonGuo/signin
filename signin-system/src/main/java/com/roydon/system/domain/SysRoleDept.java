package com.roydon.system.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 角色和单元关联 sys_role_dept
 */
@TableName("sys_role_dept")
public class SysRoleDept {
    /**
     * 角色ID
     */
    private Long roleId;

    /**
     * 单元ID
     */
    private Long deptId;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public Long getDeptId() {
        return deptId;
    }

    public void setDeptId(Long deptId) {
        this.deptId = deptId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("roleId", getRoleId())
                .append("deptId", getDeptId())
                .toString();
    }
}
