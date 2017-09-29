package com.showtime.service.generator;

import com.showtime.model.view.generator.GeneratorView;
import com.showtime.service.commons.utils.ReflectUtils;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@ContextConfiguration(locations = "classpath:application-context-test.xml")
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
//@Rollback(false)
@Transactional
public class GeneratorServiceImplTest {
    /**
     * JdbcTemplate
     */
    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    GeneratorService generatorService;

    private static String id = "999999";
    private static List<Long> ids = new ArrayList<>();

    @Test
    public void test1SaveGenerators() throws Exception {
        for (int i=0;i<10;i++){
            GeneratorView generatorView =new GeneratorView();
            //generatorView.setUserName("lvxin_test@torinosrc.com");
            //generatorView.setPassword("123");
            ReflectUtils.fillModel(generatorView);
            id = generatorService.saveEntity(generatorView);
            System.out.print(id + " ");
            ids.add(Long.valueOf(id));
        }
    }

    @Test
    public void test2GetGenerators() throws Exception {
        for(Long Lid:ids) {
            GeneratorView generatorView = generatorService.getEntity(Long.valueOf(id));
            //Assert.assertEquals("lvxin_test@torinosrc.com", generatorView.getUserName());
        }
    }

    @Test
    public void test3UpdateGenerators() throws Exception {
        //GeneratorView generatorView = generatorService.getEntity(Long.valueOf(id));
        //generatorView.setUserName("lvxin_test1@torinosrc.com");
        //generatorService.updateEntity(generatorView);

        //GeneratorView generatorView1 = generatorService.getEntity(Long.valueOf(id));
        //Assert.assertEquals("lvxin_test1@torinosrc.com", generatorView1.getUserName());

        for (Long Lid: ids) {
            Integer num = 0;
            GeneratorView generatorView = new GeneratorView();
            generatorView.setId(Lid);
            generatorView.setEnabled(num);
            generatorService.updateEntity(generatorView);
            GeneratorView generatorView1 = generatorService.getEntity(Lid);
            Assert.assertEquals(num, generatorView1.getEnabled());
        }
    }

    @Test
    public void test4getAllGenerators() throws Exception {
        int pageNumber = 0;
        int pageSize = 10;
        GeneratorView generatorView = new GeneratorView();
        ReflectUtils.fillModelByDefault(generatorView);
        Page<GeneratorView>  generatorViews =  generatorService.getEntitiesByParms(generatorView,pageNumber,pageSize);
    }

    @Test
    public void test5DeleteGenerators() throws Exception {
        String Lids = String.valueOf(ids.get(0)) +","+  String.valueOf(ids.get(1)) + "," + String.valueOf(ids.get(2));
        System.out.println(Lids);
        generatorService.deleteEntities(Lids);
        System.out.println(ids);
        for (int i = 0; i < 3; i++) {
            ids.remove(0);
        }

        System.out.println(ids);
    }

    @Test
    public void test6DeleteGeneratorsById() throws Exception {
        System.out.println(ids);
        for (Long Lid: ids) {
            generatorService.deleteEntity(Lid);
        }
    }

    @Test
    public void test7clearGenerator() throws Exception {
        generatorService.clearGenerator();
    }
}
