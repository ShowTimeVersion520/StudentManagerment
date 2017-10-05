package com.showtime.service.student;

import com.showtime.model.view.student.ScholarshipView;
import com.showtime.service.student.ScholarshipService;
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
* <b><code>ScholarshipServiceImplTest</code></b>
* <p/>
* Scholarship的具体实现
* <p/>
* <b>Creation Time:</b> Thu Oct 05 16:34:21 CST 2017.
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
public class ScholarshipServiceImplTest {
    /**
     * JdbcTemplate
     */
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    ScholarshipService scholarshipService;

    private static String id = "999999";

    @Before
    public void init() throws Exception {
        ScholarshipView scholarshipView =new ScholarshipView();
        //scholarshipView.setUserName("lvxin_test@andy.com");
        //scholarshipView.setPassword("123");
        ReflectUtils.fillModel(scholarshipView);
        id = scholarshipService.saveEntity(scholarshipView);
    }

    @Test
    public void test1SaveScholarships() throws Exception {
        ScholarshipView scholarshipView =new ScholarshipView();
        //scholarshipView.setUserName("lvxin_test@andy.com");
        //scholarshipView.setPassword("123");
        ReflectUtils.fillModel(scholarshipView);
        id = scholarshipService.saveEntity(scholarshipView);
    }

    @Test
    public void test2GetScholarships() throws Exception {
        ScholarshipView scholarshipView = scholarshipService.getEntity(Long.valueOf(id));
        //Assert.assertEquals("lvxin_test@andy.com", scholarshipView.getUserName());
    }

    @Test
    public void test3UpdateScholarships() throws Exception {
        ScholarshipView scholarshipView = new ScholarshipView();
        scholarshipView.setId(Long.valueOf(id));
        //scholarshipView.setUserName("lvxin_test1@andy.com");
        scholarshipService.updateEntity(scholarshipView);

        scholarshipView = scholarshipService.getEntity(Long.valueOf(id));
        //Assert.assertEquals("lvxin_test1@andy.com", scholarshipView.getUserName());


    }

    @Test
    public void test4getAllScholarships() throws Exception {
        int pageNumber = 0;
        int pageSize = 10;
        ScholarshipView scholarshipView = new ScholarshipView();
        ReflectUtils.fillModelByDefault(scholarshipView);
        Page<ScholarshipView>  scholarshipViews =  scholarshipService.getEntitiesByParms(scholarshipView,pageNumber,pageSize);
    }

    @Test
    public void test5DeleteScholarships() throws Exception {
        scholarshipService.deleteEntities(id);
    }

    @Test
    public void test6DeleteScholarshipsById() throws Exception {
        scholarshipService.deleteEntity(Long.valueOf(id));
    }
}
