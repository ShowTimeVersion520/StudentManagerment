package com.showtime.service.sysuser;

import com.showtime.model.view.sysuser.SysUserView;
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
public class SysUserServiceImplTest {
    /**
     * JdbcTemplate
     */
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    SysUserService sysUserService;

    private static String id = "999999";

    @Test
    public void test1SaveUser() throws Exception {
        SysUserView sysUserView =new SysUserView();
        sysUserView.setUserName("lvxin_test@torinosrc.com");
        sysUserView.setPassword("123");

//        SysRole sysRole = new SysRole();
//        sysRole.setId(1);
//        sysUserView.setSysRole(sysRole);

        id = sysUserService.saveEntity(sysUserView);
    }

    @Test
    public void test2GetUser() throws Exception {
        SysUserView sysUserView = sysUserService.getEntity(Long.valueOf(id));
        Assert.assertEquals("lvxin_test@torinosrc.com", sysUserView.getUserName());
    }

    @Test
    public void test3UpdateUser() throws Exception {
        SysUserView sysUserView = sysUserService.getEntity(Long.valueOf(id));
        sysUserView.setUserName("lvxin_test1@torinosrc.com");

//        SysRole sysRole = new SysRole();
//        sysRole.setId(2);
//        sysUserView.setSysRole(sysRole);

        sysUserService.updateEntity(sysUserView);
        SysUserView sysUserView1 = sysUserService.getEntity(Long.valueOf(id));
        Assert.assertEquals("lvxin_test1@torinosrc.com", sysUserView1.getUserName());
    }

    @Test
    public void test4DeleteUser() throws Exception {
        sysUserService.deleteEntity(Long.valueOf(id));
    }

//    @Test
//    public void test9DeleteUsers() throws Exception {
//        sysUserService.deleteEntities("6,7,8,9");
//    }

    @Test
    public void test9GetUsers() throws Exception {
//        Page<SysUserView> sysUserViews = sysUserService.getEntities(1,100);
//        System.out.println(sysUserViews.toString() + " | " + sysUserViews.getTotalElements());
//
//        sysUserService.getSysUserUserNameAndPaswordList();


//        SysUserView sysUserView = new SysUserView();
//        sysUserView.setUserName("");
//        sysUserView.setEnabled(Integer.MIN_VALUE);
//
//        Page<SysUserView> sysUserViews = sysUserService
//                .getEntitiesByParms(sysUserView, 0, 1000);
//        System.out.println(sysUserViews.toString());

//        sysUserService.test();

//        SysUserView sysUserView = sysUserService.getEntity("lvxin");
//
//        System.out.println(sysUserView.toString());
    }
}
