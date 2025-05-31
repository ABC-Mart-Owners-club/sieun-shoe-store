package org.shoestore;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Util {

    public static LocalDateTime milliToLocalDateTime(long ms) {
        Instant instant = Instant.ofEpochMilli(ms);
        return LocalDateTime.ofInstant(instant, ZoneId.systemDefault());
    }

    public static long localDateTimeToMilli(LocalDateTime time) {
        return time
                .atZone(ZoneId.systemDefault()) // 원하는 시간대 사용
                .toInstant()
                .toEpochMilli();
    }
}
