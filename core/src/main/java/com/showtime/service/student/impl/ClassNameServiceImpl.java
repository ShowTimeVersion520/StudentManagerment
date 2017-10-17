package com.showtime.service.student.impl;

import com.showtime.dao.student.ClassNameDao;
import com.showtime.model.entity.student.ClassName;
import com.showtime.model.view.student.ClassNameView;
import com.showtime.service.commons.utils.ReflectUtils;
import com.showtime.service.commons.utils.CommonUtils;
import com.showtime.service.exception.ApplicationException;
import com.showtime.service.student.ClassNameService;
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
* <b><code>ClassNameImpl</code></b>
* <p/>
* ClassName的具体实现
* <p/>
* <b>Creation Time:</b> Thu Oct 05 16:41:20 CST 2017.
*
* @author qinJianLun
* @version 1.0.0
* @since andy-core 1.0.0
*/
@Service
public class ClassNameServiceImpl implements ClassNameService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(ClassNameServiceImpl.class);

    @Autowired
    private ClassNameDao classNameDao;

    private BeanCopier viewToDaoCopier = BeanCopier.create(ClassNameView.class, ClassName.class,
            false);

    private BeanCopier daoToViewCopier = BeanCopier.create(ClassName.class, ClassNameView.class,
            false);

    @Override
    public ClassNameView getEntity(long id) {
        // 获取Entity
        ClassName className = classNameDao.getOne(id);
        // 复制Dao层属性到view属性
        ClassNameView classNameView = new ClassNameView();
        daoToViewCopier.copy(className, classNameView,null);
        return classNameView;
    }

    @Override
    public Page<ClassNameView> getEntities(int currentPage, int pageSize) {
        List<ClassName> classNames = classNameDao.findAll();
        // 设置分页
        Pageable pageable = new PageRequest(currentPage, pageSize);
        // 转换成View对象
        List<ClassNameView> classNameViews = new ArrayList<>();
        for (ClassName className : classNames){
            ClassNameView classNameView = new ClassNameView();
            daoToViewCopier.copy(className, classNameView, null);
            classNameViews.add(classNameView);
        }
        return new PageImpl(classNameViews, pageable, classNameViews == null ? 0 : classNameViews.size());
    }

    @Override
    public Page<ClassNameView> getEntitiesByParms(ClassNameView classNameView, int currentPage, int pageSize) {
        Specification<ClassName> classNameSpecification = new Specification<ClassName>() {
            @Override
            public Predicate toPredicate(Root<ClassName> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                // 年级
                if(!"".equals(classNameView.getGrade())){
                    predicates.add(criteriaBuilder.equal(root.get("grade").as(String.class), classNameView.getGrade()));
                }
                // 班级名称
                if(!"".equals(classNameView.getClassName())){
                    predicates.add(criteriaBuilder.equal(root.get("className").as(String.class), classNameView.getClassName()));
                }
                                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return criteriaQuery.getRestriction();
            }
        };

        // 设置排序
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        // 设置分页
        Pageable pageable = new PageRequest(currentPage, pageSize, sort);

        Page<ClassName> classNames = classNameDao.findAll(classNameSpecification, pageable);

        // 转换成View对象
        Converter<ClassName, ClassNameView> c = new Converter<ClassName, ClassNameView>() {
            @Override
            public ClassNameView convert(ClassName className) {
                ClassNameView classNameView = new ClassNameView();
                daoToViewCopier.copy(className, classNameView, null);
                return classNameView;
            }
        };
        return classNames.map(c);

    }

    @Override
    public long getEntitiesCount() {
        return classNameDao.count();
    }

    @Override
    public List<ClassNameView> findAll() {
        List<ClassNameView> classNameViews = new ArrayList<>();
        List<ClassName> classNames = classNameDao.findAll();
        for (ClassName className : classNames){
            ClassNameView classNameView = new ClassNameView();
            daoToViewCopier.copy(className, classNameView, null);
            classNameViews.add(classNameView);
        }
        return classNameViews;
    }

    @Override
    public String saveEntity(ClassNameView classNameView) {
        // 保存的业务逻辑
        ClassName className = new ClassName();
        viewToDaoCopier.copy(classNameView, className, null);
        // user数据库映射传给dao进行存储 TODO: 添加ClassName相关属性
        //className.setCreateTime(new Date().getTime());
        //className.setUpdateTime(new Date().getTime());
        classNameDao.save(className);
        return String.valueOf(className.getId());
    }

    @Override
    public void deleteEntity(long id) {
        classNameDao.delete(id);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= CommonUtils.splitString(ids,
                CommonUtils.COMMA);
        List<ClassName> classNames = new ArrayList<>();
        for(String entityId : entityIds){
            ClassName className = new ClassName();
            className.setId(Long.valueOf(entityId));
            classNames.add(className);
        }
        classNameDao.deleteInBatch(classNames);
    }

    @Override
    public void updateEntity(ClassNameView classNameView) {
        ClassName className = new ClassName();
        BeanCopier copier = BeanCopier.create(ClassNameView.class, ClassName.class,
                false);
        copier.copy(classNameView, className, null);
        // 获取原有的属性，把变的属性覆盖 TODO: 添加需要更新的字段
        ClassName className1 = classNameDao.findOne(className.getId());
        if(ObjectUtils.isEmpty(className1)){
            throw new ApplicationException("id not find in database!");
        }
        //className1.setUserName(className.getUserName());
        // 判断密码是否改变
        //StandardPasswordEncoder standardPasswordEncoder = new StandardPasswordEncoder();
        //if(!className.getPassword().equals(className1.getPassword())){
        //    className1.setPassword(new StandardPasswordEncoder().encode(className.getPassword()));
        //}
        // className1.setCreateTime(className1.getCreateTime());
        //className.setUpdateTime(new Date().getTime());
        //className.setCreateTime(className1.getCreateTime());
        //className1.setSysRole(className.getSysRole());
        ReflectUtils.flushModel(className,className1);
        classNameDao.save(className);
    }

    @Override
    public Page<ClassNameView> getClassNameByKeyword(String keyword, int currentPage, int pageSize) {
        Specification<ClassName> classNameSpecification = new Specification<ClassName>() {
            @Override
            public Predicate toPredicate(Root<ClassName> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
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

        Page<ClassName> classNames = classNameDao.findAll(classNameSpecification, pageable);

        // 转换成View对象
        Converter<ClassName, ClassNameView> c = new Converter<ClassName, ClassNameView>() {
            @Override
            public ClassNameView convert(ClassName className) {
                ClassNameView classNameView = new ClassNameView();
                daoToViewCopier.copy(className, classNameView, null);
                return classNameView;
            }
        };
        return classNames.map(c);
    }
}
