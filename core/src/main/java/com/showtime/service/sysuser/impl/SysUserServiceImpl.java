package com.showtime.service.sysuser.impl;

import com.showtime.service.commons.utils.CommonUtils;
import com.showtime.service.sysuser.SysUserService;
import com.showtime.dao.SysUserDao;
import com.showtime.model.entity.sysuser.SysUser;
import com.showtime.model.view.sysuser.SysUserView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanCopier;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.StandardPasswordEncoder;
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
public class SysUserServiceImpl implements SysUserService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(SysUserServiceImpl.class);

    @Autowired
    private SysUserDao sysUserDao;

    private BeanCopier viewToDaoCopier = BeanCopier.create(SysUserView.class, SysUser.class,
            false);

    private BeanCopier daoToViewCopier = BeanCopier.create(SysUser.class, SysUserView.class,
            false);

    @Override
    public SysUserView getEntity(long id) {
        // 获取Entity
        SysUser sysUser = sysUserDao.getOne(id);
        // 复制Dao层属性到view属性
        SysUserView sysUserView = new SysUserView();
        daoToViewCopier.copy(sysUser, sysUserView,null);
        return sysUserView;
    }

    @Override
    public SysUserView getEntity(String userName) {
        // 获取Entity
        SysUser sysUser = sysUserDao.findByUserName(userName);
        // 复制Dao层属性到view属性
        SysUserView sysUserView = new SysUserView();
        daoToViewCopier.copy(sysUser, sysUserView,null);
        return sysUserView;
    }

    @Override
    public Page<SysUserView> getEntities(int currentPage, int pageSize) {
        List<SysUser> sysUsers = sysUserDao.findAll();
        // 设置分页
        Pageable pageable = new PageRequest(currentPage, pageSize);
        // 转换成View对象
        List<SysUserView> sysUserViews = new ArrayList<>();
        for (SysUser sysUser : sysUsers){
            SysUserView sysUserView = new SysUserView();
            daoToViewCopier.copy(sysUser, sysUserView, null);
            sysUserViews.add(sysUserView);
        }
        return new PageImpl(sysUserViews, pageable, sysUserViews == null ? 0 : sysUserViews.size());
    }

    @Override
    public Page<SysUserView> getEntitiesByParms(SysUserView sysUserView, int currentPage, int pageSize) {
        Specification<SysUser> sysUserSpecification = new Specification<SysUser>() {
            @Override
            public Predicate toPredicate(Root<SysUser> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                // userName
                if(!sysUserView.getUserName().equals("")){
                    predicates.add(criteriaBuilder.like(root.get("userName").as(String.class),  "%" + sysUserView.getUserName() + "%"));
                }
                // enabled
                if(sysUserView.getEnabled() != Integer.MIN_VALUE ){
                    predicates.add(criteriaBuilder.equal(root.get("enabled").as(Integer.class), sysUserView.getEnabled()));
                }
                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return criteriaQuery.getRestriction();
            }
        };

        // 设置排序
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        // 设置分页
        Pageable pageable = new PageRequest(currentPage, pageSize, sort);

        Page<SysUser> sysUsers = sysUserDao.findAll(sysUserSpecification, pageable);

        // 转换成View对象
        Converter<SysUser, SysUserView> c = new Converter<SysUser, SysUserView>() {
            @Override
            public SysUserView convert(SysUser sysUser) {
                SysUserView sysUserView = new SysUserView();
                daoToViewCopier.copy(sysUser, sysUserView, null);
                return sysUserView;
            }
        };
        return sysUsers.map(c);

    }

    @Override
    public long getEntitiesCount() {
        return sysUserDao.count();
    }

    @Override
    public List<SysUserView> findAll() {
        List<SysUserView> sysUserViews = new ArrayList<>();
        List<SysUser> sysUsers = sysUserDao.findAll();
        for (SysUser sysUser : sysUsers){
            SysUserView sysUserView = new SysUserView();
            daoToViewCopier.copy(sysUser, sysUserView, null);
            sysUserViews.add(sysUserView);
        }
        return sysUserViews;
    }

    @Override
    public String saveEntity(SysUserView sysUserView) {
        // 保存User的业务逻辑
        SysUser sysUser = new SysUser();
        viewToDaoCopier.copy(sysUserView, sysUser, null);
        // user数据库映射传给dao进行存储
        sysUser.setPassword(new StandardPasswordEncoder().encode(sysUser.getPassword()));
//        sysUser.setCreateTime(new Date().getTime());
//        sysUser.setUpdateTime(new Date().getTime());
        sysUser.setEnabled(1);
        sysUserDao.save(sysUser);
        return String.valueOf(sysUser.getId());
    }

    @Override
    public void deleteEntity(long id) {
        sysUserDao.delete(id);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= CommonUtils.splitString(ids,
                CommonUtils.COMMA);
        List<SysUser> sysUsers = new ArrayList<>();
        for(String entityId : entityIds){
            SysUser sysUser = new SysUser();
            sysUser.setId(Long.valueOf(entityId));
            sysUsers.add(sysUser);
        }
        sysUserDao.deleteInBatch(sysUsers);
    }

    @Override
    public void updateEntity(SysUserView sysUserView) {
        SysUser sysUser = new SysUser();
        BeanCopier copier = BeanCopier.create(SysUserView.class, SysUser.class,
                false);
        copier.copy(sysUserView, sysUser, null);
        // 获取原有的属性，把不变的属性覆盖
        SysUser sysUser1 = sysUserDao.findOne(sysUser.getId());
        sysUser1.setUserName(sysUser.getUserName());
        // 判断密码是否改变
        StandardPasswordEncoder standardPasswordEncoder = new StandardPasswordEncoder();

        if(!sysUser.getPassword().equals(sysUser1.getPassword())){
            sysUser1.setPassword(new StandardPasswordEncoder().encode(sysUser.getPassword()));
        }
        // sysUser1.setCreateTime(sysUser1.getCreateTime());
//        sysUser1.setUpdateTime(new Date().getTime());
        sysUser1.setEnabled(sysUser.getEnabled());
        sysUser1.setSysRole(sysUser.getSysRole());
        sysUserDao.save(sysUser1);
    }



    /**
     * ============================================以下代码是Demo============================================
     */

    /**
     * 配置Criteria查询的样例
     * @return
     */
    private Specification<SysUser> isEnableSysUserJason() {
        String userName = "lvxin";
        int enabled = 1;
        return new Specification<SysUser>() {
            public Predicate toPredicate(Root<SysUser> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                Predicate isUserJason = cb.equal(root.get("userName").as(String.class), userName);
                Predicate isUserEnabled = cb.equal(root.get("enabled").as(Integer.class), enabled);
                // 2个检索条件同时使用
                query.where(cb.and(isUserJason, isUserEnabled));
                return query.getRestriction();
            }
        };
    }

    /**
     * 结合isEnableSysUserJason（）提供查询名为jason和状态为激活的记录。
     * Criteria查询的样例
     *
     * @param currentPage 当前页数（需要>0）
     * @param pageSize 当前页容量(需要>1)
     * @return 分页结果
     */
    public Page<SysUserView> getEntitiesDemo(int currentPage, int pageSize) {
        List<SysUser> sysUsers = sysUserDao.findAll(isEnableSysUserJason());
        // 设置分页
        Pageable pageable = new PageRequest(currentPage, pageSize);
        // 转换成View对象
        List<SysUserView> sysUserViews = new ArrayList<>();
        for (SysUser sysUser : sysUsers){
            SysUserView sysUserView = new SysUserView();
            daoToViewCopier.copy(sysUser, sysUserView, null);
            sysUserViews.add(sysUserView);
        }
        return new PageImpl(sysUserViews, pageable, sysUserViews == null ? 0 : sysUserViews.size());
    }

    @Override
    public List<SysUser> getSysUserUserNameAndPaswordList(){
        List<SysUser> sysUsers = sysUserDao.getSysUserUserNameAndPaswordList();
        for(SysUser sysUser : sysUsers){
            System.out.println("SysUser: " + sysUser.toString());
        }
        return sysUsers;
    }

    public void test(){

    }


}
