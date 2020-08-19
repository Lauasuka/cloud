package io.amoe.cloud.tools;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static java.time.format.DateTimeFormatter.ISO_LOCAL_DATE;

/**
 * @author lauamoe
 * @date 2020/8/25 17:53
 */
public final class DatetimeUtils {
    private static final DateTimeFormatter DEFAULT_DATE_PATTERN = ISO_LOCAL_DATE;
    private static final DateTimeFormatter DEFAULT_DATE_TIME_PATTERN = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static LocalDate getToday() {
        return LocalDate.now();
    }

    public static String getToday(String pattern) {
        if (StringUtils.isBlank(pattern)) {
            return getToday().format(DEFAULT_DATE_PATTERN);
        }
        return getToday().format(DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDate getYesterday() {
        return getToday().minusDays(1);
    }

    public static LocalDate getYesterdayOfDate(String date) {
        return parseDate(date).minusDays(1);
    }

    public static LocalDate getYesterdayOfDate(String dateStr, String pattern) {
        return parseDate(dateStr, pattern).minusDays(1);
    }

    public static LocalDate getTomorrow() {
        return getToday().plusDays(1);
    }

    public static LocalDate getTomorrowOfDate(String date) {
        return parseDate(date).plusDays(1);
    }

    public static LocalDate getTomorrowOfDate(String date, String pattern) {
        if (StringUtils.isBlank(date)) {
            throw new IllegalArgumentException("Argument [date] should not be null or blank!");
        }
        if (StringUtils.isBlank(pattern)) {
            return getTomorrowOfDate(date);
        }
        return parseDate(date, pattern).plusDays(1);
    }

    public static LocalDateTime getDatetimeNow() {
        return LocalDateTime.now();
    }

    public static LocalDate parseDate(String date) {
        return LocalDate.parse(date, DEFAULT_DATE_PATTERN);
    }

    public static LocalDate parseDate(String date, String pattern) {
        return LocalDate.parse(date, DateTimeFormatter.ofPattern(pattern));
    }

    public static LocalDateTime parseDatetime(String date) {
        return LocalDateTime.parse(date, DEFAULT_DATE_TIME_PATTERN);
    }

    public static LocalDateTime parseDatetime(String date, String pattern) {
        if (StringUtils.isBlank(pattern)) {
            return parseDatetime(date);
        }
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern(pattern));
    }

    public static String datetimeToStr(LocalDate datetime) {
        return datetimeToStr(datetime, null);
    }

    public static String datetimeToStr(LocalDate datetime, String pattern) {
        if (StringUtils.isBlank(pattern)) {
            return datetime.format(DEFAULT_DATE_TIME_PATTERN);
        }
        return datetime.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static String dateToStr(LocalDate date) {
        return dateToStr(date, null);
    }

    public static String dateToStr(LocalDate date, String pattern) {
        if (StringUtils.isBlank(pattern)) {
            return date.toString();
        }
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }
}
