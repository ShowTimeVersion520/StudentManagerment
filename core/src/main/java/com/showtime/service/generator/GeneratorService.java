package com.showtime.service.generator;

import com.showtime.model.view.generator.GeneratorView;
import com.showtime.service.base.BaseService;
import org.springframework.stereotype.Service;

/**
* <b><code>Generator</code></b>
* <p/>
* Generator的具体实现
* <p/>
* <b>Creation Time:</b> Fri Jul 28 16:32:21 CST 2017.
*
* @author qinJianLun
* @version 1.0.0
 * @since core 1.0.0
 */
@Service
public interface GeneratorService extends BaseService<GeneratorView> {

//    /**
//     * 获取采购单编号后缀
//     */
//    public String getPurchaseNumberSuffix();

    /**
     * 清理Generator表
     */
    public void clearGenerator();

}
