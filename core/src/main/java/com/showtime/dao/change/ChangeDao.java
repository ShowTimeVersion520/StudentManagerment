package com.showtime.dao.change;


import com.showtime.model.entity.change.Change;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <b><code>ChangeDao</code></b>
 * <p/>
 * Change的具体实现
 * <p/>
 * <b>Creation Time:</b> Tue Oct 03 11:57:14 CST 2017.
 *
 * @author qinJianLun
 * @version 1.0.0
 * @since andy-core 1.0.0
 */
@Repository
public interface ChangeDao extends JpaRepository<Change, Long>, JpaSpecificationExecutor<Change> {

}
