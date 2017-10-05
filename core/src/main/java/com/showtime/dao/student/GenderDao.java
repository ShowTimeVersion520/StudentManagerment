package com.showtime.dao.student;


import com.showtime.model.entity.student.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <b><code>GenderDao</code></b>
 * <p/>
 * Gender的具体实现
 * <p/>
 * <b>Creation Time:</b> Thu Oct 05 18:32:00 CST 2017.
 *
 * @author qinJianLun
 * @version 1.0.0
 * @since andy-core 1.0.0
 */
@Repository
public interface GenderDao extends JpaRepository<Gender, Long>, JpaSpecificationExecutor<Gender> {

    @Query("select distinct g.gender from Gender as g")
    List<String> getAllGenders();
}
