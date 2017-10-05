package com.showtime.service.student;

import com.showtime.model.view.student.GradeView;
import com.showtime.service.student.GradeService;
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
* <b><code>GradeServiceImplTest</code></b>
* <p/>
* Grade的具体实现
* <p/>
* <b>Creation Time:</b> Thu Oct 05 16:50:41 CST 2017.
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
public class GradeServiceImplTest {
    /**
     * JdbcTemplate
     */
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    GradeService gradeService;

    private static String id = "999999";

    @Before
    public void init() throws Exception {
        GradeView gradeView =new GradeView();
        //gradeView.setUserName("lvxin_test@andy.com");
        //gradeView.setPassword("123");
        ReflectUtils.fillModel(gradeView);
        id = gradeService.saveEntity(gradeView);
    }

    @Test
    public void test1SaveGrades() throws Exception {
        GradeView gradeView =new GradeView();
        //gradeView.setUserName("lvxin_test@andy.com");
        //gradeView.setPassword("123");
        ReflectUtils.fillModel(gradeView);
        id = gradeService.saveEntity(gradeView);
    }

    @Test
    public void test2GetGrades() throws Exception {
        GradeView gradeView = gradeService.getEntity(Long.valueOf(id));
        //Assert.assertEquals("lvxin_test@andy.com", gradeView.getUserName());
    }

    @Test
    public void test3UpdateGrades() throws Exception {
        GradeView gradeView = new GradeView();
        gradeView.setId(Long.valueOf(id));
        //gradeView.setUserName("lvxin_test1@andy.com");
        gradeService.updateEntity(gradeView);

        gradeView = gradeService.getEntity(Long.valueOf(id));
        //Assert.assertEquals("lvxin_test1@andy.com", gradeView.getUserName());


    }

    @Test
    public void test4getAllGrades() throws Exception {
        int pageNumber = 0;
        int pageSize = 10;
        GradeView gradeView = new GradeView();
        ReflectUtils.fillModelByDefault(gradeView);
        Page<GradeView>  gradeViews =  gradeService.getEntitiesByParms(gradeView,pageNumber,pageSize);
    }

    @Test
    public void test5DeleteGrades() throws Exception {
        gradeService.deleteEntities(id);
    }

    @Test
    public void test6DeleteGradesById() throws Exception {
        gradeService.deleteEntity(Long.valueOf(id));
    }
}
