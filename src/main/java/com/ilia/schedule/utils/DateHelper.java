package com.ilia.schedule.utils;

import java.time.LocalDateTime;
import java.time.temporal.ChronoField;
import java.time.temporal.TemporalField;

public class DateHelper {

    public static LocalDateTime firstHourOfDay(LocalDateTime dateTime) {
        return dateTime.withHour(0).withMinute(0);
    }

    public static LocalDateTime lastHourOfDay(LocalDateTime dateTime) {
        return dateTime.withHour(23).withMinute(59);
    }
}
