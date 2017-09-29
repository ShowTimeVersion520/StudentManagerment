package com.showtime.service.banner;

import com.showtime.model.view.banner.BannerView;
import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

@ContextConfiguration(locations = "classpath:application-context-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//@Rollback(false)
@Transactional
public class BannerServiceImplTest {
    /**
     * JdbcTemplate
     */
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    BannerService bannerService;

    private static String id = "999999";

    @Before
    public void initBanner() throws Exception{
        BannerView bannerView =new BannerView();
        bannerView.setContent("内容");
        bannerView.setTitle("广告标题");
        bannerView.setSubTitle("子标题");
        bannerView.setRedirectUrl("RedirectUrl");
        bannerView.setImageUrl("ImageUrl");
        bannerView.setSequence(99);
        id = bannerService.saveEntity(bannerView);
    }
    @Test
    public void test1SaveUser() throws Exception {
        BannerView bannerView =new BannerView();
        bannerView.setContent("内容2");
        bannerView.setTitle("广告标题2");
        bannerView.setSubTitle("子标题2");
        bannerView.setRedirectUrl("RedirectUrl2");
        bannerView.setImageUrl("ImageUrl2");
        bannerView.setSequence(98);
    }

    @Test
    public void test2GetUser() throws Exception {
        BannerView bannerView = bannerService.getEntity(Long.valueOf(id));
        Assert.assertEquals("内容", bannerView.getContent());
    }

    @Test
    public void test3UpdateUser() throws Exception {
        BannerView bannerView = bannerService.getEntity(Long.valueOf(id));
        bannerView.setContent("内容?");
        bannerService.updateEntity(bannerView);

        BannerView bannerView1 = bannerService.getEntity(Long.valueOf(id));
        Assert.assertEquals("内容?", bannerView1.getContent());
    }

    @Test
    public void test4DeleteUser() throws Exception {
        bannerService.deleteEntity(Long.valueOf(id));
    }


}
