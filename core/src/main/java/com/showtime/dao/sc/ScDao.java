package com.showtime.dao.sc;


import com.showtime.model.entity.sc.Sc;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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
public interface ScDao extends JpaRepository<Sc, Long>, JpaSpecificationExecutor<Sc> {

    List<Sc> getByCourse_CourseNumberAndStudent_ClassName(String courseNumber, String className, Sort sort);

    List<Sc> getByCourse_CourseNumberAndStudent_Grade(String courseNumber, String grade, Sort sort);
}
