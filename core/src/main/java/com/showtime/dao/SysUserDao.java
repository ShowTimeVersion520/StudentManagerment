package com.showtime.dao;


import com.showtime.model.entity.sysuser.SysUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SysUserDao extends JpaRepository<SysUser, Long>, JpaSpecificationExecutor<SysUser> {

    /**
     * 获取用户名和密码
     *
     * @param userName
     * @param password
     * @return List<SysUser>
     */
    @Query("select new SysUser (s.userName, s.password) from SysUser s ")
    List<SysUser> getSysUserUserNameAndPaswordList();

    /**
     *
     * @param userName
     * @return
     */
    SysUser findByUserName(String userName);
}
