package com.showtime.service.commons.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CommonUtils {

    public static String SFSTR = "yyyyMMddHHmmss";

    public static String HFSTR = "yyyyMMddHH";

    public static String DFSTR = "yyyy-MM-dd";

    public static String MFSTR = "yyyy-MM-dd HH:mm";

    public static String SSFSTR = "yyyy-MM-dd HH:mm:ss";

    public static String COMMA = ",";

    public static String URL="http://jdxb.torinosrc.com";

    public static String dateToString(Date date, String format) {

        SimpleDateFormat hf = new SimpleDateFormat(format);

        String dateTime = hf.format(date);

        return dateTime;

    }

    public static Date stringToDate(String str, String format) throws ParseException {

        SimpleDateFormat hf = new SimpleDateFormat(format);

        Date date = hf.parse(str);

        return date;

    }

    public static String[] splitString(String str, String split) {
        String[] sourceStrArray = str.split(split);
        return sourceStrArray;
    }

    public static List<String> splitStringToList(String str, String split) {
        String[] sourceStrArray = str.split(split);
        List<String> list=new ArrayList<>();
        for(int i=0;i<sourceStrArray.length;i++){
            list.add(sourceStrArray[i]);
        }
        return list;
    }

}
