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
    @ManyToOne
    @JoinColumn(name = "student_number",referencedColumnName = "student_number")
    private Student student;
    //课程号
    @ManyToOne
    @JoinColumn(name = "course_number",referencedColumnName = "course_number")
    private Course course;
    //成绩
    @Column(name = "fraction")
    private BigDecimal fraction;
    //全级排名
    @Column(name = "grade_ranking")
    private Integer gradeRanking;
    //全级排名百分比
    @Column(name = "grade_ranking_percent")
    private BigDecimal gradeRankingPercent;
    //班级排名
    @Column(name = "class_ranking")
    private Integer classRanking;
    //班级排名百分比
    @Column(name = "class_ranking_percent")
    private BigDecimal classRankingPercent;

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

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public BigDecimal getFraction() {
        return fraction;
    }

    public void setFraction(BigDecimal fraction) {
        this.fraction = fraction;
    }
    public Integer getGradeRanking() {
        return gradeRanking;
    }

    public void setGradeRanking(Integer gradeRanking) {
        this.gradeRanking = gradeRanking;
    }
    public BigDecimal getGradeRankingPercent() {
        return gradeRankingPercent;
    }

    public void setGradeRankingPercent(BigDecimal gradeRankingPercent) {
        this.gradeRankingPercent = gradeRankingPercent;
    }
    public Integer getClassRanking() {
        return classRanking;
    }

    public void setClassRanking(Integer classRanking) {
        this.classRanking = classRanking;
    }
    public BigDecimal getClassRankingPercent() {
        return classRankingPercent;
    }

    public void setClassRankingPercent(BigDecimal classRankingPercent) {
        this.classRankingPercent = classRankingPercent;
    }

}
