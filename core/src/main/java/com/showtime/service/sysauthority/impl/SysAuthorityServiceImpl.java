package com.showtime.service.sysauthority.impl;

import com.showtime.service.commons.utils.CommonUtils;
import com.showtime.service.sysauthority.SysAuthorityService;
import com.showtime.dao.SysAuthorityDao;
import com.showtime.model.entity.sysauthority.SysAuthority;
import com.showtime.model.view.sysauthority.SysAuthorityView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class SysAuthorityServiceImpl implements SysAuthorityService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(SysAuthorityServiceImpl.class);

    @Autowired
    private SysAuthorityDao sysAuthorityDao;

    private BeanCopier viewToDaoCopier = BeanCopier.create(SysAuthorityView.class, SysAuthority.class,
            false);

    private BeanCopier daoToViewCopier = BeanCopier.create(SysAuthority.class, SysAuthorityView.class,
            false);

    @Override
    public SysAuthorityView getEntity(long id) {
        // 获取Entity
        SysAuthority sysAuthority = sysAuthorityDao.getOne(id);
        // 复制Dao层属性到view属性
        SysAuthorityView sysAuthorityView = new SysAuthorityView();
        daoToViewCopier.copy(sysAuthority, sysAuthorityView,null);
        return sysAuthorityView;
    }

    @Override
    public Page<SysAuthorityView> getEntities(int currentPage, int pageSize) {
        List<SysAuthority> sysAuthoritys = sysAuthorityDao.findAll();
        // 设置分页
        Pageable pageable = new PageRequest(currentPage, pageSize);
        // 转换成View对象
        List<SysAuthorityView> sysAuthorityViews = new ArrayList<>();
        for (SysAuthority sysAuthority : sysAuthoritys){
            SysAuthorityView sysAuthorityView = new SysAuthorityView();
            daoToViewCopier.copy(sysAuthority, sysAuthorityView, null);
            sysAuthorityViews.add(sysAuthorityView);
        }
        return new PageImpl(sysAuthorityViews, pageable, sysAuthorityViews == null ? 0 : sysAuthorityViews.size());
    }

    @Override
    public Page<SysAuthorityView> getEntitiesByParms(SysAuthorityView sysAuthorityView, int currentPage, int pageSize) {
        Specification<SysAuthority> sysAuthoritySpecification = new Specification<SysAuthority>() {
            @Override
            public Predicate toPredicate(Root<SysAuthority> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                // userName
                if(!sysAuthorityView.getName().equals("")){
                    predicates.add(criteriaBuilder.like(root.get("name").as(String.class), "%" + sysAuthorityView.getName() + "%"));
                }
                // enabled
                if(sysAuthorityView.getEnabled() != Integer.MIN_VALUE ){
                    predicates.add(criteriaBuilder.equal(root.get("enabled").as(Integer.class), sysAuthorityView.getEnabled()));
                }
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return criteriaQuery.getRestriction();
            }
        };

        // 设置排序
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        // 设置分页
        Pageable pageable = new PageRequest(currentPage, pageSize, sort);

        Page<SysAuthority> sysAuthorities = sysAuthorityDao.findAll(sysAuthoritySpecification, pageable);

        // 转换成View对象
        Converter<SysAuthority, SysAuthorityView> c = new Converter<SysAuthority, SysAuthorityView>() {
            @Override
            public SysAuthorityView convert(SysAuthority sysAuthority) {
                SysAuthorityView sysAuthorityView = new SysAuthorityView();
                daoToViewCopier.copy(sysAuthority, sysAuthorityView, null);
                return sysAuthorityView;
            }
        };
        return sysAuthorities.map(c);

    }

    @Override
    public long getEntitiesCount() {
        return sysAuthorityDao.count();
    }

    @Override
    public List<SysAuthorityView> findAll() {
        List<SysAuthorityView> sysAuthorityViews = new ArrayList<>();
        List<SysAuthority> sysAuthorities = sysAuthorityDao.findAll();
        for (SysAuthority sysAuthority : sysAuthorities){
            SysAuthorityView sysAuthorityView = new SysAuthorityView();
            daoToViewCopier.copy(sysAuthority, sysAuthorityView, null);
            sysAuthorityViews.add(sysAuthorityView);
        }
        return sysAuthorityViews;
    }

    @Override
    public String saveEntity(SysAuthorityView sysAuthorityView) {
        // 保存User的业务逻辑
        SysAuthority sysAuthority = new SysAuthority();
        viewToDaoCopier.copy(sysAuthorityView, sysAuthority, null);
        // user数据库映射传给dao进行存储
//        sysAuthority.setCreateTime(new Date().getTime());
//        sysAuthority.setUpdateTime(new Date().getTime());
        sysAuthority.setEnabled(1);
        sysAuthorityDao.save(sysAuthority);
        return String.valueOf(sysAuthority.getId());
    }

    @Override
    public void deleteEntity(long id) {
        sysAuthorityDao.delete(id);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= CommonUtils.splitString(ids,
                CommonUtils.COMMA);
        List<SysAuthority> sysAuthorities = new ArrayList<>();
        for(String entityId : entityIds){
            SysAuthority sysAuthority = new SysAuthority();
            sysAuthority.setId(Long.valueOf(entityId));
            sysAuthorities.add(sysAuthority);
        }
        sysAuthorityDao.deleteInBatch(sysAuthorities);
    }

    @Override
    public void updateEntity(SysAuthorityView sysAuthorityView) {
        SysAuthority sysAuthority = new SysAuthority();
        BeanCopier copier = BeanCopier.create(SysAuthorityView.class, SysAuthority.class,
                false);
        copier.copy(sysAuthorityView, sysAuthority, null);
        // 获取原有的属性，把不变的属性覆盖
        SysAuthority sysAuthority1 = sysAuthorityDao.findOne(sysAuthority.getId());
        sysAuthority1.setName(sysAuthority.getName());
        sysAuthority1.setDescription(sysAuthority.getDescription());
        // sysAuthority1.setCreateTime(sysAuthority1.getCreateTime());
//        sysAuthority1.setUpdateTime(new Date().getTime());
        sysAuthority1.setEnabled(sysAuthority.getEnabled());
        sysAuthorityDao.save(sysAuthority1);
    }

}
