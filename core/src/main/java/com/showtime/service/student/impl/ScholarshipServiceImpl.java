package com.showtime.service.student.impl;

import com.showtime.dao.student.ScholarshipDao;
import com.showtime.model.entity.student.Scholarship;
import com.showtime.model.view.student.ScholarshipView;
import com.showtime.service.commons.utils.ReflectUtils;
import com.showtime.service.commons.utils.CommonUtils;
import com.showtime.service.exception.ApplicationException;
import com.showtime.service.student.ScholarshipService;
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
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
* <b><code>ScholarshipImpl</code></b>
* <p/>
* Scholarship的具体实现
 * 奖学金信息的获取展示
* <p/>
* <b>Creation Time:</b> Thu Oct 05 16:34:21 CST 2017.
*
* @author qinJianLun
* @version 1.0.0
* @since andy-core 1.0.0
*/
@Service
public class ScholarshipServiceImpl implements ScholarshipService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(ScholarshipServiceImpl.class);

    @Autowired
    private ScholarshipDao scholarshipDao;

    private BeanCopier viewToDaoCopier = BeanCopier.create(ScholarshipView.class, Scholarship.class,
            false);

    private BeanCopier daoToViewCopier = BeanCopier.create(Scholarship.class, ScholarshipView.class,
            false);

    @Override
    public ScholarshipView getEntity(long id) {
        // 获取Entity
        Scholarship scholarship = scholarshipDao.getOne(id);
        // 复制Dao层属性到view属性
        ScholarshipView scholarshipView = new ScholarshipView();
        daoToViewCopier.copy(scholarship, scholarshipView,null);
        return scholarshipView;
    }

    @Override
    public Page<ScholarshipView> getEntities(int currentPage, int pageSize) {
        List<Scholarship> scholarships = scholarshipDao.findAll();
        // 设置分页
        Pageable pageable = new PageRequest(currentPage, pageSize);
        // 转换成View对象
        List<ScholarshipView> scholarshipViews = new ArrayList<>();
        for (Scholarship scholarship : scholarships){
            ScholarshipView scholarshipView = new ScholarshipView();
            daoToViewCopier.copy(scholarship, scholarshipView, null);
            scholarshipViews.add(scholarshipView);
        }
        return new PageImpl(scholarshipViews, pageable, scholarshipViews == null ? 0 : scholarshipViews.size());
    }

    @Override
    public Page<ScholarshipView> getEntitiesByParms(ScholarshipView scholarshipView, int currentPage, int pageSize) {
        Specification<Scholarship> scholarshipSpecification = new Specification<Scholarship>() {
            @Override
            public Predicate toPredicate(Root<Scholarship> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                                // 奖学金等级
                if(scholarshipView.getScholarshipLevel() != Integer.MIN_VALUE){
                    predicates.add(criteriaBuilder.equal(root.get("scholarshipLevel").as(Integer.class), scholarshipView.getScholarshipLevel()));
                }
//                                // 奖学金数目
//                if(!"".equals(scholarshipView.getMoney())){
//                    predicates.add(criteriaBuilder.equal(root.get("money").as(BigDecimal.class), scholarshipView.getMoney()));
//                }
                                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return criteriaQuery.getRestriction();
            }
        };

        // 设置排序
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        // 设置分页
        Pageable pageable = new PageRequest(currentPage, pageSize, sort);

        Page<Scholarship> scholarships = scholarshipDao.findAll(scholarshipSpecification, pageable);

        // 转换成View对象
        Converter<Scholarship, ScholarshipView> c = new Converter<Scholarship, ScholarshipView>() {
            @Override
            public ScholarshipView convert(Scholarship scholarship) {
                ScholarshipView scholarshipView = new ScholarshipView();
                daoToViewCopier.copy(scholarship, scholarshipView, null);
                return scholarshipView;
            }
        };
        return scholarships.map(c);

    }

    @Override
    public long getEntitiesCount() {
        return scholarshipDao.count();
    }

    @Override
    public List<ScholarshipView> findAll() {
        List<ScholarshipView> scholarshipViews = new ArrayList<>();
        List<Scholarship> scholarships = scholarshipDao.findAll();
        for (Scholarship scholarship : scholarships){
            ScholarshipView scholarshipView = new ScholarshipView();
            daoToViewCopier.copy(scholarship, scholarshipView, null);
            scholarshipViews.add(scholarshipView);
        }
        return scholarshipViews;
    }

    @Override
    public String saveEntity(ScholarshipView scholarshipView) {
        // 保存的业务逻辑
        Scholarship scholarship = new Scholarship();
        viewToDaoCopier.copy(scholarshipView, scholarship, null);
        // user数据库映射传给dao进行存储 TODO: 添加Scholarship相关属性
        //scholarship.setCreateTime(new Date().getTime());
        //scholarship.setUpdateTime(new Date().getTime());
        scholarshipDao.save(scholarship);
        return String.valueOf(scholarship.getId());
    }

    @Override
    public void deleteEntity(long id) {
        scholarshipDao.delete(id);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= CommonUtils.splitString(ids,
                CommonUtils.COMMA);
        List<Scholarship> scholarships = new ArrayList<>();
        for(String entityId : entityIds){
            Scholarship scholarship = new Scholarship();
            scholarship.setId(Long.valueOf(entityId));
            scholarships.add(scholarship);
        }
        scholarshipDao.deleteInBatch(scholarships);
    }

    @Override
    public void updateEntity(ScholarshipView scholarshipView) {
        Scholarship scholarship = new Scholarship();
        BeanCopier copier = BeanCopier.create(ScholarshipView.class, Scholarship.class,
                false);
        copier.copy(scholarshipView, scholarship, null);
        // 获取原有的属性，把变的属性覆盖 TODO: 添加需要更新的字段
        Scholarship scholarship1 = scholarshipDao.findOne(scholarship.getId());
        if(ObjectUtils.isEmpty(scholarship1)){
            throw new ApplicationException("id not find in database!");
        }
        //scholarship1.setUserName(scholarship.getUserName());
        // 判断密码是否改变
        //StandardPasswordEncoder standardPasswordEncoder = new StandardPasswordEncoder();
        //if(!scholarship.getPassword().equals(scholarship1.getPassword())){
        //    scholarship1.setPassword(new StandardPasswordEncoder().encode(scholarship.getPassword()));
        //}
        // scholarship1.setCreateTime(scholarship1.getCreateTime());
        //scholarship.setUpdateTime(new Date().getTime());
        //scholarship.setCreateTime(scholarship1.getCreateTime());
        //scholarship1.setSysRole(scholarship.getSysRole());
        ReflectUtils.flushModel(scholarship,scholarship1);
        scholarshipDao.save(scholarship);
    }

    @Override
    public Page<ScholarshipView> getScholarshipByKeyword(String keyword, int currentPage, int pageSize) {
        Specification<Scholarship> scholarshipSpecification = new Specification<Scholarship>() {
            @Override
            public Predicate toPredicate(Root<Scholarship> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
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

        Page<Scholarship> scholarships = scholarshipDao.findAll(scholarshipSpecification, pageable);

        // 转换成View对象
        Converter<Scholarship, ScholarshipView> c = new Converter<Scholarship, ScholarshipView>() {
            @Override
            public ScholarshipView convert(Scholarship scholarship) {
                ScholarshipView scholarshipView = new ScholarshipView();
                daoToViewCopier.copy(scholarship, scholarshipView, null);
                return scholarshipView;
            }
        };
        return scholarships.map(c);
    }
}
