package com.showtime.dao.student;


import com.showtime.model.entity.student.Student;
import com.showtime.model.view.student.CountStudentView;
import org.jboss.logging.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

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

    @Query("SELECT new com.showtime.model.view.student.CountStudentView(COUNT(s), s.grade,s.className) FROM Student AS s GROUP BY s.grade,s.className")
    List<CountStudentView> getCountByClassName();

    @Query("SELECT new com.showtime.model.view.student.CountStudentView(COUNT(s), s.grade) FROM Student AS s GROUP BY s.grade")
    List<CountStudentView> getCountByGrade();

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE t_student AS s INNER JOIN v_student_sum_fraction AS sf ON s.student_number = sf.student_number SET s.sum_fraction = sf.sum_fraction", nativeQuery = true)
    int updateStudentSumFractions();

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE t_student SET scholarship_level = ?2 WHERE grade = ?1 AND sum_fraction >= (SELECT stable.sum_fraction FROM (SELECT sum_fraction FROM t_student ORDER BY sum_fraction DESC LIMIT ?3,1)stable)", nativeQuery = true)
    int updateScholarship(String grade, Integer scholarshipLevel, Integer number);

    @Query("select s.studentNumber from Student s where s.grade=?1")
    List<String> getStudentNumberByGrade(String grade);

    @Query("select s.studentNumber from Student s where s.className=?1")
    List<String> getStudentNumberByClassName(String className);


    @Query("select s.studentNumber from Student s where s.name=?1")
    List<String> getStudentNumberByName(String name);

    Student getByStudentNumber(String studentNumber);
}
