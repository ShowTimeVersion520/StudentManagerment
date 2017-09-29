package com.showtime.dao.banner;


import com.showtime.model.entity.banner.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface BannerDao extends JpaRepository<Banner, Long>, JpaSpecificationExecutor<Banner> {

}
