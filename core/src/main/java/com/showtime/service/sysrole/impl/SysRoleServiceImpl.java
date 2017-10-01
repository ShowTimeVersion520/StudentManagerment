package com.showtime.service.sysrole.impl;

import com.showtime.dao.SysAuthorityDao;
import com.showtime.dao.SysRoleDao;
import com.showtime.model.entity.sysauthority.SysAuthority;
import com.showtime.model.entity.sysrole.SysRole;
import com.showtime.model.view.sysrole.SysRoleView;
import com.showtime.service.commons.utils.CommonUtils;
import com.showtime.service.sysrole.SysRoleService;
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
public class SysRoleServiceImpl implements SysRoleService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(SysRoleServiceImpl.class);

    @Autowired
    private SysRoleDao sysRoleDao;

    @Autowired
    private SysAuthorityDao sysAuthorityDao;

    private BeanCopier viewToDaoCopier = BeanCopier.create(SysRoleView.class, SysRole.class,
            false);

    private BeanCopier daoToViewCopier = BeanCopier.create(SysRole.class, SysRoleView.class,
            false);

    @Override
    public SysRoleView getEntity(long id) {
        // 获取所有权限
        List<SysAuthority> sysAuthorities = sysAuthorityDao.findAll();
        // 获取Entity
        SysRole sysRole = sysRoleDao.getOne(id);
        // 复制Dao层属性到view属性
        SysRoleView sysRoleView = new SysRoleView();
        daoToViewCopier.copy(sysRole, sysRoleView,null);
        // 更新用户权限信息，如果有权限，则hasAuthority等于true
        sysRoleView.setSysAuthoritiesWithRole(getAuthoritiesWithRole(sysAuthorities, sysRole.getSysAuthorities()));
        return sysRoleView;
    }

    @Override
    public Page<SysRoleView> getEntities(int currentPage, int pageSize) {
        List<SysRole> sysRoles = sysRoleDao.findAll();

        // 获取所有权限
        List<SysAuthority> sysAuthorities = sysAuthorityDao.findAll();
        // 设置分页
        Pageable pageable = new PageRequest(currentPage, pageSize);
        // 转换成View对象
        List<SysRoleView> sysRoleViews = new ArrayList<>();
        for (SysRole sysRole : sysRoles){
            SysRoleView sysRoleView = new SysRoleView();
            // 复制bean
            daoToViewCopier.copy(sysRole, sysRoleView, null);
            // 更新用户权限信息，如果有权限，则hasAuthority等于true
            sysRoleView.setSysAuthoritiesWithRole(getAuthoritiesWithRole(sysAuthorities, sysRole.getSysAuthorities()));
            sysRoleViews.add(sysRoleView);
        }

        return new PageImpl(sysRoleViews, pageable, sysRoleViews == null ? 0 : sysRoleViews.size());
    }

    /**
     * 更新用户权限信息，如果有权限，则hasAuthority等于true
     *
     * @param totalAuthorities 全部权限
     * @param currentAuthorities 拥有权限
     * @return 全部权限并对拥有权限进行标识
     */
    private List<SysAuthority> getAuthoritiesWithRole(List<SysAuthority> totalAuthorities, List<SysAuthority> currentAuthorities){
        // 新建全部权限实例
        List<SysAuthority> sysAuthorities = new ArrayList<SysAuthority>();

        BeanCopier copier = BeanCopier.create(SysAuthority.class, SysAuthority.class,
                false);
        // 遍历所有权限
        for(SysAuthority totalSysAuthority : totalAuthorities){
            // 默认没有权限
            SysAuthority sysAuthority = new SysAuthority();
            copier.copy(totalSysAuthority, sysAuthority, null);
            sysAuthority.setHasAuthority(false);

            // 遍历拥有权限并对拥有权限进行标识
            for(SysAuthority currentSysAuthority : currentAuthorities){
                if(totalSysAuthority.getId() == currentSysAuthority.getId()){
                    sysAuthority.setHasAuthority(true);
                    break;
                }
            }
            // 添加到所有权限
            sysAuthorities.add(sysAuthority);
        }

        return sysAuthorities;
    }

    @Override
    public Page<SysRoleView> getEntitiesByParms(SysRoleView sysRoleView, int currentPage, int pageSize) {
        // 获取所有权限
        List<SysAuthority> sysAuthorities = sysAuthorityDao.findAll();

        Specification<SysRole> sysRoleSpecification = new Specification<SysRole>() {
            @Override
            public Predicate toPredicate(Root<SysRole> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                // userName
                if(!sysRoleView.getChineseName().equals("")){
                    predicates.add(criteriaBuilder.like(root.get("chineseName").as(String.class), "%" + sysRoleView.getChineseName() + "%"));
                }
                // enabled
                if(sysRoleView.getEnabled() != Integer.MIN_VALUE ){
                    predicates.add(criteriaBuilder.equal(root.get("enabled").as(Integer.class), sysRoleView.getEnabled()));
                }
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return criteriaQuery.getRestriction();
            }
        };

        // 设置排序
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        // 设置分页
        Pageable pageable = new PageRequest(currentPage, pageSize, sort);

        Page<SysRole> sysRoles = sysRoleDao.findAll(sysRoleSpecification, pageable);

        // 转换成View对象
        Converter<SysRole, SysRoleView> c = new Converter<SysRole, SysRoleView>() {
            @Override
            public SysRoleView convert(SysRole sysRole) {
                SysRoleView sysRoleView1 = new SysRoleView();
                daoToViewCopier.copy(sysRole, sysRoleView1, null);
                // 更新用户权限信息，如果有权限，则hasAuthority等于true
                sysRoleView1.setSysAuthoritiesWithRole(getAuthoritiesWithRole(sysAuthorities, sysRole.getSysAuthorities()));
                return sysRoleView1;
            }
        };
        return sysRoles.map(c);

    }

    @Override
    public long getEntitiesCount() {
        return sysRoleDao.count();
    }

    @Override
    public List<SysRoleView> findAll() {
        // 获取所有权限
        List<SysAuthority> sysAuthorities = sysAuthorityDao.findAll();
        List<SysRoleView> sysRoleViews = new ArrayList<>();
        List<SysRole> sysRoles = sysRoleDao.findAll();
        for (SysRole sysRole : sysRoles){
            SysRoleView sysRoleView = new SysRoleView();
            daoToViewCopier.copy(sysRole, sysRoleView, null);
            // 更新用户权限信息，如果有权限，则hasAuthority等于true
            sysRoleView.setSysAuthoritiesWithRole(getAuthoritiesWithRole(sysAuthorities, sysRole.getSysAuthorities()));
            sysRoleViews.add(sysRoleView);
        }
        return sysRoleViews;
    }

    @Override
    public String saveEntity(SysRoleView sysRoleView) {
        // 保存User的业务逻辑
        SysRole sysRole = new SysRole();
        // 复制view层的数据到dao层
        viewToDaoCopier.copy(sysRoleView, sysRole, null);
        // 把选中的权限抽取出来，保存到数据库
        List<SysAuthority> sysAuthorities = new ArrayList<>();
        for(SysAuthority sysAuthority : sysRoleView.getSysAuthoritiesWithRole()){
            if(sysAuthority.isHasAuthority()){
                sysAuthorities.add(sysAuthority);
            }
        }
        sysRole.setSysAuthorities(sysAuthorities);
        // 设置保存时间
//        sysRole.setCreateTime(new Date().getTime());
//        // 设置更新时间
//        sysRole.setUpdateTime(new Date().getTime());
        // 设置记录启用
        sysRole.setEnabled(1);
        // 保存
        sysRoleDao.save(sysRole);

        return String.valueOf(sysRole.getId());
    }

    @Override
    public void deleteEntity(long id) {
        sysRoleDao.delete(id);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= CommonUtils.splitString(ids,
                CommonUtils.COMMA);
        List<SysRole> sysRoles = new ArrayList<>();
        for(String entityId : entityIds){
            SysRole sysRole = new SysRole();
            sysRole.setId(Long.valueOf(entityId));
            sysRoles.add(sysRole);
        }
        sysRoleDao.deleteInBatch(sysRoles);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void updateEntity(SysRoleView sysRoleView) {
        SysRole sysRole = new SysRole();
        BeanCopier copier = BeanCopier.create(SysRoleView.class, SysRole.class,
                false);
        copier.copy(sysRoleView, sysRole, null);

        // 获取原有的属性，把不变的属性覆盖
        SysRole sysRole1 = sysRoleDao.findOne(sysRole.getId());
        // 把选中的权限抽取出来，保存到数据库
        List<SysAuthority> sysAuthorities = new ArrayList<>();
        for(SysAuthority sysAuthority : sysRoleView.getSysAuthoritiesWithRole()){
            System.out.println("isHasAuthority: " + sysAuthority.isHasAuthority());
            if(sysAuthority.isHasAuthority()){
                sysAuthorities.add(sysAuthority);
            }
        }
        System.out.println("sysAuthorities: " + sysAuthorities.size());
        sysRole1.setSysAuthorities(sysAuthorities);
        sysRole1.setEnglishName(sysRole.getEnglishName());
        sysRole1.setChineseName(sysRole.getChineseName());
        sysRole1.setDescription(sysRole.getDescription());
        // sysRole1.setCreateTime(sysRole1.getCreateTime());
//        sysRole1.setUpdateTime(new Date().getTime());
        sysRole1.setEnabled(sysRole.getEnabled());
//        sysRoleDao.save(sysRole1);
    }

}
