package com.showtime.service.sc.impl;

import com.showtime.dao.sc.ScDao;
import com.showtime.model.entity.sc.Sc;
import com.showtime.model.view.sc.ScView;
import com.showtime.service.commons.utils.ReflectUtils;
import com.showtime.service.commons.utils.CommonUtils;
import com.showtime.service.exception.ApplicationException;
import com.showtime.service.sc.ScService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

/**
* <b><code>ScImpl</code></b>
* <p/>
* Sc的具体实现
* <p/>
* <b>Creation Time:</b> Sun Oct 01 17:20:57 CST 2017.
*
* @author qinJianLun
* @version 1.0.0
* @since andy-core 1.0.0
*/
@Service
public class ScServiceImpl implements ScService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(ScServiceImpl.class);

    @Autowired
    private ScDao scDao;

    private BeanCopier viewToDaoCopier = BeanCopier.create(ScView.class, Sc.class,
            false);

    private BeanCopier daoToViewCopier = BeanCopier.create(Sc.class, ScView.class,
            false);

    @Override
    public ScView getEntity(long id) {
        // 获取Entity
        Sc sc = scDao.getOne(id);
        // 复制Dao层属性到view属性
        ScView scView = new ScView();
        daoToViewCopier.copy(sc, scView,null);
        return scView;
    }

    @Override
    public Page<ScView> getEntities(int currentPage, int pageSize) {
        List<Sc> scs = scDao.findAll();
        // 设置分页
        Pageable pageable = new PageRequest(currentPage, pageSize);
        // 转换成View对象
        List<ScView> scViews = new ArrayList<>();
        for (Sc sc : scs){
            ScView scView = new ScView();
            daoToViewCopier.copy(sc, scView, null);
            scViews.add(scView);
        }
        return new PageImpl(scViews, pageable, scViews == null ? 0 : scViews.size());
    }

    @Override
    public Page<ScView> getEntitiesByParms(ScView scView, int currentPage, int pageSize) {
        Specification<Sc> scSpecification = new Specification<Sc>() {
            @Override
            public Predicate toPredicate(Root<Sc> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                                // 更新时间
                if(!"".equals(scView.getUpdateTime())){
                    predicates.add(criteriaBuilder.equal(root.get("updateTime").as(Long.class), scView.getUpdateTime()));
                }
                                // 学号
                if(!"".equals(scView.getStudentNumber())){
                    predicates.add(criteriaBuilder.equal(root.get("studentNumber").as(String.class), scView.getStudentNumber()));
                }
                                // 课程号
                if(!"".equals(scView.getCourseNumber())){
                    predicates.add(criteriaBuilder.equal(root.get("courseNumber").as(String.class), scView.getCourseNumber()));
                }
                                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return criteriaQuery.getRestriction();
            }
        };

        // 设置排序
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        // 设置分页
        Pageable pageable = new PageRequest(currentPage, pageSize, sort);

        Page<Sc> scs = scDao.findAll(scSpecification, pageable);

        // 转换成View对象
        Converter<Sc, ScView> c = new Converter<Sc, ScView>() {
            @Override
            public ScView convert(Sc sc) {
                ScView scView = new ScView();
                daoToViewCopier.copy(sc, scView, null);
                return scView;
            }
        };
        return scs.map(c);

    }

    @Override
    public long getEntitiesCount() {
        return scDao.count();
    }

    @Override
    public List<ScView> findAll() {
        List<ScView> scViews = new ArrayList<>();
        List<Sc> scs = scDao.findAll();
        for (Sc sc : scs){
            ScView scView = new ScView();
            daoToViewCopier.copy(sc, scView, null);
            scViews.add(scView);
        }
        return scViews;
    }

    @Override
    public String saveEntity(ScView scView) {
        // 保存的业务逻辑
        Sc sc = new Sc();
        viewToDaoCopier.copy(scView, sc, null);
        // user数据库映射传给dao进行存储 TODO: 添加Sc相关属性
        //sc.setCreateTime(new Date().getTime());
        //sc.setUpdateTime(new Date().getTime());
        scDao.save(sc);
        return String.valueOf(sc.getId());
    }

    @Override
    public void deleteEntity(long id) {
        scDao.delete(id);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= CommonUtils.splitString(ids,
                CommonUtils.COMMA);
        List<Sc> scs = new ArrayList<>();
        for(String entityId : entityIds){
            Sc sc = new Sc();
            sc.setId(Long.valueOf(entityId));
            scs.add(sc);
        }
        scDao.deleteInBatch(scs);
    }

    @Override
    public void updateEntity(ScView scView) {
        Sc sc = new Sc();
        BeanCopier copier = BeanCopier.create(ScView.class, Sc.class,
                false);
        copier.copy(scView, sc, null);
        // 获取原有的属性，把变的属性覆盖 TODO: 添加需要更新的字段
        Sc sc1 = scDao.findOne(sc.getId());
        if(ObjectUtils.isEmpty(sc1)){
            throw new ApplicationException("id not find in database!");
        }
        //sc1.setUserName(sc.getUserName());
        // 判断密码是否改变
        //StandardPasswordEncoder standardPasswordEncoder = new StandardPasswordEncoder();
        //if(!sc.getPassword().equals(sc1.getPassword())){
        //    sc1.setPassword(new StandardPasswordEncoder().encode(sc.getPassword()));
        //}
        // sc1.setCreateTime(sc1.getCreateTime());
        //sc.setUpdateTime(new Date().getTime());
        //sc.setCreateTime(sc1.getCreateTime());
        //sc1.setSysRole(sc.getSysRole());
        ReflectUtils.flushModel(sc,sc1);
        scDao.save(sc);
    }

    @Override
    public Page<ScView> getScByKeyword(String keyword, int currentPage, int pageSize) {
        Specification<Sc> scSpecification = new Specification<Sc>() {
            @Override
            public Predicate toPredicate(Root<Sc> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
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

        Page<Sc> scs = scDao.findAll(scSpecification, pageable);

        // 转换成View对象
        Converter<Sc, ScView> c = new Converter<Sc, ScView>() {
            @Override
            public ScView convert(Sc sc) {
                ScView scView = new ScView();
                daoToViewCopier.copy(sc, scView, null);
                return scView;
            }
        };
        return scs.map(c);
    }
}
