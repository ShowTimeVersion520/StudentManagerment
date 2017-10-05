package com.showtime.service.change;

import com.showtime.model.view.change.ChangeView;
import com.showtime.service.change.ChangeService;
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
* <b><code>ChangeServiceImplTest</code></b>
* <p/>
* Change的具体实现
* <p/>
* <b>Creation Time:</b> Tue Oct 03 11:57:14 CST 2017.
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
public class ChangeServiceImplTest {
    /**
     * JdbcTemplate
     */
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    ChangeService changeService;

    private static String id = "999999";

    @Before
    public void init() throws Exception {
        ChangeView changeView =new ChangeView();
        //changeView.setUserName("lvxin_test@andy.com");
        //changeView.setPassword("123");
        ReflectUtils.fillModel(changeView);
        id = changeService.saveEntity(changeView);
    }

    @Test
    public void test1SaveChanges() throws Exception {
        ChangeView changeView =new ChangeView();
        //changeView.setUserName("lvxin_test@andy.com");
        //changeView.setPassword("123");
        ReflectUtils.fillModel(changeView);
        id = changeService.saveEntity(changeView);
    }

    @Test
    public void test2GetChanges() throws Exception {
        ChangeView changeView = changeService.getEntity(Long.valueOf(id));
        //Assert.assertEquals("lvxin_test@andy.com", changeView.getUserName());
    }

    @Test
    public void test3UpdateChanges() throws Exception {
        ChangeView changeView = new ChangeView();
        changeView.setId(Long.valueOf(id));
        //changeView.setUserName("lvxin_test1@andy.com");
        changeService.updateEntity(changeView);

        changeView = changeService.getEntity(Long.valueOf(id));
        //Assert.assertEquals("lvxin_test1@andy.com", changeView.getUserName());


    }

    @Test
    public void test4getAllChanges() throws Exception {
        int pageNumber = 0;
        int pageSize = 10;
        ChangeView changeView = new ChangeView();
        ReflectUtils.fillModelByDefault(changeView);
        Page<ChangeView>  changeViews =  changeService.getEntitiesByParms(changeView,pageNumber,pageSize);
    }

    @Test
    public void test5DeleteChanges() throws Exception {
        changeService.deleteEntities(id);
    }

    @Test
    public void test6DeleteChangesById() throws Exception {
        changeService.deleteEntity(Long.valueOf(id));
    }
}
