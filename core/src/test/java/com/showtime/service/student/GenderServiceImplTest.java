package com.showtime.service.student;

import com.showtime.model.view.student.GenderView;
import com.showtime.service.student.GenderService;
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
* <b><code>GenderServiceImplTest</code></b>
* <p/>
* Gender的具体实现
* <p/>
* <b>Creation Time:</b> Thu Oct 05 18:32:00 CST 2017.
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
public class GenderServiceImplTest {
    /**
     * JdbcTemplate
     */
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    GenderService genderService;

    private static String id = "999999";

    @Before
    public void init() throws Exception {
        GenderView genderView =new GenderView();
        //genderView.setUserName("lvxin_test@andy.com");
        //genderView.setPassword("123");
        ReflectUtils.fillModel(genderView);
        id = genderService.saveEntity(genderView);
    }

    @Test
    public void test1SaveGenders() throws Exception {
        GenderView genderView =new GenderView();
        //genderView.setUserName("lvxin_test@andy.com");
        //genderView.setPassword("123");
        ReflectUtils.fillModel(genderView);
        id = genderService.saveEntity(genderView);
    }

    @Test
    public void test2GetGenders() throws Exception {
        GenderView genderView = genderService.getEntity(Long.valueOf(id));
        //Assert.assertEquals("lvxin_test@andy.com", genderView.getUserName());
    }

    @Test
    public void test3UpdateGenders() throws Exception {
        GenderView genderView = new GenderView();
        genderView.setId(Long.valueOf(id));
        //genderView.setUserName("lvxin_test1@andy.com");
        genderService.updateEntity(genderView);

        genderView = genderService.getEntity(Long.valueOf(id));
        //Assert.assertEquals("lvxin_test1@andy.com", genderView.getUserName());


    }

    @Test
    public void test4getAllGenders() throws Exception {
        int pageNumber = 0;
        int pageSize = 10;
        GenderView genderView = new GenderView();
        ReflectUtils.fillModelByDefault(genderView);
        Page<GenderView>  genderViews =  genderService.getEntitiesByParms(genderView,pageNumber,pageSize);
    }

    @Test
    public void test5DeleteGenders() throws Exception {
        genderService.deleteEntities(id);
    }

    @Test
    public void test6DeleteGendersById() throws Exception {
        genderService.deleteEntity(Long.valueOf(id));
    }
}
