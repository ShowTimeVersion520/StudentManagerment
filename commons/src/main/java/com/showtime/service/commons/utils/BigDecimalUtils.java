package com.showtime.service.commons.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 金额计算的 util
 */
public class BigDecimalUtils {

    /**
     * 金额相加
     *
     * @param val1  加数1
     * @param val2  加数2
     * @param scale 结果保留的位数
     * @return 相加结果
     */
    public static String add(String val1, String val2, int scale) {
        if (StringUtils.isBlank(val1)) {
            val1 = "0.0";
        }
        if (StringUtils.isBlank(val2)) {
            val2 = "0.0";
        }
        double result = new BigDecimal(val1).add(new BigDecimal(val2)).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        if (scale == 2) {
            // 将结果变成小数点后两位
            DecimalFormat decimalFormat = new DecimalFormat("######0.00");
            return decimalFormat.format(result);
        }
        return result + "";
    }



    /**
     * 金额与数量相乘
     * @param val  金额
     * @param count  数量
     * @param scale 结果保留的位数
     * @return      相加结果
     */
    public static String multiply(String val, Long count, int scale) {
        if (null == val) {
            val = "0.0";
        }
        if (null == count) {
            count = 0L;
        }
        BigDecimal bigDecimal = new BigDecimal(val);
        bigDecimal = BigDecimal.valueOf(count).multiply(bigDecimal);
        Double result = bigDecimal.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        if (scale == 2) {
            // 将结果变成小数点后两位
            DecimalFormat decimalFormat = new DecimalFormat("######0.00");
            return decimalFormat.format(result);
        }
        return result + "";
    }

    /**
     * 相乘
     * @param val1  乘数1
     * @param val2  乘数2
     * @param scale 结果保留的位数
     * @return      相乘结果
     */
    public static String multiply(String val1, String val2, int scale) {
        if (StringUtils.isBlank(val1)) {
            val1 = "0.0";
        }
        if (StringUtils.isBlank(val2)) {
            val2 = "0.0";
        }
//        BigDecimal bigDecimal = new BigDecimal(val);
//        bigDecimal = BigDecimal.valueOf(count).multiply(bigDecimal);
//        Double result = bigDecimal.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        double result = new BigDecimal(val1).multiply(new BigDecimal(val2)).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        if (scale == 2) {
            // 将结果变成小数点后两位
            DecimalFormat decimalFormat = new DecimalFormat("######0.00");
            return decimalFormat.format(result);
        }
        return result + "";
    }

    /**
     * 用于后缀计算
     * 不够位数的在前面补0，保留num的长度位数字
     * @param code
     * @return
     */
    public static String autoGenericCode(String code, int num) {
        String result = "";
        // 保留num的位数
        // 0 代表前面补充0
        // num 代表长度为4
        // d 代表参数为正数型
        result = String.format("%0" + num + "d", Integer.parseInt(code));

        return result;
    }

    /**
     * 金额减
     *
     * @param val1  减数1
     * @param val2  减数2
     * @param scale 结果保留的位数
     * @return 相加结果
     */
    public static String reduce(String val1, String val2, int scale) {
        if (null == val1) {
            val1 = "0.0";
        }
        if (null == val2) {
            val2 = "0.0";
        }
        double result = new BigDecimal(val1).subtract(new BigDecimal(val2)).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        if (scale == 2) {
            // 将结果变成小数点后两位
            DecimalFormat decimalFormat = new DecimalFormat("######0.00");
            return decimalFormat.format(result);
        }
        return result + "";
    }

}
