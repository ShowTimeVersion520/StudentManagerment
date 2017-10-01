package com.showtime.model.entity.sc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.showtime.model.entity.base.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <b><code>Sc</code></b>
 * <p/>
 * Sc的具体实现
 * <p/>
 * <b>Creation Time:</b> Sun Oct 01 17:26:25 CST 2017.
 *
 * @author qinJianLun
 * @version 1.0.0
 * @since andy-model 1.0.0
 */
@Entity
@Table(name="t_sc")
@DynamicInsert
@DynamicUpdate
public class Sc extends BaseEntity implements Serializable {

    /**
    * The constant serialVersionUID.
    */
    private static final long serialVersionUID = -1L;

    public Sc(){
        super();
    }
    //更新时间
    @Column(name = "update_time")
    private Long updateTime;
    //学号
    @Column(name = "student_number")
    private String studentNumber;
    //课程号
    @Column(name = "course_number")
    private String courseNumber;

    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }
    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }
    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }

}
