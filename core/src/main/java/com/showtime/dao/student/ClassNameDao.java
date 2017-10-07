package com.showtime.dao.student;


import com.showtime.model.entity.student.ClassName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * <b><code>ClassNameDao</code></b>
 * <p/>
 * ClassName的具体实现
 * <p/>
 * <b>Creation Time:</b> Thu Oct 05 16:41:20 CST 2017.
 *
 * @author qinJianLun
 * @version 1.0.0
 * @since andy-core 1.0.0
 */
@Repository
public interface ClassNameDao extends JpaRepository<ClassName, Long>, JpaSpecificationExecutor<ClassName> {

    @Query("select distinct c.className from ClassName as c")
    List<String> getAllClassNames();

    @Query("select distinct c.grade from ClassName as c")
    List<String> getAllGrades();
}
