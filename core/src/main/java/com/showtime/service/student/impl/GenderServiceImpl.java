package com.showtime.service.student.impl;

import com.showtime.dao.student.GenderDao;
import com.showtime.model.entity.student.Gender;
import com.showtime.model.view.student.GenderView;
import com.showtime.service.commons.utils.ReflectUtils;
import com.showtime.service.commons.utils.CommonUtils;
import com.showtime.service.exception.ApplicationException;
import com.showtime.service.student.GenderService;
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
* <b><code>GenderImpl</code></b>
* <p/>
* Gender的具体实现
* <p/>
* <b>Creation Time:</b> Thu Oct 05 18:32:00 CST 2017.
*
* @author qinJianLun
* @version 1.0.0
* @since andy-core 1.0.0
*/
@Service
public class GenderServiceImpl implements GenderService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(GenderServiceImpl.class);

    @Autowired
    private GenderDao genderDao;

    private BeanCopier viewToDaoCopier = BeanCopier.create(GenderView.class, Gender.class,
            false);

    private BeanCopier daoToViewCopier = BeanCopier.create(Gender.class, GenderView.class,
            false);

    @Override
    public GenderView getEntity(long id) {
        // 获取Entity
        Gender gender = genderDao.getOne(id);
        // 复制Dao层属性到view属性
        GenderView genderView = new GenderView();
        daoToViewCopier.copy(gender, genderView,null);
        return genderView;
    }

    @Override
    public Page<GenderView> getEntities(int currentPage, int pageSize) {
        List<Gender> genders = genderDao.findAll();
        // 设置分页
        Pageable pageable = new PageRequest(currentPage, pageSize);
        // 转换成View对象
        List<GenderView> genderViews = new ArrayList<>();
        for (Gender gender : genders){
            GenderView genderView = new GenderView();
            daoToViewCopier.copy(gender, genderView, null);
            genderViews.add(genderView);
        }
        return new PageImpl(genderViews, pageable, genderViews == null ? 0 : genderViews.size());
    }

    @Override
    public Page<GenderView> getEntitiesByParms(GenderView genderView, int currentPage, int pageSize) {
        Specification<Gender> genderSpecification = new Specification<Gender>() {
            @Override
            public Predicate toPredicate(Root<Gender> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                                // 性别
                if(!"".equals(genderView.getGender())){
                    predicates.add(criteriaBuilder.equal(root.get("gender").as(String.class), genderView.getGender()));
                }
                                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return criteriaQuery.getRestriction();
            }
        };

        // 设置排序
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        // 设置分页
        Pageable pageable = new PageRequest(currentPage, pageSize, sort);

        Page<Gender> genders = genderDao.findAll(genderSpecification, pageable);

        // 转换成View对象
        Converter<Gender, GenderView> c = new Converter<Gender, GenderView>() {
            @Override
            public GenderView convert(Gender gender) {
                GenderView genderView = new GenderView();
                daoToViewCopier.copy(gender, genderView, null);
                return genderView;
            }
        };
        return genders.map(c);

    }

    @Override
    public long getEntitiesCount() {
        return genderDao.count();
    }

    @Override
    public List<GenderView> findAll() {
        List<GenderView> genderViews = new ArrayList<>();
        List<Gender> genders = genderDao.findAll();
        for (Gender gender : genders){
            GenderView genderView = new GenderView();
            daoToViewCopier.copy(gender, genderView, null);
            genderViews.add(genderView);
        }
        return genderViews;
    }

    @Override
    public String saveEntity(GenderView genderView) {
        // 保存的业务逻辑
        Gender gender = new Gender();
        viewToDaoCopier.copy(genderView, gender, null);
        // user数据库映射传给dao进行存储 TODO: 添加Gender相关属性
        //gender.setCreateTime(new Date().getTime());
        //gender.setUpdateTime(new Date().getTime());
        genderDao.save(gender);
        return String.valueOf(gender.getId());
    }

    @Override
    public void deleteEntity(long id) {
        genderDao.delete(id);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= CommonUtils.splitString(ids,
                CommonUtils.COMMA);
        List<Gender> genders = new ArrayList<>();
        for(String entityId : entityIds){
            Gender gender = new Gender();
            gender.setId(Long.valueOf(entityId));
            genders.add(gender);
        }
        genderDao.deleteInBatch(genders);
    }

    @Override
    public void updateEntity(GenderView genderView) {
        Gender gender = new Gender();
        BeanCopier copier = BeanCopier.create(GenderView.class, Gender.class,
                false);
        copier.copy(genderView, gender, null);
        // 获取原有的属性，把变的属性覆盖 TODO: 添加需要更新的字段
        Gender gender1 = genderDao.findOne(gender.getId());
        if(ObjectUtils.isEmpty(gender1)){
            throw new ApplicationException("id not find in database!");
        }
        //gender1.setUserName(gender.getUserName());
        // 判断密码是否改变
        //StandardPasswordEncoder standardPasswordEncoder = new StandardPasswordEncoder();
        //if(!gender.getPassword().equals(gender1.getPassword())){
        //    gender1.setPassword(new StandardPasswordEncoder().encode(gender.getPassword()));
        //}
        // gender1.setCreateTime(gender1.getCreateTime());
        //gender.setUpdateTime(new Date().getTime());
        //gender.setCreateTime(gender1.getCreateTime());
        //gender1.setSysRole(gender.getSysRole());
        ReflectUtils.flushModel(gender,gender1);
        genderDao.save(gender);
    }

    @Override
    public Page<GenderView> getGenderByKeyword(String keyword, int currentPage, int pageSize) {
        Specification<Gender> genderSpecification = new Specification<Gender>() {
            @Override
            public Predicate toPredicate(Root<Gender> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
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

        Page<Gender> genders = genderDao.findAll(genderSpecification, pageable);

        // 转换成View对象
        Converter<Gender, GenderView> c = new Converter<Gender, GenderView>() {
            @Override
            public GenderView convert(Gender gender) {
                GenderView genderView = new GenderView();
                daoToViewCopier.copy(gender, genderView, null);
                return genderView;
            }
        };
        return genders.map(c);
    }
}
