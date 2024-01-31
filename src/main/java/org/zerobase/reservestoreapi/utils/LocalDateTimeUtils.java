package org.zerobase.reservestoreapi.utils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class LocalDateTimeUtils {
    public static String dateTimeToString(LocalDateTime localDateTime) {
        return localDateTime.format(
                DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        );
    }

    public static long diffMinutes(LocalTime fromTime, LocalTime toTime) {
        return fromTime.until(toTime, ChronoUnit.MINUTES);
    }
}
