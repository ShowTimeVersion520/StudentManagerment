package com.showtime.dao.student;


import com.showtime.model.entity.student.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * <b><code>StudentDao</code></b>
 * <p/>
 * Student的具体实现
 * <p/>
 * <b>Creation Time:</b> Sun Oct 01 17:12:45 CST 2017.
 *
 * @author qinJianLun
 * @version 1.0.0
 * @since andy-core 1.0.0
 */
@Repository
public interface StudentDao extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {

}
