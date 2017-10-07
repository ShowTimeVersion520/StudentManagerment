package com.showtime.model.entity.sc;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.showtime.model.entity.base.BaseEntity;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <b><code>AvgFraction</code></b>
 * <p/>
 * AvgFraction的具体实现
 * <p/>
 * <b>Creation Time:</b> Sat Oct 07 18:18:43 CST 2017.
 *
 * @author qinJianLun
 * @version 1.0.0
 * @since andy-model 1.0.0
 */
@Entity
@Table(name="v_course_avg_fraction")
@DynamicInsert
@DynamicUpdate
public class AvgFraction extends BaseEntity implements Serializable {

    /**
    * The constant serialVersionUID.
    */
    private static final long serialVersionUID = -1L;

    public AvgFraction(){
        super();
    }
    //课程编号
    @Column(name = "course_number")
    private String courseNumber;
    //平均成绩
    @Column(name = "avg_fraction")
    private BigDecimal avgFraction;

    public String getCourseNumber() {
        return courseNumber;
    }

    public void setCourseNumber(String courseNumber) {
        this.courseNumber = courseNumber;
    }
    public BigDecimal getAvgFraction() {
        return avgFraction;
    }

    public void setAvgFraction(BigDecimal avgFraction) {
        this.avgFraction = avgFraction;
    }

}
