package com.showtime.dao.sc;


import com.showtime.model.entity.sc.Sc;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * <b><code>ScDao</code></b>
 * <p/>
 * Sc的具体实现
 * <p/>
 * <b>Creation Time:</b> Sun Oct 01 17:20:57 CST 2017.
 *
 * @author qinJianLun
 * @version 1.0.0
 * @since andy-core 1.0.0
 */
@Repository
public interface ScDao extends JpaRepository<Sc, Long>, JpaSpecificationExecutor<Sc> {

}
