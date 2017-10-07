package com.showtime.service.sc;

import com.showtime.model.view.sc.AvgFractionView;
import com.showtime.service.base.BaseService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;

import java.util.List;

/**
* <b><code>AvgFraction</code></b>
* <p/>
* AvgFraction的具体实现
* <p/>
* <b>Creation Time:</b> Sat Oct 07 18:18:43 CST 2017.
*
* @author qinJianLun
* @version 1.0.0
 * @since andy-core 1.0.0
 */
@Service
public interface AvgFractionService extends BaseService<AvgFractionView> {

    /**
    * 通过 keyword 获取课程平均成绩分页
    * @param currentPage
    * @param pageSize
    * @return
    */
    Page<AvgFractionView> getAvgFractionByKeyword(String keyword, int currentPage, int pageSize);

}
