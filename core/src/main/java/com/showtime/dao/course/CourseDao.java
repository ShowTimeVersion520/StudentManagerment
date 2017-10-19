package com.showtime.dao.course;


import com.showtime.model.entity.course.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <b><code>CourseDao</code></b>
 * <p/>
 * Course的具体实现
 * <p/>
 * <b>Creation Time:</b> Fri Oct 06 11:18:24 CST 2017.
 *
 * @author qinJianLun
 * @version 1.0.0
 * @since andy-core 1.0.0
 */
@Repository
public interface CourseDao extends JpaRepository<Course, Long>, JpaSpecificationExecutor<Course> {

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE t_course AS c INNER JOIN v_course_avg_fraction AS a ON c.course_number = a.course_number SET c.avg_fraction = a.avg_fraction", nativeQuery = true)
    int updateCoursesAvgFraction();

    @Query("select c.courseNumber from Course c where c.name = ?1")
    List<String> getCourseNumberByName(String courseName);

    Course getByCourseNumber(String courseNumber);
}
