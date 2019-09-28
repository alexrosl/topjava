package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class TimeUtil {
    public static boolean isBetween(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static boolean isBetween(LocalDateTime ldt, LocalTime startTime, LocalTime endTime){
        LocalTime lt = TimeUtil.getLocalTime(ldt);
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static LocalDate getLocalDate(LocalDateTime localDateTime){
        return localDateTime.toLocalDate();
    }

    public static LocalTime getLocalTime(LocalDateTime localDateTime){
        return localDateTime.toLocalTime();
    }
}
