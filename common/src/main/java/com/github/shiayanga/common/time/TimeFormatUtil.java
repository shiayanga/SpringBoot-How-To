package com.github.shiayanga.common.time;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.function.Supplier;

/**
 * @author LiYang
 */
public class TimeFormatUtil {
    private static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static ThreadLocal<SimpleDateFormat> DATE_TIME_FORMATTER=ThreadLocal.withInitial((Supplier) () -> new SimpleDateFormat(DATETIME_FORMAT));
    public static String dateToString(Date date){
        return DATE_TIME_FORMATTER.get().format(date);
    }
}
