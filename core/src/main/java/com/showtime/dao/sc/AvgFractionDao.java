package com.showtime.dao.sc;


import com.showtime.model.entity.sc.AvgFraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <b><code>AvgFractionDao</code></b>
 * <p/>
 * AvgFraction的具体实现
 * <p/>
 * <b>Creation Time:</b> Sat Oct 07 18:18:43 CST 2017.
 *
 * @author qinJianLun
 * @version 1.0.0
 * @since andy-core 1.0.0
 */
@Repository
public interface AvgFractionDao extends JpaRepository<AvgFraction, Long>, JpaSpecificationExecutor<AvgFraction> {

}
