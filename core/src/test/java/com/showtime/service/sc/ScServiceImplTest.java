package com.showtime.service.sc;

import com.showtime.model.view.sc.ScView;
import com.showtime.service.sc.ScService;
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
* <b><code>ScServiceImplTest</code></b>
* <p/>
* Sc的具体实现
* <p/>
* <b>Creation Time:</b> Sun Oct 01 17:20:57 CST 2017.
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

    private static String id = "999999";

    @Before
    public void init() throws Exception {
        ScView scView =new ScView();
        //scView.setUserName("lvxin_test@andy.com");
        //scView.setPassword("123");
        ReflectUtils.fillModel(scView);
        id = scService.saveEntity(scView);
    }

    @Test
    public void test1SaveScs() throws Exception {
        ScView scView =new ScView();
        //scView.setUserName("lvxin_test@andy.com");
        //scView.setPassword("123");
        ReflectUtils.fillModel(scView);
        id = scService.saveEntity(scView);
    }

    @Test
    public void test2GetScs() throws Exception {
        ScView scView = scService.getEntity(Long.valueOf(id));
        //Assert.assertEquals("lvxin_test@andy.com", scView.getUserName());
    }

    @Test
    public void test3UpdateScs() throws Exception {
        ScView scView = new ScView();
        scView.setId(Long.valueOf(id));
        //scView.setUserName("lvxin_test1@andy.com");
        scService.updateEntity(scView);

        scView = scService.getEntity(Long.valueOf(id));
        //Assert.assertEquals("lvxin_test1@andy.com", scView.getUserName());


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
}
