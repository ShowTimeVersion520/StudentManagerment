package com.showtime.dao.student;


import com.showtime.model.entity.student.Scholarship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <b><code>ScholarshipDao</code></b>
 * <p/>
 * Scholarship的具体实现
 * <p/>
 * <b>Creation Time:</b> Thu Oct 05 16:34:21 CST 2017.
 *
 * @author qinJianLun
 * @version 1.0.0
 * @since andy-core 1.0.0
 */
@Repository
public interface ScholarshipDao extends JpaRepository<Scholarship, Long>, JpaSpecificationExecutor<Scholarship> {

    @Query("select distinct s.scholarshipLevel from Scholarship as s")
    List<String> getAllScholarshipLevels();
}
