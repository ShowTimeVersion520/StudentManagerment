package com.showtime.model.entity.sysauthority;

import com.showtime.model.entity.base.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

@Entity
@Table(name="t_sys_authority")
public class SysAuthority extends BaseEntity implements Serializable {

    /**
     * 标识当前角色是否拥有该权限
     */
    @Transient
    private boolean hasAuthority = false;

    /**
     * The SysUser name.
     */

    @Column(name = "name")
    private String name;
    /**
     * The Password.
     */
    @Column(name="description")
    private String description;

    @Column(name = "enabled")
    private Integer enabled;

    public Integer getEnabled() {
        return enabled;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isHasAuthority() {
        return hasAuthority;
    }

    public void setHasAuthority(boolean hasAuthority) {
        this.hasAuthority = hasAuthority;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SysAuthority{");
        sb.append("hasAuthority=").append(hasAuthority);
        sb.append(", name='").append(name).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
