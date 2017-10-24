package com.showtime.service.sysauthority;

import com.showtime.model.view.sysauthority.SysAuthorityView;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

@ContextConfiguration(locations = "classpath:application-context-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@Rollback(false)
@Transactional
@Ignore
public class SysAuthorityServiceImplTest {
    /**
     * JdbcTemplate
     */
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    SysAuthorityService sysAuthorityService;

    private static String id = "999999";

    @Test
    public void test1SaveEntity() throws Exception {
        SysAuthorityView sysAuthorityView =new SysAuthorityView();
        sysAuthorityView.setName("LVXIN_TEST");
        sysAuthorityView.setDescription("描述");

        id = sysAuthorityService.saveEntity(sysAuthorityView);
    }

    @Test
    public void test2GetEntity() throws Exception {
        SysAuthorityView sysAuthorityView = sysAuthorityService.getEntity(Long.valueOf(id));
        Assert.assertEquals("LVXIN_TEST", sysAuthorityView.getName());
    }

    @Test
    public void test3UpdateEntity() throws Exception {
        SysAuthorityView sysAuthorityView = sysAuthorityService.getEntity(Long.valueOf(id));
        sysAuthorityView.setName("LVXIN_TEST1");
//        sysAuthorityView.setDescription("描述");
        sysAuthorityService.updateEntity(sysAuthorityView);
        SysAuthorityView sysAuthorityView1 = sysAuthorityService.getEntity(Long.valueOf(id));
        Assert.assertEquals("LVXIN_TEST1", sysAuthorityView1.getName());
        Assert.assertEquals("描述", sysAuthorityView1.getDescription());
    }

    @Test
    public void test4DeleteEntity() throws Exception {
        sysAuthorityService.deleteEntity(Long.valueOf(id));
    }

//    @Test
//    public void test9DeleteUsers() throws Exception {
//        sysAuthorityService.deleteEntities("6,7,8,9");
//    }

    @Test
    public void test9GetEntities() throws Exception {
//        Page<SysAuthorityView> sysAuthorityViews = sysAuthorityService.getEntities(1,100);
//        System.out.println(sysAuthorityViews.toString() + " | " + sysAuthorityViews.getTotalElements());
//
//        sysAuthorityService.getSysAuthorityUserNameAndPaswordList();

//
//        SysAuthorityView sysAuthorityView = new SysAuthorityView();
//        sysAuthorityView.setName("");
//        sysAuthorityView.setEnabled(Integer.MIN_VALUE);
//
//        Page<SysAuthorityView> sysAuthorityViews = sysAuthorityService
//                .getEntitiesByParms(sysAuthorityView, 0, 1000);
//        System.out.println("TEST: " + sysAuthorityViews.toString());

//        sysAuthorityService.test();
//        SysAuthorityView sysAuthorityView = sysAuthorityService.getEntity(1);
//
//        System.out.println(sysAuthorityView.toString());
    }
}
