package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetween(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static <T> boolean isBetween(T cur, T start, T end) {
        if (cur instanceof LocalDate) {
            return ((LocalDate) cur).compareTo((LocalDate) start) >= 0 && ((LocalDate) cur).compareTo((LocalDate) end) <= 0;
        } else if (cur instanceof LocalTime) {
            return ((LocalTime) cur).compareTo((LocalTime) start) >= 0 && ((LocalTime) cur).compareTo((LocalTime) end) <= 0;
        } else {
            return false;
        }
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}

