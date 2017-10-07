package com.showtime.service.course;

import com.showtime.model.view.course.CourseView;
import com.showtime.model.view.course.PreCourseView;
import com.showtime.service.base.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import java.util.List;

/**
* <b><code>Course</code></b>
* <p/>
* Course的具体实现
* <p/>
* <b>Creation Time:</b> Fri Oct 06 11:18:24 CST 2017.
*
* @author qinJianLun
* @version 1.0.0
 * @since andy-core 1.0.0
 */
@Service
public interface CourseService extends BaseService<CourseView> {

    /**
    * 通过 keyword 获取课程分页
    * @param currentPage
    * @param pageSize
    * @return
    */
    Page<CourseView> getCourseByKeyword(String keyword, int currentPage, int pageSize);

    /**
     * 获取先修课列表
     * @return
     */
    List<PreCourseView> getAllPreCourses();

    /**
     * 更新课程平均成绩
     */
    void updateCoursesAvgFraction();
}
