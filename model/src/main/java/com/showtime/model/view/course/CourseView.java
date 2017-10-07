package com.showtime.model.view.course;

import com.showtime.model.entity.course.Course;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;

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
public class CourseView extends Course implements Serializable {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = -1L;

    private String preCourseName;

    public String getPreCourseName() {
        return preCourseName;
    }

    public void setPreCourseName(String preCourseName) {
        this.preCourseName = preCourseName;
    }
}
