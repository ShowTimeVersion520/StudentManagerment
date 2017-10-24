package com.showtime.service.student;

import com.showtime.model.view.student.StudentView;
import com.showtime.service.student.StudentService;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import com.showtime.service.commons.utils.ReflectUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import org.junit.Before;

/**
* <b><code>StudentServiceImplTest</code></b>
* <p/>
* Student的具体实现
* <p/>
* <b>Creation Time:</b> Sun Oct 01 17:12:45 CST 2017.
*
* @author qinJianLun
* @version 1.0.0
* @since andy-core 1.0.0
*/
@ContextConfiguration(locations = "classpath:application-context-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//@Rollback(false)
@Transactional
public class StudentServiceImplTest {
    /**
     * JdbcTemplate
     */
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    StudentService studentService;

    private static String id = "999999";

    @Before
    public void init() throws Exception {
        StudentView studentView =new StudentView();
        //studentView.setUserName("lvxin_test@andy.com");
        //studentView.setPassword("123");
        studentView.setGender("男");
        studentView.setStudentNumber(99);
        ReflectUtils.fillModel(studentView);
        id = studentService.saveEntity(studentView);
    }

    @Test
    public void test1SaveStudents() throws Exception {
        StudentView studentView =new StudentView();
        //studentView.setUserName("lvxin_test@andy.com");
        //studentView.setPassword("123");
        ReflectUtils.fillModel(studentView);
        id = studentService.saveEntity(studentView);
    }

    @Test
    public void test2GetStudents() throws Exception {
        StudentView studentView = studentService.getEntity(Long.valueOf(id));
        //Assert.assertEquals("lvxin_test@andy.com", studentView.getUserName());
    }

    @Test
    public void test3UpdateStudents() throws Exception {
        StudentView studentView = new StudentView();
        studentView.setId(Long.valueOf(id));
        //studentView.setUserName("lvxin_test1@andy.com");
        studentService.updateEntity(studentView);

        studentView = studentService.getEntity(Long.valueOf(id));
        //Assert.assertEquals("lvxin_test1@andy.com", studentView.getUserName());


    }

    @Test
    public void test4getAllStudents() throws Exception {
        int pageNumber = 0;
        int pageSize = 10;
        StudentView studentView = new StudentView();
        studentView.setClassName("1班");
        ReflectUtils.fillModelByDefault(studentView);
        Page<StudentView>  studentViews =  studentService.getEntitiesByParms(studentView,pageNumber,pageSize);
    }

    @Test
    public void test5DeleteStudents() throws Exception {
        studentService.deleteEntities(id);
    }

    @Test
    public void test6DeleteStudentsById() throws Exception {
        studentService.deleteEntity(Long.valueOf(id));
    }
}
