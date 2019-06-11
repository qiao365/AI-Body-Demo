package com.jdjr.risk.face.local.demo.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by michael on 18-12-7.
 */

public class TimeUtil {
    public static String getTimeLabel(long timestamp) {
        final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        final String timeLabel = format.format(new Date(timestamp));
        return timeLabel;
    }
    
    
    public static String getDayTimeLabel(long timestamp) {
        final SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss:SSS");
        final String timeLabel = format.format(new Date(timestamp));
        return timeLabel;
    }
    
    
}
