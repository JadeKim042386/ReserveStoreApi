package org.zerobase.reservestoreapi.utils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class LocalDateTimeUtils {

    /**  Convert LocalDateTime to yyyy-MM-dd HH:mm:ss (e.g. 2024-02-08 10:00:00) */
    public static String dateTimeToString(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    /** Calculate time(minutes) difference */
    public static long diffMinutes(LocalTime fromTime, LocalTime toTime) {
        return fromTime.until(toTime, ChronoUnit.MINUTES);
    }
}
