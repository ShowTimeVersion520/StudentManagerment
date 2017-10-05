package com.showtime.service.student;

import com.showtime.model.view.student.ScholarshipView;
import com.showtime.service.base.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import java.util.List;

/**
* <b><code>Scholarship</code></b>
* <p/>
* Scholarship的具体实现
* <p/>
* <b>Creation Time:</b> Thu Oct 05 16:34:21 CST 2017.
*
* @author qinJianLun
* @version 1.0.0
 * @since andy-core 1.0.0
 */
@Service
public interface ScholarshipService extends BaseService<ScholarshipView> {

    /**
    * 通过 keyword 获取奖学金分页
    * @param currentPage
    * @param pageSize
    * @return
    */
    Page<ScholarshipView> getScholarshipByKeyword(String keyword, int currentPage, int pageSize);

}
