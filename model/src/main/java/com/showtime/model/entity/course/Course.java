package com.showtime.model.entity.course;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.showtime.model.entity.base.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <b><code>Course</code></b>
 * <p/>
 * Course的具体实现
 * <p/>
 * <b>Creation Time:</b> Fri Oct 06 11:18:24 CST 2017.
 *
 * @author qinJianLun
 * @version 1.0.0
 * @since andy-model 1.0.0
 */
@Entity
@Table(name="t_course")
@DynamicInsert
@DynamicUpdate
public class Course extends BaseEntity implements Serializable {

    /**
    * The constant serialVersionUID.
    */
    private static final long serialVersionUID = -1L;

    public Course(){
        super();
    }
    //课程编号
    @Column(name = "course_number")
    private String courseNumber;
    //课程名称
    @Column(name = "name")
    private String name;
    //学时
    @Column(name = "learn_hours")
    private String learnHours;
    //学分
    @Column(name = "credit")
    private String credit;
    //平均分
    @Column(name = "avg_fraction")
    private BigDecimal avgFraction;
    //先修课id号
    @Column(name = "pre_course")
    private Long preCourse;

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getLearnHours() {
        return learnHours;
    }

    public void setLearnHours(String learnHours) {
        this.learnHours = learnHours;
    }
    public String getCredit() {
        return credit;
    }

    public void setCredit(String credit) {
        this.credit = credit;
    }
    public BigDecimal getAvgFraction() {
        return avgFraction;
    }

    public void setAvgFraction(BigDecimal avgFraction) {
        this.avgFraction = avgFraction;
    }
    public Long getPreCourse() {
        return preCourse;
    }

    public void setPreCourse(Long preCourse) {
        this.preCourse = preCourse;
    }

}
