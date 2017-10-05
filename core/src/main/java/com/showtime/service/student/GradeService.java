package com.showtime.service.student;

import com.showtime.model.view.student.GradeView;
import com.showtime.service.base.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import java.util.List;

/**
* <b><code>Grade</code></b>
* <p/>
* Grade的具体实现
* <p/>
* <b>Creation Time:</b> Thu Oct 05 16:50:41 CST 2017.
*
* @author qinJianLun
* @version 1.0.0
 * @since andy-core 1.0.0
 */
@Service
public interface GradeService extends BaseService<GradeView> {

    /**
    * 通过 keyword 获取年级分页
    * @param currentPage
    * @param pageSize
    * @return
    */
    Page<GradeView> getGradeByKeyword(String keyword, int currentPage, int pageSize);

}
