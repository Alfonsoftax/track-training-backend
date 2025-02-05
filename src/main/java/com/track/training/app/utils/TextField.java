package com.track.training.app.utils;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.LOCAL_VARIABLE })
@Retention(RetentionPolicy.RUNTIME)
public @interface TextField {

    static final String OR = ";";
    public static final String YYYYMMDD_FORMAT = "yyyyMMdd";

    /**
     * Field size.
     */
    int size();

    /**
     * Default value.
     */
    String defaultValue() default EMPTY;

    /**
     * Valid values.
     */
    String validValues() default EMPTY;

    /**
     * Default date format.
     */
    String dateFormat() default YYYYMMDD_FORMAT;

    /**
     * If field has only time part.
     */
    boolean onlyTime() default false;

    /**
     * Precision digits (allowed on string fields).
     */
    int digits() default 0;

    /**
     * Decimal precision on big decimals.
     */
    int fraction() default 2;

    /**
     * Set to <code>false</code> to allow sign on numbers.
     */
    boolean onlyDigits() default true;

    /**
     * Set to <code>false</code> to disable validation errors.
     */
    boolean validate() default true;

    /**
     * Set to <code>true</code> to right padding on numbers.
     */
    boolean rightPad() default false;

    /**
     * Char to right padding.
     */
    char rightPadChar() default ' ';

    /**
     * Char to right padding on blank values.
     */
    char rightPadCharBlank() default ' ';

    /**
     * Set to <code>true</code> to left padding on texts.
     */
    boolean leftPad() default false;

    /**
     * Char to left padding.
     */
    char leftPadChar() default '0';

    /**
     * Char to left padding on blank values.
     */
    char leftPadCharBlank() default ' ';

    /**
     * Total blanks after field.
     */
    int trailing() default 0;

}
