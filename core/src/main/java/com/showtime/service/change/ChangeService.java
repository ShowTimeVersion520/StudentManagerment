package com.showtime.service.change;

import com.showtime.model.view.change.ChangeView;
import com.showtime.service.base.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import java.util.List;

/**
* <b><code>Change</code></b>
* <p/>
* Change的具体实现
* <p/>
* <b>Creation Time:</b> Fri Oct 06 20:01:08 CST 2017.
*
* @author qinJianLun
* @version 1.0.0
 * @since andy-core 1.0.0
 */
@Service
public interface ChangeService extends BaseService<ChangeView> {

    /**
    * 通过 keyword 获取变动分页
    * @param currentPage
    * @param pageSize
    * @return
    */
    Page<ChangeView> getChangeByKeyword(String keyword, int currentPage, int pageSize);

    ChangeView getByChangeName(String changeName);

}
