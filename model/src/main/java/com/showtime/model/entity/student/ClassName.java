package com.showtime.model.entity.student;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.showtime.model.entity.base.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <b><code>ClassName</code></b>
 * <p/>
 * ClassName的具体实现
 * <p/>
 * <b>Creation Time:</b> Thu Oct 05 16:44:31 CST 2017.
 *
 * @author qinJianLun
 * @version 1.0.0
 * @since andy-model 1.0.0
 */
@Entity
@Table(name="t_class_name")
@DynamicInsert
@DynamicUpdate
public class ClassName extends BaseEntity implements Serializable {

    /**
    * The constant serialVersionUID.
    */
    private static final long serialVersionUID = -1L;

    public ClassName(){
        super();
    }
    //班级名称
    @Column(name = "class_name")
    private String className;

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

}
