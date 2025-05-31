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

}
