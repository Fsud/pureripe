package com.fankun.pureRipe.util;

import java.util.Date;

import org.ocpsoft.prettytime.PrettyTime;

/**
 * Created by fankun on 2017/6/19.
 */
public class DateUtil {
    private static PrettyTime prettyTime = new PrettyTime();

    public static String timeShowFriendly(Date date){
        return prettyTime.format(date);
    }
}
