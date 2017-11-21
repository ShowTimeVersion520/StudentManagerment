package com.showtime.service.student;

import com.showtime.model.view.student.ClassNameView;
import com.showtime.service.student.ClassNameService;
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
* <b><code>ClassNameServiceImplTest</code></b>
* <p/>
* ClassName的具体实现
* <p/>
* <b>Creation Time:</b> Thu Oct 05 16:41:20 CST 2017.
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
public class ClassNameServiceImplTest {
    /**
     * JdbcTemplate
     */
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    ClassNameService classNameService;

    private static String id = "999999";

    @Before
    public void init() throws Exception {
        ClassNameView classNameView =new ClassNameView();
        //classNameView.setUserName("lvxin_test@andy.com");
        //classNameView.setPassword("123");
        ReflectUtils.fillModel(classNameView);
        id = classNameService.saveEntity(classNameView);
    }


    @Test
    public void test2GetClassNames() throws Exception {
        ClassNameView classNameView = classNameService.getEntity(Long.valueOf(id));
        //Assert.assertEquals("lvxin_test@andy.com", classNameView.getUserName());
    }

    @Test
    public void test3UpdateClassNames() throws Exception {
        ClassNameView classNameView = new ClassNameView();
        classNameView.setId(Long.valueOf(id));
        //classNameView.setUserName("lvxin_test1@andy.com");
        classNameService.updateEntity(classNameView);
//SSSSSaAASSS
        classNameView = classNameService.getEntity(Long.valueOf(id));
        //Assert.assertEquals("lvxin_test1@andy.com", classNameView.getUserName());


    }

    @Test
    public void test4getAllClassNames() throws Exception {
        int pageNumber = 0;
        int pageSize = 10;
        ClassNameView classNameView = new ClassNameView();
        ReflectUtils.fillModelByDefault(classNameView);
        Page<ClassNameView>  classNameViews =  classNameService.getEntitiesByParms(classNameView,pageNumber,pageSize);
    }

    @Test
    public void test5DeleteClassNames() throws Exception {
        classNameService.deleteEntities(id);
    }

    @Test
    public void test6DeleteClassNamesById() throws Exception {
        classNameService.deleteEntity(Long.valueOf(id));
    }
}
