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
* <b>Creation Time:</b> Fri Oct 06 11:02:15 CST 2017.
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

    /**
     * 多条件查询
     * @param fraction
     * @param scView
     * @param pageNumber
     * @param pageSize
     * @return
     */
    Page<ScView> getEntitiesByParms(String sortType, String sortDirection,Integer fraction, ScView scView, int pageNumber, int pageSize);

    //设置排名
    void setRanking();
}
