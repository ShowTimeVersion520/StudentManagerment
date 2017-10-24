package com.showtime.service.sysrole;

import com.showtime.model.view.sysrole.SysRoleView;
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
@Transactional
@Ignore
public class SysRoleServiceImplTest {
    /**
     * JdbcTemplate
     */
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    SysRoleService sysRoleService;

    private static String id = "999999";

    @Test
    public void test1SaveEntity() throws Exception {
        SysRoleView sysRoleView =new SysRoleView();
        sysRoleView.setEnglishName("LVXIN_TEST");
        sysRoleView.setChineseName("测试");
        sysRoleView.setDescription("描述");

//        List<SysAuthority> sysAuthorities = new ArrayList<>();
//        SysAuthority sysAuthority = new SysAuthority();
//        sysAuthority.setId(1);
//        sysAuthorities.add(sysAuthority);
//        SysAuthority sysAuthority1 = new SysAuthority();
//        sysAuthority1.setId(2);
//        sysAuthorities.add(sysAuthority1);
//        sysRoleView.setSysAuthorities(sysAuthorities);

        id = sysRoleService.saveEntity(sysRoleView);
    }

    @Test
    public void test2GetEntity() throws Exception {
        SysRoleView sysRoleView = sysRoleService.getEntity(Long.valueOf(1));
        Assert.assertEquals("LVXIN_TEST", sysRoleView.getEnglishName());
    }

    @Test
    public void test3UpdateEntity() throws Exception {
//        id = "1";
        SysRoleView sysRoleView = sysRoleService.getEntity(Long.valueOf(id));
        sysRoleView.setEnglishName("LVXIN_TEST1");
        sysRoleView.setChineseName("测试1");
        sysRoleView.setDescription("描述");

//        List<SysAuthority> sysAuthorities = new ArrayList<>();
//        SysAuthority sysAuthority = new SysAuthority();
//        sysAuthority.setId(1);
//        sysAuthority.setHasAuthority(true);
//        sysAuthorities.add(sysAuthority);
//        SysAuthority sysAuthority1 = new SysAuthority();
//        sysAuthority1.setId(2);
//        sysAuthority1.setHasAuthority(false);
//        sysAuthorities.add(sysAuthority1);
//        sysRoleView.setSysAuthoritiesWithRole(sysAuthorities);

        sysRoleService.updateEntity(sysRoleView);
        SysRoleView sysRoleView1 = sysRoleService.getEntity(Long.valueOf(id));
        Assert.assertEquals("LVXIN_TEST1", sysRoleView1.getEnglishName());
        Assert.assertEquals("测试1", sysRoleView1.getChineseName());
        Assert.assertEquals("描述", sysRoleView1.getDescription());



    }

    @Test
    public void test4DeleteEntity() throws Exception {
//        id = "4";
        sysRoleService.deleteEntity(Long.valueOf(id));
    }

//    @Test
//    public void test9DeleteUsers() throws Exception {
//        sysRoleService.deleteEntities("6,7,8,9");
//    }

    @Test
    public void test9GetEntities() throws Exception {
//        Page<SysRoleView> sysRoleViews = sysRoleService.getEntities(1,100);
//        System.out.println(sysRoleViews.toString() + " | " + sysRoleViews.getTotalElements());
//
//        sysRoleService.getSysRoleUserNameAndPaswordList();


//        SysRoleView sysRoleView = new SysRoleView();
//        sysRoleView.setChineseName("");
//        sysRoleView.setEnabled(Integer.MIN_VALUE);
//
//        Page<SysRoleView> sysRoleViews = sysRoleService
//                .getEntitiesByParms(sysRoleView, 0, 1000);
//        System.out.println(sysRoleViews.toString());

//        sysRoleService.test();
//        SysRoleView sysRoleView = sysRoleService.getEntity(1);
//
//        System.out.println(sysRoleView.toString());
    }
}
