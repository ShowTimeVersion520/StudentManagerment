package com.showtime.dao;

import com.showtime.model.entity.sysauthority.SysAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SysAuthorityDao extends JpaRepository<SysAuthority, Long>, JpaSpecificationExecutor<SysAuthority> {

}
