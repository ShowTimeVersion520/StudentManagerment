package com.showtime.dao.sc;


import com.showtime.dao.base.BaseDao;
import com.showtime.dao.base.BatchDao;
import com.showtime.model.entity.sc.Sc;
import com.showtime.model.view.sc.ScView;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.stereotype.Repository;

import javax.persistence.QueryHint;
import java.util.List;

/**
 * <b><code>ScDao</code></b>
 * <p/>
 * Sc的具体实现
 * <p/>
 * <b>Creation Time:</b> Fri Oct 06 11:02:15 CST 2017.
 *
 * @author qinJianLun
 * @version 1.0.0
 * @since andy-core 1.0.0
 */
@Repository
public interface ScDao extends JpaRepository<Sc, Long>, JpaSpecificationExecutor<Sc>, BatchDao, BaseDao<ScView> {

    @Query(value = "SELECT * FROM t_sc sc INNER JOIN t_student student ON sc.student_number = student.student_number WHERE sc.course_number = ?1 AND student.class_name = ?2 AND student.grade = ?3 ORDER BY ?4 DESC", nativeQuery = true)
    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
    List<Sc> getByCourse_CourseNumberAndStudent_ClassNameAndStudent_Grade(String courseNumber, String className, String grade, String sort);

    @Query(value = "SELECT * FROM t_sc sc INNER JOIN t_student student ON sc.student_number = student.student_number WHERE sc.course_number = ?1 AND student.grade = ?2 ORDER BY ?3 DESC ", nativeQuery = true)
    @QueryHints({ @QueryHint(name = "org.hibernate.cacheable", value ="true") })
    List<Sc> getByCourse_CourseNumberAndStudent_Grade(String courseNumber, String grade, String sort);
}
