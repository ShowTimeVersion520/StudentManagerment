package com.showtime.model.entity.sysuser;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.showtime.model.entity.base.BaseEntity;
import com.showtime.model.entity.sysrole.SysRole;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="t_sys_user")
@DynamicInsert
@DynamicUpdate
public class SysUser extends BaseEntity implements Serializable {

    public  SysUser(){

    }

    public SysUser(String userName, String password){
        this.userName = userName;
        this.password = password;
    }

    /**
     * The SysUser name.
     */
    @Column(name = "username")
    private String userName;
    /**
     * The Password.
     */
    @Column(name="password")
    private String password;

    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    @OneToOne(cascade = CascadeType.MERGE, fetch=FetchType.LAZY)
    @JoinTable(name = "t_sys_user_role",
            joinColumns = @JoinColumn(name="sys_user_id",referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name="sys_role_id", referencedColumnName="id"))
    private SysRole sysRole;


    /**
     * Gets entity name.
     *
     * @return the entity name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * Sets entity name.
     *
     * @param userName the entity name
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    public SysRole getSysRole() {
        return sysRole;
    }

    public void setSysRole(SysRole sysRole) {
        this.sysRole = sysRole;
    }
}
