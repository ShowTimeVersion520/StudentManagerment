package com.showtime.model.entity.sysrole;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.showtime.model.entity.sysauthority.SysAuthority;
import com.showtime.model.entity.base.BaseEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name="t_sys_role")
public class SysRole extends BaseEntity implements Serializable {

    /**
     * 标识当前用户是否拥有该角色
     */
    @Transient
    private Boolean hasRole = false;

    /**
     * The SysUser name.
     */

    @Column(name = "english_name")
    private String englishName;
    /**
     * The Password.
     */
    @Column(name="chinese_name")
    private String chineseName;

    @Column(name="description")
    private String description;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @ManyToMany(cascade = CascadeType.MERGE, fetch=FetchType.LAZY)
    @JoinTable(name = "t_sys_role_authority",
            joinColumns = @JoinColumn(name="sys_role_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="sys_authority_id", referencedColumnName="id"))
    private List<SysAuthority> sysAuthorities;

    public String getEnglishName() {
        return englishName;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public String getChineseName() {
        return chineseName;
    }

    public void setChineseName(String chineseName) {
        this.chineseName = chineseName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<SysAuthority> getSysAuthorities() {
        return sysAuthorities;
    }

    public void setSysAuthorities(List<SysAuthority> sysAuthorities) {
        this.sysAuthorities = sysAuthorities;
    }

    public Boolean getHasRole() {
        return hasRole;
    }

    public void setHasRole(Boolean hasRole) {
        this.hasRole = hasRole;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("SysRole{");
        sb.append("englishName='").append(englishName).append('\'');
        sb.append(", chineseName='").append(chineseName).append('\'');
        sb.append(", description='").append(description).append('\'');
        sb.append(", sysAuthorities=").append(sysAuthorities);
        sb.append('}');
        return sb.toString();
    }
}
