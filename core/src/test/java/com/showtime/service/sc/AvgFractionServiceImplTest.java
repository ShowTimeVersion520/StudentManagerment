package com.showtime.service.sc;

import com.showtime.model.view.course.CourseView;
import com.showtime.model.view.sc.AvgFractionView;
import com.showtime.service.course.CourseService;
import com.showtime.service.sc.AvgFractionService;
import org.junit.*;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
//@Ignore
public class AvgFractionServiceImplTest {
    /**
     * JdbcTemplate
     */
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    AvgFractionService avgFractionService;
    @Autowired
    CourseService courseService;


    private static String id = "999999";
    private static String courseId = "999999";

    @Before
    public void init() throws Exception {
//        CourseView courseView =new CourseView();
//        courseView.setLearnHours("64");
//        courseView.setCredit("2.5");
//        courseView.setName("test");
//
//        courseId = courseService.saveEntity(courseView);
//
//        AvgFractionView avgFractionView =new AvgFractionView();
//        avgFractionView.setCourseNumber(courseService.getEntity(Long.valueOf(courseId)).getCourseNumber());
//        avgFractionView.setAvgFraction(new BigDecimal(65.5).setScale(2));
//        id = avgFractionService.saveEntity(avgFractionView);
    }

    @Test
    public void test2GetAvgFractions() throws Exception {
//        AvgFractionView avgFractionView = avgFractionService.getEntity(Long.valueOf(id));
//        Assert.assertEquals(new BigDecimal(65.5).setScale(2), avgFractionView.getAvgFraction());
    }

    @Test
    public void test3UpdateAvgFractions() throws Exception {
//        AvgFractionView avgFractionView = new AvgFractionView();
//        avgFractionView.setId(Long.valueOf(id));
//        avgFractionView.setAvgFraction(new BigDecimal(60));
//        avgFractionService.updateEntity(avgFractionView);
//
//        avgFractionView = avgFractionService.getEntity(Long.valueOf(id));
//        Assert.assertEquals(new BigDecimal(60), avgFractionView.getAvgFraction());
//

    }

    @Test
    public void test4getAllAvgFractions() throws Exception {
//        int pageNumber = 0;
//        int pageSize = 10;
//        AvgFractionView avgFractionView = new AvgFractionView();
//        ReflectUtils.fillModelByDefault(avgFractionView);
//        Page<AvgFractionView>  avgFractionViews =  avgFractionService.getEntitiesByParms(avgFractionView,pageNumber,pageSize);
    }

    @Test
    public void test5DeleteAvgFractions() throws Exception {
        //avgFractionService.deleteEntities(id);
    }

    @Test
    public void test6DeleteAvgFractionsById() throws Exception {
        //avgFractionService.deleteEntity(Long.valueOf(id));
    }
}
