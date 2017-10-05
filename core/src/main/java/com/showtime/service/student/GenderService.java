package com.showtime.service.student;

import com.showtime.model.view.student.GenderView;
import com.showtime.service.base.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import java.util.List;

/**
* <b><code>Gender</code></b>
* <p/>
* Gender的具体实现
* <p/>
* <b>Creation Time:</b> Thu Oct 05 18:32:00 CST 2017.
*
* @author qinJianLun
* @version 1.0.0
 * @since andy-core 1.0.0
 */
@Service
public interface GenderService extends BaseService<GenderView> {

    /**
    * 通过 keyword 获取性别分页
    * @param currentPage
    * @param pageSize
    * @return
    */
    Page<GenderView> getGenderByKeyword(String keyword, int currentPage, int pageSize);

}
