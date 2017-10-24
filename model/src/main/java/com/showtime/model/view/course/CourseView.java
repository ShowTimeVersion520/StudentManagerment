package com.showtime.model.view.course;

import com.showtime.model.entity.course.Course;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <b><code>CourseView</code></b>
 * <p/>
 * Course的具体实现
 * <p/>
 * <b>Creation Time:</b> Fri Oct 06 11:18:24 CST 2017.
 *
 * @author qinJianLun
 * @version 1.0.0
 * @since andy-model 1.0.0
 */
@ApiModel
public class CourseView implements Serializable {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -1L;

    private Long id;
    private String courseNumber;
    private String name;
    private String learnHours;
    private String credit;
    private BigDecimal avgFraction;
    private String preCourseName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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
//
//    public void setAvgFraction(Integer avgFraction) {
//        this.avgFraction = (avgFraction != null ? new BigDecimal((float)avgFraction/100).setScale(2, BigDecimal.ROUND_HALF_UP):null);
//    }


    public String getPreCourseName() {
        return preCourseName;
    }

    public void setPreCourseName(String preCourseName) {
        this.preCourseName = preCourseName;
    }
}
