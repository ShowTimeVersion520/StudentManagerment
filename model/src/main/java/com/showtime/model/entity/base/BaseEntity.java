package com.showtime.model.entity.base;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * <b><code>BaseEntity</code></b>
 * <p/>
 * BaseEntity
 * <p/>
 * <b>Creation Time:</b> 2017/05/17 21:18.
 *
 * @author lvxin
 * @version 1.0.0
 * @since model 1.0.0
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    @Id
    @Column(name = "id", unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "create_time")
    private Long createTime;

    @NotNull
    @Column(name = "update_time")
    private Long updateTime;

    @Column(name = "enabled")
    private Integer enabled;

    @Column(name = "auditors")
    private String auditors;

    @Column(name = "audit_status")
    private Integer auditStatus;

    @Column(name = "audit_content")
    private String auditContent;

    @Column(name = "audit_time")
    private Long auditTime;

    public Long getAuditTime() {
        return auditTime;
    }

    public void setAuditTime(Long auditTime) {
        this.auditTime = auditTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setEnabled(Integer enabled) {
        this.enabled = enabled;
    }

    public String getAuditors() {
        return auditors;
    }

    public void setAuditors(String auditors) {
        this.auditors = auditors;
    }

    public Integer getAuditStatus() {
        return auditStatus;
    }

    public void setAuditStatus(Integer auditStatus) {
        this.auditStatus = auditStatus;
    }

    public String getAuditContent() {
        return auditContent;
    }

    public void setAuditContent(String auditContent) {
        this.auditContent = auditContent;
    }

    public Long getId() {
        return id;
    }


    public Long getCreateTime() {
        return createTime;
    }


    public Long getUpdateTime() {
        return updateTime;
    }

    public Integer getEnabled() {
        return enabled;
    }

    @Override
    public String toString() {
        return "BaseEntity{" +
                "id=" + id +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", enabled=" + enabled +
                ", auditors='" + auditors + '\'' +
                ", auditStatus=" + auditStatus +
                ", auditContent='" + auditContent + '\'' +
                '}';
    }
}
