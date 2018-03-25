package com.solory.gankionews.Util;
/*
 *
 * Created by William on 2018/3/9.
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TimeParse {
    private TimeParse() {
    }

    public static TimeParse getInstance() {
        return InstanceHolder.instance;
    }

    private final SimpleDateFormat utcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS Z", Locale.CHINA);
    private final SimpleDateFormat defaultFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);

    private static class InstanceHolder {
        private static final TimeParse instance = new TimeParse();
    }

    public String getTime(String UTCString) {
        try {
            UTCString = UTCString.replace("Z", " UTC");
            Date date = utcFormat.parse(UTCString);
            return defaultFormat.format(date);
        } catch (ParseException pe) {
            pe.printStackTrace();
            return null;
        }
    }
}
