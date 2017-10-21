package com.showtime.service.sc;

import com.showtime.model.view.sc.AvgFractionView;
import com.showtime.service.sc.AvgFractionService;
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
* <b><code>AvgFractionServiceImplTest</code></b>
* <p/>
* AvgFraction的具体实现
* <p/>
* <b>Creation Time:</b> Sat Oct 07 18:18:43 CST 2017.
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
public class AvgFractionServiceImplTest {
    /**
     * JdbcTemplate
     */
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    AvgFractionService avgFractionService;

    private static String id = "999999";

    @Before
    public void init() throws Exception {
        AvgFractionView avgFractionView =new AvgFractionView();
        //avgFractionView.setUserName("lvxin_test@andy.com");
        //avgFractionView.setPassword("123");
        ReflectUtils.fillModel(avgFractionView);
        id = avgFractionService.saveEntity(avgFractionView);
    }

    @Test
    public void test1SaveAvgFractions() throws Exception {
        AvgFractionView avgFractionView =new AvgFractionView();
        //avgFractionView.setUserName("lvxin_test@andy.com");
        //avgFractionView.setPassword("123");
        ReflectUtils.fillModel(avgFractionView);
        id = avgFractionService.saveEntity(avgFractionView);
    }

    @Test
    public void test2GetAvgFractions() throws Exception {
        AvgFractionView avgFractionView = avgFractionService.getEntity(Long.valueOf(id));
        //Assert.assertEquals("lvxin_test@andy.com", avgFractionView.getUserName());
    }

    @Test
    public void test3UpdateAvgFractions() throws Exception {
        AvgFractionView avgFractionView = new AvgFractionView();
        avgFractionView.setId(Long.valueOf(id));
        //avgFractionView.setUserName("lvxin_test1@andy.com");
        avgFractionService.updateEntity(avgFractionView);

        avgFractionView = avgFractionService.getEntity(Long.valueOf(id));
        //Assert.assertEquals("lvxin_test1@andy.com", avgFractionView.getUserName());


    }

    @Test
    public void test4getAllAvgFractions() throws Exception {
        int pageNumber = 0;
        int pageSize = 10;
        AvgFractionView avgFractionView = new AvgFractionView();
        ReflectUtils.fillModelByDefault(avgFractionView);
        Page<AvgFractionView>  avgFractionViews =  avgFractionService.getEntitiesByParms(avgFractionView,pageNumber,pageSize);
    }

    @Test
    public void test5DeleteAvgFractions() throws Exception {
        avgFractionService.deleteEntities(id);
    }

    @Test
    public void test6DeleteAvgFractionsById() throws Exception {
        avgFractionService.deleteEntity(Long.valueOf(id));
    }
}