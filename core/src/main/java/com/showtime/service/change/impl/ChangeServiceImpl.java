package com.showtime.service.change.impl;

import com.showtime.dao.change.ChangeDao;
import com.showtime.model.entity.change.Change;
import com.showtime.model.view.change.ChangeView;
import com.showtime.service.commons.utils.ReflectUtils;
import com.showtime.service.commons.utils.CommonUtils;
import com.showtime.service.exception.ApplicationException;
import com.showtime.service.change.ChangeService;
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
* <b><code>ChangeImpl</code></b>
* <p/>
* Change的具体实现
* <p/>
* <b>Creation Time:</b> Tue Oct 03 11:57:14 CST 2017.
*
* @author qinJianLun
* @version 1.0.0
* @since andy-core 1.0.0
*/
@Service
public class ChangeServiceImpl implements ChangeService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(ChangeServiceImpl.class);

    @Autowired
    private ChangeDao changeDao;

    private BeanCopier viewToDaoCopier = BeanCopier.create(ChangeView.class, Change.class,
            false);

    private BeanCopier daoToViewCopier = BeanCopier.create(Change.class, ChangeView.class,
            false);

    @Override
    public ChangeView getEntity(long id) {
        // 获取Entity
        Change change = changeDao.getOne(id);
        // 复制Dao层属性到view属性
        ChangeView changeView = new ChangeView();
        daoToViewCopier.copy(change, changeView,null);
        return changeView;
    }

    @Override
    public Page<ChangeView> getEntities(int currentPage, int pageSize) {
        List<Change> changes = changeDao.findAll();
        // 设置分页
        Pageable pageable = new PageRequest(currentPage, pageSize);
        // 转换成View对象
        List<ChangeView> changeViews = new ArrayList<>();
        for (Change change : changes){
            ChangeView changeView = new ChangeView();
            daoToViewCopier.copy(change, changeView, null);
            changeViews.add(changeView);
        }
        return new PageImpl(changeViews, pageable, changeViews == null ? 0 : changeViews.size());
    }

    @Override
    public Page<ChangeView> getEntitiesByParms(ChangeView changeView, int currentPage, int pageSize) {
        Specification<Change> changeSpecification = new Specification<Change>() {
            @Override
            public Predicate toPredicate(Root<Change> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                                // 成绩变动 0-未变动 1-已变动
                if(!"".equals(changeView.getScChange())){
                    predicates.add(criteriaBuilder.equal(root.get("scChange").as(String.class), changeView.getScChange()));
                }
                                // 学生信息变动 0-未变动 1-已变动
                if(!"".equals(changeView.getStudentChange())){
                    predicates.add(criteriaBuilder.equal(root.get("studentChange").as(String.class), changeView.getStudentChange()));
                }
                                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return criteriaQuery.getRestriction();
            }
        };

        // 设置排序
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        // 设置分页
        Pageable pageable = new PageRequest(currentPage, pageSize, sort);

        Page<Change> changes = changeDao.findAll(changeSpecification, pageable);

        // 转换成View对象
        Converter<Change, ChangeView> c = new Converter<Change, ChangeView>() {
            @Override
            public ChangeView convert(Change change) {
                ChangeView changeView = new ChangeView();
                daoToViewCopier.copy(change, changeView, null);
                return changeView;
            }
        };
        return changes.map(c);

    }

    @Override
    public long getEntitiesCount() {
        return changeDao.count();
    }

    @Override
    public List<ChangeView> findAll() {
        List<ChangeView> changeViews = new ArrayList<>();
        List<Change> changes = changeDao.findAll();
        for (Change change : changes){
            ChangeView changeView = new ChangeView();
            daoToViewCopier.copy(change, changeView, null);
            changeViews.add(changeView);
        }
        return changeViews;
    }

    @Override
    public String saveEntity(ChangeView changeView) {
        // 保存的业务逻辑
        Change change = new Change();
        viewToDaoCopier.copy(changeView, change, null);
        // user数据库映射传给dao进行存储 TODO: 添加Change相关属性
        //change.setCreateTime(new Date().getTime());
        //change.setUpdateTime(new Date().getTime());
        changeDao.save(change);
        return String.valueOf(change.getId());
    }

    @Override
    public void deleteEntity(long id) {
        changeDao.delete(id);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= CommonUtils.splitString(ids,
                CommonUtils.COMMA);
        List<Change> changes = new ArrayList<>();
        for(String entityId : entityIds){
            Change change = new Change();
            change.setId(Long.valueOf(entityId));
            changes.add(change);
        }
        changeDao.deleteInBatch(changes);
    }

    @Override
    public void updateEntity(ChangeView changeView) {
        Change change = new Change();
        BeanCopier copier = BeanCopier.create(ChangeView.class, Change.class,
                false);
        copier.copy(changeView, change, null);
        // 获取原有的属性，把变的属性覆盖 TODO: 添加需要更新的字段
        Change change1 = changeDao.findOne(change.getId());
        if(ObjectUtils.isEmpty(change1)){
            throw new ApplicationException("id not find in database!");
        }
        //change1.setUserName(change.getUserName());
        // 判断密码是否改变
        //StandardPasswordEncoder standardPasswordEncoder = new StandardPasswordEncoder();
        //if(!change.getPassword().equals(change1.getPassword())){
        //    change1.setPassword(new StandardPasswordEncoder().encode(change.getPassword()));
        //}
        // change1.setCreateTime(change1.getCreateTime());
        //change.setUpdateTime(new Date().getTime());
        //change.setCreateTime(change1.getCreateTime());
        //change1.setSysRole(change.getSysRole());
        ReflectUtils.flushModel(change,change1);
        changeDao.save(change);
    }

    @Override
    public Page<ChangeView> getChangeByKeyword(String keyword, int currentPage, int pageSize) {
        Specification<Change> changeSpecification = new Specification<Change>() {
            @Override
            public Predicate toPredicate(Root<Change> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
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

        Page<Change> changes = changeDao.findAll(changeSpecification, pageable);

        // 转换成View对象
        Converter<Change, ChangeView> c = new Converter<Change, ChangeView>() {
            @Override
            public ChangeView convert(Change change) {
                ChangeView changeView = new ChangeView();
                daoToViewCopier.copy(change, changeView, null);
                return changeView;
            }
        };
        return changes.map(c);
    }
}
