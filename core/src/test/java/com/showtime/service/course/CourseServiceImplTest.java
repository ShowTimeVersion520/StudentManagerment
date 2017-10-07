package com.showtime.service.course;

import com.showtime.model.view.course.CourseView;
import com.showtime.service.course.CourseService;
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
* <b><code>CourseServiceImplTest</code></b>
* <p/>
* Course的具体实现
* <p/>
* <b>Creation Time:</b> Fri Oct 06 11:18:24 CST 2017.
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
public class CourseServiceImplTest {
    /**
     * JdbcTemplate
     */
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    CourseService courseService;

    private static String id = "999999";

    @Before
    public void init() throws Exception {
        CourseView courseView =new CourseView();
        //courseView.setUserName("lvxin_test@andy.com");
        //courseView.setPassword("123");
        ReflectUtils.fillModel(courseView);
        id = courseService.saveEntity(courseView);
    }

    @Test
    public void test1SaveCourses() throws Exception {
        CourseView courseView =new CourseView();
        //courseView.setUserName("lvxin_test@andy.com");
        //courseView.setPassword("123");
        ReflectUtils.fillModel(courseView);
        id = courseService.saveEntity(courseView);
    }

    @Test
    public void test2GetCourses() throws Exception {
        CourseView courseView = courseService.getEntity(Long.valueOf(id));
        //Assert.assertEquals("lvxin_test@andy.com", courseView.getUserName());
    }

    @Test
    public void test3UpdateCourses() throws Exception {
        CourseView courseView = new CourseView();
        courseView.setId(Long.valueOf(id));
        //courseView.setUserName("lvxin_test1@andy.com");
        courseService.updateEntity(courseView);

        courseView = courseService.getEntity(Long.valueOf(id));
        //Assert.assertEquals("lvxin_test1@andy.com", courseView.getUserName());


    }

    @Test
    public void test4getAllCourses() throws Exception {
        int pageNumber = 0;
        int pageSize = 10;
        CourseView courseView = new CourseView();
        ReflectUtils.fillModelByDefault(courseView);
        Page<CourseView>  courseViews =  courseService.getEntitiesByParms(courseView,pageNumber,pageSize);
    }

    @Test
    public void test5DeleteCourses() throws Exception {
        courseService.deleteEntities(id);
    }

    @Test
    public void test6DeleteCoursesById() throws Exception {
        courseService.deleteEntity(Long.valueOf(id));
    }
}
