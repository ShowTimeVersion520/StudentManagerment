package com.showtime.service.student.impl;

import com.showtime.dao.student.GradeDao;
import com.showtime.model.entity.student.Grade;
import com.showtime.model.view.student.GradeView;
import com.showtime.service.commons.utils.ReflectUtils;
import com.showtime.service.commons.utils.CommonUtils;
import com.showtime.service.exception.ApplicationException;
import com.showtime.service.student.GradeService;
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
* <b><code>GradeImpl</code></b>
* <p/>
* Grade的具体实现
* <p/>
* <b>Creation Time:</b> Thu Oct 05 16:50:41 CST 2017.
*
* @author qinJianLun
* @version 1.0.0
* @since andy-core 1.0.0
*/
@Service
public class GradeServiceImpl implements GradeService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(GradeServiceImpl.class);

    @Autowired
    private GradeDao gradeDao;

    private BeanCopier viewToDaoCopier = BeanCopier.create(GradeView.class, Grade.class,
            false);

    private BeanCopier daoToViewCopier = BeanCopier.create(Grade.class, GradeView.class,
            false);

    @Override
    public GradeView getEntity(long id) {
        // 获取Entity
        Grade grade = gradeDao.getOne(id);
        // 复制Dao层属性到view属性
        GradeView gradeView = new GradeView();
        daoToViewCopier.copy(grade, gradeView,null);
        return gradeView;
    }

    @Override
    public Page<GradeView> getEntities(int currentPage, int pageSize) {
        List<Grade> grades = gradeDao.findAll();
        // 设置分页
        Pageable pageable = new PageRequest(currentPage, pageSize);
        // 转换成View对象
        List<GradeView> gradeViews = new ArrayList<>();
        for (Grade grade : grades){
            GradeView gradeView = new GradeView();
            daoToViewCopier.copy(grade, gradeView, null);
            gradeViews.add(gradeView);
        }
        return new PageImpl(gradeViews, pageable, gradeViews == null ? 0 : gradeViews.size());
    }

    @Override
    public Page<GradeView> getEntitiesByParms(GradeView gradeView, int currentPage, int pageSize) {
        Specification<Grade> gradeSpecification = new Specification<Grade>() {
            @Override
            public Predicate toPredicate(Root<Grade> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                                // 年级
                if(!"".equals(gradeView.getGrade())){
                    predicates.add(criteriaBuilder.equal(root.get("grade").as(String.class), gradeView.getGrade()));
                }
                                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return criteriaQuery.getRestriction();
            }
        };

        // 设置排序
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        // 设置分页
        Pageable pageable = new PageRequest(currentPage, pageSize, sort);

        Page<Grade> grades = gradeDao.findAll(gradeSpecification, pageable);

        // 转换成View对象
        Converter<Grade, GradeView> c = new Converter<Grade, GradeView>() {
            @Override
            public GradeView convert(Grade grade) {
                GradeView gradeView = new GradeView();
                daoToViewCopier.copy(grade, gradeView, null);
                return gradeView;
            }
        };
        return grades.map(c);

    }

    @Override
    public long getEntitiesCount() {
        return gradeDao.count();
    }

    @Override
    public List<GradeView> findAll() {
        List<GradeView> gradeViews = new ArrayList<>();
        List<Grade> grades = gradeDao.findAll();
        for (Grade grade : grades){
            GradeView gradeView = new GradeView();
            daoToViewCopier.copy(grade, gradeView, null);
            gradeViews.add(gradeView);
        }
        return gradeViews;
    }

    @Override
    public String saveEntity(GradeView gradeView) {
        // 保存的业务逻辑
        Grade grade = new Grade();
        viewToDaoCopier.copy(gradeView, grade, null);
        // user数据库映射传给dao进行存储 TODO: 添加Grade相关属性
        //grade.setCreateTime(new Date().getTime());
        //grade.setUpdateTime(new Date().getTime());
        gradeDao.save(grade);
        return String.valueOf(grade.getId());
    }

    @Override
    public void deleteEntity(long id) {
        gradeDao.delete(id);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= CommonUtils.splitString(ids,
                CommonUtils.COMMA);
        List<Grade> grades = new ArrayList<>();
        for(String entityId : entityIds){
            Grade grade = new Grade();
            grade.setId(Long.valueOf(entityId));
            grades.add(grade);
        }
        gradeDao.deleteInBatch(grades);
    }

    @Override
    public void updateEntity(GradeView gradeView) {
        Grade grade = new Grade();
        BeanCopier copier = BeanCopier.create(GradeView.class, Grade.class,
                false);
        copier.copy(gradeView, grade, null);
        // 获取原有的属性，把变的属性覆盖 TODO: 添加需要更新的字段
        Grade grade1 = gradeDao.findOne(grade.getId());
        if(ObjectUtils.isEmpty(grade1)){
            throw new ApplicationException("id not find in database!");
        }
        //grade1.setUserName(grade.getUserName());
        // 判断密码是否改变
        //StandardPasswordEncoder standardPasswordEncoder = new StandardPasswordEncoder();
        //if(!grade.getPassword().equals(grade1.getPassword())){
        //    grade1.setPassword(new StandardPasswordEncoder().encode(grade.getPassword()));
        //}
        // grade1.setCreateTime(grade1.getCreateTime());
        //grade.setUpdateTime(new Date().getTime());
        //grade.setCreateTime(grade1.getCreateTime());
        //grade1.setSysRole(grade.getSysRole());
        ReflectUtils.flushModel(grade,grade1);
        gradeDao.save(grade);
    }

    @Override
    public Page<GradeView> getGradeByKeyword(String keyword, int currentPage, int pageSize) {
        Specification<Grade> gradeSpecification = new Specification<Grade>() {
            @Override
            public Predicate toPredicate(Root<Grade> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
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

        Page<Grade> grades = gradeDao.findAll(gradeSpecification, pageable);

        // 转换成View对象
        Converter<Grade, GradeView> c = new Converter<Grade, GradeView>() {
            @Override
            public GradeView convert(Grade grade) {
                GradeView gradeView = new GradeView();
                daoToViewCopier.copy(grade, gradeView, null);
                return gradeView;
            }
        };
        return grades.map(c);
    }
}
