package com.showtime.service.sc;

import com.showtime.model.view.course.CourseView;
import com.showtime.model.view.sc.ScView;
import com.showtime.model.view.student.StudentView;
import com.showtime.service.course.CourseService;
import com.showtime.service.student.StudentService;
import org.junit.*;
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

import java.math.BigDecimal;

/**
* <b><code>ScServiceImplTest</code></b>
* <p/>
* Sc的具体实现
 * 有关分数的排名，总分等的方法的测试
* <p/>
* <b>Creation Time:</b> Fri Oct 06 11:02:15 CST 2017.
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
public class ScServiceImplTest {
    /**
     * JdbcTemplate
     */
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    ScService scService;
    @Autowired
    StudentService studentService;
    @Autowired
    CourseService courseService;

    private static String id = "999999";
    private static String studentId = "999999";
    private static String courseId = "999999";

    @Before
    public void init() throws Exception {
        StudentView studentView =new StudentView();
        studentView.setStudentNumber(100);
        studentView.setGrade("14级");
        studentView.setClassName("1班");
        studentView.setGender("男");
        studentView.setNativePlace("广东省");
        studentView.setName("秦");
        studentId = studentService.saveEntity(studentView);

        CourseView courseView =new CourseView();
        courseView.setLearnHours("64");
        courseView.setCredit("2.5");
        courseView.setName("test");

        courseId = courseService.saveEntity(courseView);


        ScView scView =new ScView();
        scView.setCourseNumber(courseService.getEntity(Long.valueOf(courseId)).getCourseNumber());
        scView.setStudentNumber(studentService.getEntity(Long.valueOf(studentId)).getStudentNumber());
        scView.setFraction(new BigDecimal(60.50).setScale(2));
        id = scService.saveEntity(scView);
    }
    @Test
    public void test2GetScs() throws Exception {
        ScView scView = scService.getEntity(Long.valueOf(id));
        Assert.assertEquals(new BigDecimal(60.50).setScale(2), scView.getFraction());
    }

    @Test
    public void test3UpdateScs() throws Exception {
        ScView scView = new ScView();
        scView.setId(Long.valueOf(id));
        scView.setFraction(new BigDecimal(100.00).setScale(2));
        scService.updateEntity(scView);

        scView = scService.getEntity(Long.valueOf(id));
        Assert.assertEquals(new BigDecimal(100.00).setScale(2), scView.getFraction());


    }

    @Test
    public void test4getAllScs() throws Exception {
        int pageNumber = 0;
        int pageSize = 10;
        ScView scView = new ScView();
        ReflectUtils.fillModelByDefault(scView);
        Page<ScView>  scViews =  scService.getEntitiesByParms(scView,pageNumber,pageSize);
    }

    @Test
    public void test5DeleteScs() throws Exception {
        scService.deleteEntities(id);
    }

    @Test
    public void test6DeleteScsById() throws Exception {
        scService.deleteEntity(Long.valueOf(id));
    }

    @Ignore
    @Test
    public void testsetRanking() throws Exception {
        scService.setRanking();
    }
}
