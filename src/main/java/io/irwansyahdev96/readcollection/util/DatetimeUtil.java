package io.irwansyahdev96.readcollection.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class DatetimeUtil {
    
    public static LocalDate epochMillisToDate(Long epochMilis){
        return Instant.ofEpochMilli(epochMilis)
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    @SuppressWarnings("unchecked")
    public static <T>T epochMillisToDate(Long epochMilis, Class<T> targetType){
        ZonedDateTime atZone = Instant.ofEpochMilli(epochMilis)
                        .atZone(ZoneId.systemDefault());

        if (targetType.isAssignableFrom(LocalDateTime.class)) {
            return (T) atZone.toLocalDateTime();
        } else if (targetType.isAssignableFrom(LocalDate.class)) {
            return (T) atZone.toLocalDate();
        } else if (targetType.isAssignableFrom(ZonedDateTime.class)) {
            return (T) atZone;
        } else if (targetType.isAssignableFrom(Instant.class)) {
            return (T) atZone.toInstant();
        } else if (targetType.isAssignableFrom(Date.class)) {
            return (T) Date.from(atZone.toInstant());
        }

        throw new IllegalArgumentException("Unsupported target type: " + targetType);
    }

    public static Long localDateToEpochMilli(LocalDate date){
        return date.atStartOfDay(ZoneId.systemDefault())
        .toInstant()
        .toEpochMilli();
    }


    public static Long toMillis(int year, int month, int day) {
        LocalDateTime dateTime = LocalDateTime.of(year, month, day, 0, 0, 0);
        return dateTime.atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli();
    }
}
