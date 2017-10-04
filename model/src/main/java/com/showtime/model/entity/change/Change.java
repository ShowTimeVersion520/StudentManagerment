package com.showtime.model.entity.change;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.showtime.model.entity.base.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;

/**
 * <b><code>Change</code></b>
 * <p/>
 * Change的具体实现
 * <p/>
 * <b>Creation Time:</b> Tue Oct 03 12:01:41 CST 2017.
 *
 * @author qinJianLun
 * @version 1.0.0
 * @since andy-model 1.0.0
 */
@Entity
@Table(name="t_change")
@DynamicInsert
@DynamicUpdate
public class Change extends BaseEntity implements Serializable {

    /**
    * The constant serialVersionUID.
    */
    private static final long serialVersionUID = -1L;

    public Change(){
        super();
    }
    //成绩变动 0-未变动 1-已变动
    @Column(name = "sc_change")
    private String scChange;
    //学生信息变动 0-未变动 1-已变动
    @Column(name = "student_change")
    private String studentChange;
    //课程信息变动 0-未变动 1-已变动
    @Column(name = "course_change")
    private String courseChange;

    public String getScChange() {
        return scChange;
    }

    public void setScChange(String scChange) {
        this.scChange = scChange;
    }
    public String getStudentChange() {
        return studentChange;
    }

    public void setStudentChange(String studentChange) {
        this.studentChange = studentChange;
    }
    public String getCourseChange() {
        return courseChange;
    }

    public void setCourseChange(String courseChange) {
        this.courseChange = courseChange;
    }

}
