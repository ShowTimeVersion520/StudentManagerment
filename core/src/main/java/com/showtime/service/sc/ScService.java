package com.showtime.service.sc;

import com.showtime.model.view.sc.ScView;
import com.showtime.service.base.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import java.util.List;

/**
* <b><code>Sc</code></b>
* <p/>
* Sc的具体实现
* <p/>
* <b>Creation Time:</b> Sun Oct 01 17:20:57 CST 2017.
*
* @author qinJianLun
* @version 1.0.0
 * @since andy-core 1.0.0
 */
@Service
public interface ScService extends BaseService<ScView> {

    /**
    * 通过 keyword 获取成绩分页
    * @param currentPage
    * @param pageSize
    * @return
    */
    Page<ScView> getScByKeyword(String keyword, int currentPage, int pageSize);

}
