package com.showtime.service.sysuser;

import com.showtime.model.entity.sysuser.SysUser;
import com.showtime.model.view.sysuser.SysUserView;
import com.showtime.service.base.BaseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SysUserService extends BaseService<SysUserView> {

    /**
     * 查询部分字段样例，无实际调用
     * @return 用户的用户名和密码列表
     */
    public List<SysUser> getSysUserUserNameAndPaswordList();

    public void test();

    public SysUserView getEntity(String userName);
}
