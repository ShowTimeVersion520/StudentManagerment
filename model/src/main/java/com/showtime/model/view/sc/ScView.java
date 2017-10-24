package com.showtime.model.view.sc;

import java.math.BigDecimal;
import java.util.Date;
//@SqlResultSetMapping
//        (
//                name = "ScViewResults",
//                entities = {
//                        @EntityResult(
//                                entityClass = ScView.class, //就是当前这个类的名字
//                                fields = {
//                                        @FieldResult(name = "id", column = "id"),
//                                        @FieldResult(name = "createTime", column = "create_time"),
//                                        @FieldResult(name = "updateTime", column = "update_time"),
//                                        @FieldResult(name = "studentNumber", column = "student_number"),
//                                        @FieldResult(name = "courseNumber", column = "course_number"),
//                                        @FieldResult(name = "fraction", column = "fraction"),
//                                        @FieldResult(name = "gradeRanking", column = "grade_ranking"),
//                                        @FieldResult(name = "gradeRankingPercent", column = "grade_ranking_percent"),
//                                        @FieldResult(name = "classRanking", column = "class_ranking"),
//                                        @FieldResult(name = "classRankingPercent", column = "class_ranking_percent"),
//                                        @FieldResult(name = "studentName", column = "student_name"),
//                                        @FieldResult(name = "className", column = "class_name"),
//                                        @FieldResult(name = "grade", column = "grade"),
//                                        @FieldResult(name = "courseName", column = "course_name")
//                                }
//                        )
//                },
//                columns = {
//                        @ColumnResult(name = "create_time"),
//                        @ColumnResult(name = "update_time"),
//                        @ColumnResult(name = "student_number"),
//                        @ColumnResult(name = "course_number"),
//                        @ColumnResult(name = "fraction"),
//                        @ColumnResult(name = "grade_ranking"),
//                        @ColumnResult(name = "grade_ranking_percent"),
//                        @ColumnResult(name = "class_ranking"),
//                        @ColumnResult(name = "class_ranking_percent"),
//                        @ColumnResult(name = "student_name"),
//                        @ColumnResult(name = "class_name"),
//                        @ColumnResult(name = "grade"),
//                        @ColumnResult(name = "course_name"),
//
//                }
//        )
public class ScView {

    private Long id;

    private Date createTime;

    private Date updateTime;

    private Integer studentNumber;

    private String courseNumber;

    private BigDecimal fraction;

    private Integer gradeRanking;

    private String gradeRankingPercent;

    private Integer classRanking;

    private String classRankingPercent;

    private String studentName;

    private String className;

    private String grade;

    private String courseName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
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

    public String getGradeRankingPercent() {
        return gradeRankingPercent;
    }

    public void setGradeRankingPercent(String gradeRankingPercent) {
        this.gradeRankingPercent = gradeRankingPercent;
    }

    public Integer getClassRanking() {
        return classRanking;
    }

    public void setClassRanking(Integer classRanking) {
        this.classRanking = classRanking;
    }

    public String getClassRankingPercent() {
        return classRankingPercent;
    }

    public void setClassRankingPercent(String classRankingPercent) {
        this.classRankingPercent = classRankingPercent;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
