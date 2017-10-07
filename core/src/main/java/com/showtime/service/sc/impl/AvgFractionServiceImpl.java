package com.showtime.service.sc.impl;

import com.showtime.dao.sc.AvgFractionDao;
import com.showtime.model.entity.sc.AvgFraction;
import com.showtime.model.view.sc.AvgFractionView;
import com.showtime.service.commons.utils.ReflectUtils;
import com.showtime.service.commons.utils.CommonUtils;
import com.showtime.service.exception.ApplicationException;
import com.showtime.service.sc.AvgFractionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
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
* <b><code>AvgFractionImpl</code></b>
* <p/>
* AvgFraction的具体实现
* <p/>
* <b>Creation Time:</b> Sat Oct 07 18:18:43 CST 2017.
*
* @author qinJianLun
* @version 1.0.0
* @since andy-core 1.0.0
*/
@Service
public class AvgFractionServiceImpl implements AvgFractionService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(AvgFractionServiceImpl.class);

    @Autowired
    private AvgFractionDao avgFractionDao;

    private BeanCopier viewToDaoCopier = BeanCopier.create(AvgFractionView.class, AvgFraction.class,
            false);

    private BeanCopier daoToViewCopier = BeanCopier.create(AvgFraction.class, AvgFractionView.class,
            false);

    @Override
    public AvgFractionView getEntity(long id) {
        // 获取Entity
        AvgFraction avgFraction = avgFractionDao.getOne(id);
        // 复制Dao层属性到view属性
        AvgFractionView avgFractionView = new AvgFractionView();
        daoToViewCopier.copy(avgFraction, avgFractionView,null);
        return avgFractionView;
    }

    @Override
    public Page<AvgFractionView> getEntities(int currentPage, int pageSize) {
        List<AvgFraction> avgFractions = avgFractionDao.findAll();
        // 设置分页
        Pageable pageable = new PageRequest(currentPage, pageSize);
        // 转换成View对象
        List<AvgFractionView> avgFractionViews = new ArrayList<>();
        for (AvgFraction avgFraction : avgFractions){
            AvgFractionView avgFractionView = new AvgFractionView();
            daoToViewCopier.copy(avgFraction, avgFractionView, null);
            avgFractionViews.add(avgFractionView);
        }
        return new PageImpl(avgFractionViews, pageable, avgFractionViews == null ? 0 : avgFractionViews.size());
    }

    @Override
    public Page<AvgFractionView> getEntitiesByParms(AvgFractionView avgFractionView, int currentPage, int pageSize) {
        Specification<AvgFraction> avgFractionSpecification = new Specification<AvgFraction>() {
            @Override
            public Predicate toPredicate(Root<AvgFraction> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                                                    // 课程编号
                    if(!"".equals(avgFractionView.getCourseNumber())){
                    predicates.add(criteriaBuilder.equal(root.get("courseNumber").as(String.class), avgFractionView.getCourseNumber()));
                    }
                                                                                                                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return criteriaQuery.getRestriction();
            }
        };

        // 设置排序
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        // 设置分页
        Pageable pageable = new PageRequest(currentPage, pageSize, sort);

        Page<AvgFraction> avgFractions = avgFractionDao.findAll(avgFractionSpecification, pageable);

        // 转换成View对象
        Converter<AvgFraction, AvgFractionView> c = new Converter<AvgFraction, AvgFractionView>() {
            @Override
            public AvgFractionView convert(AvgFraction avgFraction) {
                AvgFractionView avgFractionView = new AvgFractionView();
                daoToViewCopier.copy(avgFraction, avgFractionView, null);
                return avgFractionView;
            }
        };
        return avgFractions.map(c);

    }

    @Override
    public long getEntitiesCount() {
        return avgFractionDao.count();
    }

    @Override
    public List<AvgFractionView> findAll() {
        List<AvgFractionView> avgFractionViews = new ArrayList<>();
        List<AvgFraction> avgFractions = avgFractionDao.findAll();
        for (AvgFraction avgFraction : avgFractions){
            AvgFractionView avgFractionView = new AvgFractionView();
            daoToViewCopier.copy(avgFraction, avgFractionView, null);
            avgFractionViews.add(avgFractionView);
        }
        return avgFractionViews;
    }

    @Override
    public String saveEntity(AvgFractionView avgFractionView) {
        // 保存的业务逻辑
        AvgFraction avgFraction = new AvgFraction();
        viewToDaoCopier.copy(avgFractionView, avgFraction, null);
        // user数据库映射传给dao进行存储 TODO: 添加AvgFraction相关属性
        //avgFraction.setCreateTime(new Date().getTime());
        //avgFraction.setUpdateTime(new Date().getTime());
        avgFractionDao.save(avgFraction);
        return String.valueOf(avgFraction.getId());
    }

    @Override
    public void deleteEntity(long id) {
        avgFractionDao.delete(id);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= CommonUtils.splitString(ids,
                CommonUtils.COMMA);
        List<AvgFraction> avgFractions = new ArrayList<>();
        for(String entityId : entityIds){
            AvgFraction avgFraction = new AvgFraction();
            avgFraction.setId(Long.valueOf(entityId));
            avgFractions.add(avgFraction);
        }
        avgFractionDao.deleteInBatch(avgFractions);
    }

    @Override
    public void updateEntity(AvgFractionView avgFractionView) {
        AvgFraction avgFraction = new AvgFraction();
        BeanCopier copier = BeanCopier.create(AvgFractionView.class, AvgFraction.class,
                false);
        copier.copy(avgFractionView, avgFraction, null);
        // 获取原有的属性，把变的属性覆盖 TODO: 添加需要更新的字段
        AvgFraction avgFraction1 = avgFractionDao.findOne(avgFraction.getId());
        if(ObjectUtils.isEmpty(avgFraction1)){
            throw new ApplicationException("id not find in database!");
        }
        //avgFraction1.setUserName(avgFraction.getUserName());
        // 判断密码是否改变
        //StandardPasswordEncoder standardPasswordEncoder = new StandardPasswordEncoder();
        //if(!avgFraction.getPassword().equals(avgFraction1.getPassword())){
        //    avgFraction1.setPassword(new StandardPasswordEncoder().encode(avgFraction.getPassword()));
        //}
        // avgFraction1.setCreateTime(avgFraction1.getCreateTime());
        //avgFraction.setUpdateTime(new Date().getTime());
        //avgFraction.setCreateTime(avgFraction1.getCreateTime());
        //avgFraction1.setSysRole(avgFraction.getSysRole());
        ReflectUtils.flushModel(avgFraction,avgFraction1);
        avgFractionDao.save(avgFraction);
    }

    @Override
    public Page<AvgFractionView> getAvgFractionByKeyword(String keyword, int currentPage, int pageSize) {
        Specification<AvgFraction> avgFractionSpecification = new Specification<AvgFraction>() {
            @Override
            public Predicate toPredicate(Root<AvgFraction> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                List<Predicate> predicates1 = new ArrayList<>();

                String[] keywords = keyword.split(" ");

                if (!ObjectUtils.isEmpty(keywords)) {
                    for (String word : keywords) {
                        //predicates1.add(criteriaBuilder.like(root.get("userName").as(String.class), "%" + word + "%"));
                    }
                    predicates.add(criteriaBuilder.or(predicates1.toArray(new Predicate[predicates1.size()])));
                }

                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return criteriaQuery.getRestriction();
            }
        };

        // 设置排序
        Sort sort = new Sort(Sort.Direction.DESC, "");

        // 设置分页
        Pageable pageable = new PageRequest(currentPage, pageSize, sort);

        Page<AvgFraction> avgFractions = avgFractionDao.findAll(avgFractionSpecification, pageable);

        // 转换成View对象
        Converter<AvgFraction, AvgFractionView> c = new Converter<AvgFraction, AvgFractionView>() {
            @Override
            public AvgFractionView convert(AvgFraction avgFraction) {
                AvgFractionView avgFractionView = new AvgFractionView();
                daoToViewCopier.copy(avgFraction, avgFractionView, null);
                return avgFractionView;
            }
        };
        return avgFractions.map(c);
    }
}
