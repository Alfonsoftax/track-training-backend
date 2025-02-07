package com.src.train.track.general.utils;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.Period;
import org.joda.time.PeriodType;
import org.joda.time.ReadableInstant;
import org.joda.time.ReadablePartial;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public final class DateUtil extends DateUtils {

    public static final String DD_MM_YYYY_FORMAT = "dd/MM/yyyy";
    public static final String HH_MM_SS_FORMAT = "HH:mm:ss";
    public static final String HH_MM_SS_SSS_FORMAT = "HH:mm:ss.SSS";
    public static final String DD_MM_YYYY_HH_MM_FORMAT = "dd/MM/yyyy HH:mm";
    public static final String DD_MM_YYYY_HH_MM_SS_FORMAT = "dd/MM/yyyy HH:mm:ss";

    public static final String DDMMYYYY_FORMAT = "ddMMyyyy";
    public static final String YYYYMMDD_FORMAT = "yyyyMMdd";
    public static final String YYMMDD_FORMAT = "yyMMdd";
    public static final String HHMMSS_FORMAT = "HHmmss";
    public static final String YYYYMMDDHHMMSS_FORMAT = "yyyyMMddHHmmss";
    public static final String YYYYMMDD_HHMM_FORMAT = "yyyyMMdd_HHmm";
    public static final String YYYYMMDD_HHMMSS_FORMAT = "yyyyMMdd_HHmmss";

    /**
     * Non instantiable class.
     */
    private DateUtil() {
        super();
    }

    // -----------------------------------------------------------------------
    /**
     * Clone date.
     *
     * @param date
     *            the date
     * @return the date
     */
    public static Date clone(final Date date) {
        if (date == null) {
            return null;
        }
        return new Date(date.getTime());
    }

    /**
     * Format date.
     *
     * @param instant
     *            the instant
     * @return the string
     */
    public static String formatDate(final Object instant) {
        return formatDate(instant, DD_MM_YYYY_FORMAT);
    }

    /**
     * Format time.
     *
     * @param instant
     *            the instant
     * @return the string
     */
    public static String formatTime(final Object instant) {
        return formatDate(instant, HH_MM_SS_FORMAT);
    }

    /**
     * Format date.
     *
     * @param date
     *            the date
     * @param pattern
     *            the pattern
     * @return the string
     */
    public static String formatDate(final Object instant, final String pattern) {
        if (instant == null) {
            return null;
        }
        final DateTimeFormatter dtf = DateTimeFormat.forPattern(pattern);
        if (instant instanceof ReadableInstant) {
            return dtf.print((ReadableInstant) instant);
        } else if (instant instanceof ReadablePartial) {
            return dtf.print((ReadablePartial) instant);
        } else {
            return dtf.print(new DateTime(instant));
        }
    }

    /**
     * Parses the date time.
     *
     * @param text
     *            the text
     * @param pattern
     *            the pattern
     * @return the date time
     */
    public static DateTime parseDateTime(final String text, final String pattern) {
        if (StringUtils.isBlank(text)) {
            return null;
        }
        final DateTimeFormatter dtf = DateTimeFormat.forPattern(pattern);
        return dtf.parseDateTime(text);
    }

    /**
     * Parses the date.
     *
     * @param text
     *            the text
     * @param pattern
     *            the pattern
     * @return the date
     */
    public static Date parseDate(final String text, final String pattern) {
        final DateTime dateTime = parseDateTime(text, pattern);
        if (dateTime == null) {
            return null;
        }
        return dateTime.toDate();
    }

    /**
     * Parses the date.
     *
     * @param text
     *            the text
     * @param pattern
     *            the pattern
     * @param defaultValue
     *            the default value
     * @return the date
     */
    public static Date parseDate(final String text, final String pattern, final Date defaultValue) {
        DateTime dateTime = null;
        try {
            if (text.length() == pattern.length()) {
                dateTime = parseDateTime(text, pattern);
            }
        } catch (final RuntimeException e) {
            // Do nothing
        }
        if (dateTime != null) {
            return dateTime.toDate();
        }
        return defaultValue;
    }

    /**
     * Transform date.
     *
     * @param text
     *            the text
     * @param inputPattern
     *            the input pattern
     * @param outpuPattern
     *            the outpu pattern
     * @return the string
     */
    public static String transformDate(final String text, final String inputPattern, final String outpuPattern) {
        if (text == null) {
            return null;
        }
        return formatDate(DateUtil.parseDate(text, inputPattern), outpuPattern);
    }

    /**
     * Parses the local date.
     *
     * @param text
     *            the text
     * @return the local date
     */
    public static LocalDate parseLocalDate(final String text) {
        if (StringUtils.isBlank(text)) {
            return null;
        }
        final DateTimeFormatter dtf = DateTimeFormat.forPattern(DD_MM_YYYY_FORMAT);
        return dtf.parseLocalDate(text);
    }

    /**
     * Parses the date.
     *
     * @param text
     *            the text
     * @return the date
     */
    public static Date parseDate(final String text) {
        final LocalDate localDate = parseLocalDate(text);
        if (localDate == null) {
            return null;
        }
        return localDate.toDate();
    }

    /**
     * Gets the week.
     *
     * @param instant
     *            the instant
     * @return the week
     */
    public static Integer getWeek(final Object instant) {
        if (instant == null) {
            return null;
        }
        if (instant instanceof ReadableInstant) {
            return ((ReadableInstant) instant).getChronology().weekOfWeekyear()
                    .get(((ReadableInstant) instant).getMillis());
        } else {
            return new DateTime(instant).getWeekOfWeekyear();
        }
    }

    /**
     * Gets the year.
     *
     * @param instant
     *            the instant
     * @return the year
     */
    public static Integer getYear(final Object instant) {
        if (instant == null) {
            return null;
        }
        if (instant instanceof ReadableInstant) {
            return ((ReadableInstant) instant).getChronology().year().get(((ReadableInstant) instant).getMillis());
        } else {
            return new DateTime(instant).getYear();
        }
    }

    /**
     * Gets the start date.
     *
     * @param date
     *            the date
     * @return the start date
     */
    public static Date getStartDate(final Date date) {
        if (date == null) {
            return null;
        }
        final DateTime dateTime = new DateTime(date);
        return dateTime.withTime(0, 0, 0, 0).toDate();
    }

    /**
     * Gets the end date.
     *
     * @param date
     *            the date
     * @return the end date
     */
    public static Date getEndDate(final Date date) {
        if (date == null) {
            return null;
        }
        final DateTime dateTime = new DateTime(date);
        return dateTime.withTime(23, 59, 59, 999).toDate();
    }

    /**
     * Gets the sql date.
     *
     * @param date
     *            the date
     * @return the sql date
     */
    public static Date getSqlDate(final Date date) {
        if (date == null) {
            return null;
        }
        final DateTime dateTime = new DateTime(date);
        return dateTime.withMillisOfSecond(0).toDate();
    }

    /**
     * Gets the period.
     *
     * @param startDate
     *            the start date
     * @param endDate
     *            the end date
     * @param type
     *            the type
     * @return the period
     */
    public static Period getPeriod(final Date startDate, final Date endDate, final PeriodType type) {
        DateTime start = null;
        if (startDate != null) {
            start = new DateTime(startDate);
        }
        DateTime end = null;
        if (endDate != null) {
            end = new DateTime(endDate);
        }
        return new Period(start, end, type);
    }

    /**
     * Reset time.
     *
     * @param date
     *            the date
     * @return the date
     */
    public static Date resetTime(final Date date) {
        return getStartDate(date);
    }

    /**
     * Reset date.
     *
     * @param date
     *            the date
     * @return the date
     */
    public static Date resetDate(final Date date) {
        if (date == null) {
            return null;
        }
        final DateTime dateTime = new DateTime(date);
        return dateTime.withDate(1970, 1, 1).toDate();
    }

}
