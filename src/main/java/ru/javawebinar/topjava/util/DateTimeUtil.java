package ru.javawebinar.topjava.util;

import org.springframework.lang.Nullable;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class DateTimeUtil {
//    получаем форматтер
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

//    получает объект LocalDateTime, возвращает Стрингу с отформатированной ДатаТайм
    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }

//    из стринги  делает объект LocalDate
    public static @Nullable LocalDate parseLocalDate(@Nullable String str) {
        return StringUtils.isEmpty(str) ? null : LocalDate.parse(str);
    }

    //    из стринги  делает объект LocalTime
    public static @Nullable LocalTime parseLocalTime(@Nullable String str) {
        return StringUtils.isEmpty(str) ? null : LocalTime.parse(str);
    }

//    принимает LocalDate и на всякий случай default LocalDate, а также LocalDate
//    возвращает LocalDateTime
    public static LocalDateTime createDateTime(@Nullable LocalDate date, LocalDate defaultDate, LocalTime time) {
        return LocalDateTime.of(date != null ? date : defaultDate, time);
    }
}

