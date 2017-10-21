package com.showtime.model.entity.sc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.showtime.model.entity.base.BaseEntity;
import com.showtime.model.entity.course.Course;
import com.showtime.model.entity.student.Student;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <b><code>Sc</code></b>
 * <p/>
 * Sc的具体实现
 * <p/>
 * <b>Creation Time:</b> Fri Oct 06 11:02:15 CST 2017.
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
    //添加时间
    @Column(name = "create_time")
    private Long createTime;
    //更新时间
    @Column(name = "update_time")
    private Long updateTime;
    //学号
    @Column(name = "student_number")
    private Integer studentNumber;
    //课程号
    @Column(name = "course_number")
    private String courseNumber;
    //成绩
    @Column(name = "fraction")
    private Integer fraction;
    //全级排名
    @Column(name = "grade_ranking")
    private Integer gradeRanking;
    //全级排名百分比
    @Column(name = "grade_ranking_percent")
    private Integer gradeRankingPercent;
    //班级排名
    @Column(name = "class_ranking")
    private Integer classRanking;
    //班级排名百分比
    @Column(name = "class_ranking_percent")
    private Integer classRankingPercent;

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }
    public Long getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Long updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(Integer studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }


    public Integer getGradeRanking() {
        return gradeRanking;
    }

    public void setGradeRanking(Integer gradeRanking) {
        this.gradeRanking = gradeRanking;
    }

    public Integer getClassRanking() {
        return classRanking;
    }

    public void setClassRanking(Integer classRanking) {
        this.classRanking = classRanking;
    }

    public Integer getFraction() {
        return fraction;
    }

    public void setFraction(Integer fraction) {
        this.fraction = fraction;
    }

    public Integer getGradeRankingPercent() {
        return gradeRankingPercent;
    }

    public void setGradeRankingPercent(Integer gradeRankingPercent) {
        this.gradeRankingPercent = gradeRankingPercent;
    }

    public Integer getClassRankingPercent() {
        return classRankingPercent;
    }

    public void setClassRankingPercent(Integer classRankingPercent) {
        this.classRankingPercent = classRankingPercent;
    }

    public Sc(Long createTime, Long updateTime, Integer studentNumber, String courseNumber, Integer fraction, Integer gradeRanking, Integer gradeRankingPercent, Integer classRanking, Integer classRankingPercent) {
        this.createTime = createTime;
        this.updateTime = updateTime;
        this.studentNumber = studentNumber;
        this.courseNumber = courseNumber;
        this.fraction = fraction;
        this.gradeRanking = gradeRanking;
        this.gradeRankingPercent = gradeRankingPercent;
        this.classRanking = classRanking;
        this.classRankingPercent = classRankingPercent;
    }


}
