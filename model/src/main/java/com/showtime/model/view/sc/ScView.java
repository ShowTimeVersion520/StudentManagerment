package com.showtime.model.view.sc;

import com.showtime.model.entity.sc.Sc;
import io.swagger.annotations.ApiModel;

import javax.persistence.ColumnResult;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.SqlResultSetMapping;
import java.io.Serializable;

/**
 * <b><code>ScView</code></b>
 * <p/>
 * Sc的具体实现
 * <p/>
 * <b>Creation Time:</b> Fri Oct 06 11:02:15 CST 2017.
 *
 * @author qinJianLun
 * @version 1.0.0
 * @since andy-model 1.0.0
 */
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
@ApiModel
public class ScView extends Sc implements Serializable {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -1L;

    private String studentName;
    private String className;
    private String grade;
    private String courseName;

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

    public ScView(Sc sc) {
        super(sc.getCreateTime(), sc.getUpdateTime(), sc.getStudentNumber(), sc.getCourseNumber(), sc.getFraction(),sc.getGradeRanking(), sc.getGradeRankingPercent(), sc.getClassRanking(), sc.getClassRankingPercent());
    }

    public ScView(Sc sc, String studentName, String courseName) {
        super(sc.getCreateTime(), sc.getUpdateTime(), sc.getStudentNumber(), sc.getCourseNumber(), sc.getFraction(),sc.getGradeRanking(), sc.getGradeRankingPercent(), sc.getClassRanking(), sc.getClassRankingPercent());
        this.studentName = studentName;
        this.courseName = courseName;
    }

    public ScView(){

    }
}
