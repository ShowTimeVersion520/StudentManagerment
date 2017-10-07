package com.showtime.service.generator.impl;

import com.showtime.service.commons.constants.GeneratorNumberConstant;
import com.showtime.service.commons.utils.BigDecimalUtils;
import com.showtime.service.commons.utils.CommonUtils;
import com.showtime.service.generator.GeneratorService;
import com.showtime.dao.generator.GeneratorDao;
import com.showtime.model.entity.generator.Generator;
import com.showtime.model.view.generator.GeneratorView;
import com.showtime.service.commons.utils.ReflectUtils;
import com.showtime.service.exception.ApplicationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* <b><code>GeneratorImpl</code></b>
* <p/>
* Generator的具体实现
* <p/>
* <b>Creation Time:</b> Fri Jul 28 16:32:21 CST 2017.
*
* @author qinJianLun
* @version 1.0.0
* @since core 1.0.0
*/
@Service
public class GeneratorServiceImpl implements GeneratorService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(GeneratorServiceImpl.class);

    @Autowired
    private GeneratorDao generatorDao;

    private BeanCopier viewToDaoCopier = BeanCopier.create(GeneratorView.class, Generator.class,
            false);

    private BeanCopier daoToViewCopier = BeanCopier.create(Generator.class, GeneratorView.class,
            false);

    @Override
    public GeneratorView getEntity(long id) {
        // 获取Entity
        Generator generator = generatorDao.getOne(id);
        // 复制Dao层属性到view属性
        GeneratorView generatorView = new GeneratorView();
        daoToViewCopier.copy(generator, generatorView,null);
        return generatorView;
    }

    @Override
    public Page<GeneratorView> getEntities(int currentPage, int pageSize) {
        List<Generator> generators = generatorDao.findAll();
        // 设置分页
        Pageable pageable = new PageRequest(currentPage, pageSize);
        // 转换成View对象
        List<GeneratorView> generatorViews = new ArrayList<>();
        for (Generator generator : generators){
            GeneratorView generatorView = new GeneratorView();
            daoToViewCopier.copy(generator, generatorView, null);
            generatorViews.add(generatorView);
        }
        return new PageImpl(generatorViews, pageable, generatorViews == null ? 0 : generatorViews.size());
    }

    @Override
    public Page<GeneratorView> getEntitiesByParms(GeneratorView generatorView, int currentPage, int pageSize) {
        Specification<Generator> generatorSpecification = new Specification<Generator>() {
            @Override
            public Predicate toPredicate(Root<Generator> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();

                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return criteriaQuery.getRestriction();
            }
        };

        // 设置排序
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        // 设置分页
        Pageable pageable = new PageRequest(currentPage, pageSize, sort);

        Page<Generator> generators = generatorDao.findAll(generatorSpecification, pageable);

        // 转换成View对象
        Converter<Generator, GeneratorView> c = new Converter<Generator, GeneratorView>() {
            @Override
            public GeneratorView convert(Generator generator) {
                GeneratorView generatorView = new GeneratorView();
                daoToViewCopier.copy(generator, generatorView, null);
                return generatorView;
            }
        };
        return generators.map(c);

    }

    @Override
    public long getEntitiesCount() {
        return generatorDao.count();
    }

    @Override
    public List<GeneratorView> findAll() {
        List<GeneratorView> generatorViews = new ArrayList<>();
        List<Generator> generators = generatorDao.findAll();
        for (Generator generator : generators){
            GeneratorView generatorView = new GeneratorView();
            daoToViewCopier.copy(generator, generatorView, null);
            generatorViews.add(generatorView);
        }
        return generatorViews;
    }

    @Override
    public String saveEntity(GeneratorView generatorView) {
        // 保存的业务逻辑
        Generator generator = new Generator();
        viewToDaoCopier.copy(generatorView, generator, null);
        // user数据库映射传给dao进行存储
//        generator.setCreateTime(new Date().getTime());
//        generator.setUpdateTime(new Date().getTime());
//        generator.setEnabled(1);
        generatorDao.save(generator);
        return String.valueOf(generator.getId());
    }

    @Override
    public void deleteEntity(long id) {
        generatorDao.delete(id);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= CommonUtils.splitString(ids,
                CommonUtils.COMMA);
        List<Generator> generators = new ArrayList<>();
        for(String entityId : entityIds){
            Generator generator = new Generator();
            generator.setId(Long.valueOf(entityId));
            generators.add(generator);
        }
        generatorDao.deleteInBatch(generators);
    }

    @Override
    public void updateEntity(GeneratorView generatorView) {
        Generator generator = new Generator();
        BeanCopier copier = BeanCopier.create(GeneratorView.class, Generator.class,
                false);
        copier.copy(generatorView, generator, null);
        // 获取原有的属性，把变的属性覆盖
        Generator generator1 = generatorDao.findOne(generator.getId());
        if(ObjectUtils.isEmpty(generator1)){
            throw new ApplicationException("id not find in database!");
        }
        //generator1.setUserName(generator.getUserName());
        // 判断密码是否改变
        //StandardPasswordEncoder standardPasswordEncoder = new StandardPasswordEncoder();
        //if(!generator.getPassword().equals(generator1.getPassword())){
        //    generator1.setPassword(new StandardPasswordEncoder().encode(generator.getPassword()));
        //}
        // generator1.setCreateTime(generator1.getCreateTime());
        //generator.setUpdateTime(new Date().getTime());
        //generator.setCreateTime(generator1.getCreateTime());
        //generator1.setSysRole(generator.getSysRole());
        ReflectUtils.flushModel(generator,generator1);
        generatorDao.save(generator);
    }

    @Override
    public String getCourseNumberSuffix() {

        Generator generator = generatorDao.getByName(GeneratorNumberConstant.COURSE_NAME_DB);
        Long num = Long.parseLong(generator.getNumberSuffix());
        //num+1后用autoGenericCode调整为6位数后，set进去
        generator.setNumberSuffix(BigDecimalUtils.autoGenericCode(String.valueOf((++num)%1000000), 6));
        return generator.getNumberSuffix();
    }


    //每天凌晨1点清理
    @Scheduled(cron = "0 0 1 * * ?")
    @Transactional(rollbackOn = { Exception.class })
    public void clearGenerator() {
        System.out.println("开始清理Generator表：" + CommonUtils.dateToString(new Date(), CommonUtils.MFSTR));
        generatorDao.clearGenerator("000000");
        System.out.println("结束清理任务：" + CommonUtils.dateToString(new Date(), CommonUtils.MFSTR));
    }
}
