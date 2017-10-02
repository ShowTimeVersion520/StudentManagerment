package com.showtime.model.entity.student;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.showtime.model.entity.base.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <b><code>Student</code></b>
 * <p/>
 * Student的具体实现
 * <p/>
 * <b>Creation Time:</b> Tue Oct 03 00:00:13 CST 2017.
 *
 * @author qinJianLun
 * @version 1.0.0
 * @since andy-model 1.0.0
 */
@Entity
@Table(name="t_student")
@DynamicInsert
@DynamicUpdate
public class Student extends BaseEntity implements Serializable {

    /**
    * The constant serialVersionUID.
    */
    private static final long serialVersionUID = -1L;

    public Student(){
        super();
    }
    //学号
    @Column(name = "student_number")
    private String studentNumber;
    //姓名
    @Column(name = "name")
    private String name;
    //性别 1-男 0-女
    @Column(name = "gender")
    private String gender;
    //籍贯
    @Column(name = "native_place")
    private String nativePlace;
    //年纪
    @Column(name = "grade")
    private String grade;
    //班级名称
    @Column(name = "class_name")
    private String className;
    //奖学金等级
    @Column(name = "scholarship_level")
    private Integer scholarshipLevel;

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getNativePlace() {
        return nativePlace;
    }

    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }
    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }
    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }
    public Integer getScholarshipLevel() {
        return scholarshipLevel;
    }

    public void setScholarshipLevel(Integer scholarshipLevel) {
        this.scholarshipLevel = scholarshipLevel;
    }

}
