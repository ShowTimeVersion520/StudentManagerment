package com.showtime.service.student;

import com.showtime.model.view.student.ClassNameView;
import com.showtime.service.base.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import java.util.List;

/**
* <b><code>ClassName</code></b>
* <p/>
* ClassName的具体实现
* <p/>
* <b>Creation Time:</b> Thu Oct 05 16:41:20 CST 2017.
*
* @author qinJianLun
* @version 1.0.0
 * @since andy-core 1.0.0
 */
@Service
public interface ClassNameService extends BaseService<ClassNameView> {

    /**
    * 通过 keyword 获取班级分页
    * @param currentPage
    * @param pageSize
    * @return
    */
    Page<ClassNameView> getClassNameByKeyword(String keyword, int currentPage, int pageSize);

}
