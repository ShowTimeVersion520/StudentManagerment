package com.showtime.service.banner.impl;

import com.showtime.service.banner.BannerService;
import com.showtime.service.commons.utils.CommonUtils;
import com.showtime.service.exception.ApplicationException;
import com.showtime.dao.banner.BannerDao;

import com.showtime.model.entity.banner.Banner;
import com.showtime.model.view.banner.BannerView;
import org.apache.commons.lang3.StringUtils;
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
import java.util.Date;
import java.util.List;

@Service
public class BannerServiceImpl implements BannerService {

    /**
     * The Constant LOG.
     */
    private static final Logger LOG = LoggerFactory
            .getLogger(BannerServiceImpl.class);

    @Autowired
    private BannerDao bannerDao;

    private BeanCopier viewToDaoCopier = BeanCopier.create(BannerView.class, Banner.class,
            false);

    private BeanCopier daoToViewCopier = BeanCopier.create(Banner.class, BannerView.class,
            false);

    @Override
    public BannerView getEntity(long id) {
        // 获取Entity
        Banner banner = bannerDao.getOne(id);
        // 复制Dao层属性到view属性
        BannerView bannerView = new BannerView();
        daoToViewCopier.copy(banner, bannerView,null);
        return bannerView;
    }

    @Override
    public Page<BannerView> getEntities(int currentPage, int pageSize) {
        List<Banner> banners = bannerDao.findAll();
        // 设置分页
        Pageable pageable = new PageRequest(currentPage, pageSize);
        // 转换成View对象
        List<BannerView> bannerViews = new ArrayList<>();
        for (Banner banner : banners){
            BannerView bannerView = new BannerView();
            daoToViewCopier.copy(banner, bannerView, null);
            bannerViews.add(bannerView);
        }
        return new PageImpl(bannerViews, pageable, bannerViews == null ? 0 : bannerViews.size());
    }

    @Override
    public Page<BannerView> getEntitiesByParms(BannerView bannerView, int currentPage, int pageSize) {
        Specification<Banner> bannerSpecification = new Specification<Banner>() {
            @Override
            public Predicate toPredicate(Root<Banner> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                List<Predicate> predicates = new ArrayList<>();
                // enabled
                if(bannerView.getEnabled() != Integer.MIN_VALUE ){
                    predicates.add(criteriaBuilder.equal(root.get("enabled").as(Integer.class), bannerView.getEnabled()));
                }

                if(StringUtils.isNotBlank(bannerView.getSearch())){
                    Predicate predicate =criteriaBuilder.or(criteriaBuilder.like(root.get("title").as(String.class),  "%" + bannerView.getSearch() + "%"));
                    predicate=criteriaBuilder.or(predicate,criteriaBuilder.like(root.get("subTitle").as(String.class),  "%" + bannerView.getSearch() + "%"));
                    predicate=criteriaBuilder.or(predicate,criteriaBuilder.like(root.get("content").as(String.class),  "%" + bannerView.getSearch() + "%"));
                    predicates.add(predicate);
                }

                criteriaQuery.where(predicates.toArray(new Predicate[predicates.size()]));
                return criteriaQuery.getRestriction();
            }
        };

        // 设置排序,先按权重，再按更新时间
        Sort.Order bySequence = new Sort.Order(Sort.Direction.ASC, "sequence");
        Sort.Order byUpdateTime = new Sort.Order(Sort.Direction.DESC, "updateTime");
        Sort sort = new Sort(bySequence,byUpdateTime);

        // 设置分页
        Pageable pageable = new PageRequest(currentPage, pageSize, sort);

        Page<Banner> banners = bannerDao.findAll(bannerSpecification, pageable);

        // 转换成View对象
        Converter<Banner, BannerView> c = new Converter<Banner, BannerView>() {
            @Override
            public BannerView convert(Banner banner) {
                BannerView bannerView = new BannerView();
                daoToViewCopier.copy(banner, bannerView, null);
                return bannerView;
            }
        };
        return banners.map(c);

    }

    @Override
    public long getEntitiesCount() {
        return bannerDao.count();
    }

    @Override
    public List<BannerView> findAll() {
        List<BannerView> bannerViews = new ArrayList<>();
        List<Banner> banners = bannerDao.findAll();
        for (Banner banner : banners){
            BannerView bannerView = new BannerView();
            daoToViewCopier.copy(banner, bannerView, null);
            bannerViews.add(bannerView);
        }
        return bannerViews;
    }

    @Override
    public String saveEntity(BannerView bannerView) {
        // 保存的业务逻辑
        Banner banner = new Banner();
        viewToDaoCopier.copy(bannerView, banner, null);
        // user数据库映射传给dao进行存储 TODO: 添加Banner相关属性
        banner.setCreateTime(new Date().getTime());
        banner.setUpdateTime(new Date().getTime());
        banner.setEnabled(1);
        bannerDao.save(banner);
        return String.valueOf(banner.getId());
    }

    @Override
    public void deleteEntity(long id) {
        bannerDao.delete(id);
    }

    @Override
    @Transactional(rollbackOn = { Exception.class })
    public void deleteEntities(String ids) {
        String[] entityIds= CommonUtils.splitString(ids,
                CommonUtils.COMMA);
        List<Banner> banners = new ArrayList<>();
        for(String entityId : entityIds){
            Banner banner = new Banner();
            banner.setId(Long.valueOf(entityId));
            banners.add(banner);
        }
        bannerDao.deleteInBatch(banners);
    }

    @Override
    public void updateEntity(BannerView bannerView) {
        Banner banner = new Banner();
        BeanCopier copier = BeanCopier.create(BannerView.class, Banner.class,
                false);
        copier.copy(bannerView, banner, null);
        // 获取原有的属性，把变的属性覆盖 TODO: 添加需要更新的字段
        Banner banner1 = bannerDao.findOne(banner.getId());
        if (ObjectUtils.isEmpty(banner1)) {
            throw new ApplicationException("id not in database!");
        }

        banner.setUpdateTime(new Date().getTime());
        banner.setCreateTime(banner.getCreateTime());
        bannerDao.save(banner);
    }

}
