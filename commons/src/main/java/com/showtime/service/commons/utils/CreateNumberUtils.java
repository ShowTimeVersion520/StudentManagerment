package com.showtime.service.commons.utils;

import com.showtime.service.commons.constants.GeneratorNumberConstant;

import java.util.Date;

import static com.showtime.service.commons.utils.CommonUtils.SFSTR;
import static com.showtime.service.commons.utils.CommonUtils.dateToString;

/**
 * 创建编号 util
 */
public class CreateNumberUtils {

//    /**
//     * 创建订单号
//     *
//     * @param suffix
//     * @return
//     */
//    public static String createOrderNumber(String suffix) {
//        return OrderConfigConstant.PURCHASE_ORDER_NUMBER_PREFIX + dateToString(new Date(), SFSTR) + suffix;
//    }

    public static String createCourseNumber(String suffix) {
        return GeneratorNumberConstant.COURSE_NUMBER_PREFIX + dateToString(new Date(), SFSTR) + suffix;
    }


}
