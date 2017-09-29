package com.showtime.model.view.sysrole;

import com.showtime.model.entity.sysauthority.SysAuthority;
import com.showtime.model.entity.sysrole.SysRole;
import io.swagger.annotations.ApiModel;

import java.io.Serializable;
import java.util.List;

/**
 * <b><code>SysRoleView</code></b>
 * <p/>
 * SysRoleView
 * <p/>
 * <b>Creation Time:</b> 2017/05/17 21:18.
 *
 * @author lvxin
 * @version 1.0.0
 * @since model 1.0.0
 */
@ApiModel
public class SysRoleView extends SysRole implements Serializable {

    /**
     * The constant serialVersionUID.
     */
    private static final long serialVersionUID = 8672084909411877404L;


    private List<SysAuthority> sysAuthoritiesWithRole;

    public List<SysAuthority> getSysAuthoritiesWithRole() {
        return sysAuthoritiesWithRole;
    }

    public void setSysAuthoritiesWithRole(List<SysAuthority> sysAuthoritiesWithRole) {
        this.sysAuthoritiesWithRole = sysAuthoritiesWithRole;
    }
}
