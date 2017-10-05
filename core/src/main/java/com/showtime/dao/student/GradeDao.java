package com.showtime.dao.student;


import com.showtime.model.entity.student.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <b><code>GradeDao</code></b>
 * <p/>
 * Grade的具体实现
 * <p/>
 * <b>Creation Time:</b> Thu Oct 05 16:50:41 CST 2017.
 *
 * @author qinJianLun
 * @version 1.0.0
 * @since andy-core 1.0.0
 */
@Repository
public interface GradeDao extends JpaRepository<Grade, Long>, JpaSpecificationExecutor<Grade> {

    @Query("select distinct g.grade from Grade as g")
    List<String> getAllGrades();
}
