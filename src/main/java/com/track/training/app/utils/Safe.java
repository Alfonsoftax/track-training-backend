package com.track.training.app.utils;

import static java.math.RoundingMode.HALF_UP;
import static org.apache.commons.lang3.BooleanUtils.toBoolean;
import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.trimToNull;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.NotSerializableException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.Collator;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Random;
import java.util.regex.Pattern;

import jakarta.annotation.Nullable;
import jakarta.persistence.Persistence;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.hibernate.proxy.HibernateProxy;
import org.springframework.util.ReflectionUtils;

import com.track.training.app.interfaces.DomainEntity;
import com.track.training.app.interfaces.DomainEntityCode;
import com.track.training.app.interfaces.DomainEntityDescription;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Safe {

    public static final String ES = "es";
    public static final Locale LOCALE_ES = new Locale(ES);
    public static final String EN = "en";
    public static final Locale LOCALE_EN = new Locale(EN);

    public static final int FRACTION_DIGITS = 2;
    public static final int MAX_FRACTION_DIGITS = 4;

    public static final String ZERO = "0";
    public static final BigDecimal TWO = new BigDecimal("2");
    public static final BigDecimal HUNDRED = new BigDecimal("100");
    public static final BigDecimal THOUSAND = new BigDecimal("1000");
    public static final BigDecimal MIN_PERCENT = new BigDecimal("-999.99");
    public static final BigDecimal MAX_PERCENT = new BigDecimal("999.99");
    public static final BigDecimal TAX_IVA = new BigDecimal("21");

    public static final String LF = "\n";
    public static final String DOT = ".";
    public static final String DOT_SEP = ". ";
    public static final String MINUS = "-";
    public static final String SEPARATOR = " - ";
    public static final String SPACE = " ";
    public static final String COMMA = ",";
    public static final String COMMA_SEP = COMMA + SPACE;
    public static final String SEMICOLON = ";";
    public static final String SEMICOLON_SEP = SEMICOLON + SPACE;
    public static final String ELLIPSIS = "...";
    public static final String SLASH = "/";

    public static final Locale LOCALE_SPAIN = new Locale("es", "ES");
    public static final Pattern DECIMAL_PATTERN = Pattern.compile("^(\\d{1,3}(\\.\\d{3})*|\\d+)(\\,\\d+)?$");

    public static final Random randomNumber = new Random(); // NOSONAR No extra security needed

    public static final Collator TEXT_SORT = Collator.getInstance();
    static {
        TEXT_SORT.setDecomposition(Collator.CANONICAL_DECOMPOSITION);
    }

    // -------------------------------------------------------------------------

    public static BigDecimal adds(final @Nullable Object... values) {
        BigDecimal total = BigDecimal.ZERO;
        if (values != null) {
            for (final Object each : values) {
                total = add(total, toBigDecimal(each, null));
            }
        }
        return total;
    }

    public static BigDecimal adds(final @Nullable BigDecimal... values) {
        BigDecimal total = BigDecimal.ZERO;
        if (values != null) {
            for (final BigDecimal each : values) {
                total = add(total, each);
            }
        }
        return total;
    }

    public static BigDecimal adds(final @Nullable Iterable<BigDecimal> values) {
        BigDecimal total = BigDecimal.ZERO;
        if (values != null) {
            for (final BigDecimal each : values) {
                total = add(total, each);
            }
        }
        return total;
    }

    public static @Nullable BigDecimal add(final @Nullable Object o1, @Nullable final Object o2) {
        return add(toBigDecimal(o1, null), toBigDecimal(o2, null), null);
    }

    public static @Nullable BigDecimal add(final @Nullable BigDecimal d1, @Nullable final BigDecimal d2) {
        return add(d1, d2, null);
    }

    public static @Nullable BigDecimal add(final @Nullable BigDecimal d1, final @Nullable BigDecimal d2,
            final @Nullable BigDecimal defaultValue) {
        if (d2 == null) {
            return d1 == null ? defaultValue : d1;
        }
        if (d1 == null) {
            return d2;
        }
        return d1.add(d2);
    }

    public static @Nullable BigDecimal subtract(final @Nullable Object o1, final @Nullable Object o2) {
        return subtract(toBigDecimal(o1, null), toBigDecimal(o2, null), null);
    }

    public static @Nullable BigDecimal subtract(final @Nullable BigDecimal d1, final @Nullable BigDecimal d2) {
        return subtract(d1, d2, null);
    }

    public static @Nullable BigDecimal subtract(final @Nullable BigDecimal d1, final @Nullable BigDecimal d2,
            final @Nullable BigDecimal defaultValue) {
        if (d2 == null) {
            return d1 == null ? defaultValue : d1;
        }
        if (d1 == null) {
            return d2.negate();
        }
        return d1.subtract(d2);
    }

    public static @Nullable BigDecimal negate(final @Nullable BigDecimal d) {
        return negate(d, null);
    }

    public static @Nullable BigDecimal negate(final @Nullable BigDecimal d, final @Nullable BigDecimal defaultValue) {
        if (d != null) {
            return d.negate();
        }
        return defaultValue;
    }

    public static @Nullable BigDecimal abs(final @Nullable BigDecimal d) {
        return abs(d, null);
    }

    public static @Nullable BigDecimal abs(final @Nullable BigDecimal d, final @Nullable BigDecimal defaultValue) {
        if (d != null) {
            return d.abs();
        }
        return defaultValue;
    }

    public static @Nullable Integer abs(final @Nullable Integer d) {
        return abs(d, null);
    }

    public static @Nullable Integer abs(final @Nullable Integer d, final @Nullable Integer defaultValue) {
        if (d != null) {
            return Math.abs(d);
        }
        return defaultValue;
    }

    public static @Nullable BigDecimal multiply(final @Nullable Object o1, @Nullable final Object o2) {
        return multiply(toBigDecimal(o1, null), toBigDecimal(o2, null));
    }

    public static @Nullable BigDecimal multiply(final @Nullable BigDecimal d1, @Nullable final BigDecimal d2) {
        if (d1 == null || d2 == null) {
            return null;
        }
        if (d1.signum() == 0 || d2.signum() == 0) {
            return BigDecimal.ZERO;
        }
        return d1.multiply(d2).stripTrailingZeros();
    }

    public static @Nullable BigDecimal divide(final @Nullable Object o1, @Nullable final Object o2) {
        return divide(toBigDecimal(o1, null), toBigDecimal(o2, null));
    }

    public static @Nullable BigDecimal divide(final @Nullable Object o1, @Nullable final Object o2, final int scale,
            final RoundingMode roundingMode) {
        return divide(toBigDecimal(o1, null), toBigDecimal(o2, null), scale, roundingMode);
    }

    public static @Nullable BigDecimal divide(final @Nullable BigDecimal d1, @Nullable final BigDecimal d2) {
        return divide(d1, d2, FRACTION_DIGITS);
    }

    public static @Nullable BigDecimal divide(final @Nullable BigDecimal d1, final @Nullable BigDecimal d2,
            final int scale) {
        return divide(d1, d2, scale, HALF_UP);
    }

    public static @Nullable BigDecimal divide(final @Nullable BigDecimal d1, final @Nullable BigDecimal d2,
            final int scale, final RoundingMode roundingMode) {
        if (d1 == null || d2 == null) {
            return null;
        }
        if (d1.signum() == 0 || d2.signum() == 0) {
            return BigDecimal.ZERO;
        }
        return d1.divide(d2, scale, roundingMode).stripTrailingZeros();
    }

    public static @Nullable BigDecimal div(final @Nullable Object o1, @Nullable final Object o2) {
        return div(toBigDecimal(o1, null), toBigDecimal(o2, null));
    }

    public static @Nullable BigDecimal div(final @Nullable BigDecimal d1, @Nullable final BigDecimal d2) {
        if (d1 == null || d2 == null) {
            return null;
        }
        if (d1.signum() == 0 || d2.signum() == 0) {
            return BigDecimal.ZERO;
        }
        return d1.divide(d2, MathContext.DECIMAL64).stripTrailingZeros();
    }

    public static BigDecimal getVatAmount(final BigDecimal base, final BigDecimal percentage) {
        return getVatAmount(base, percentage, 2);
    }

    public static BigDecimal getVatAmount(final BigDecimal base, final BigDecimal percentage, final int scale) {
        if (base == null || percentage == null) {
            return null;
        }
        if (base.signum() == 0 || percentage.signum() == 0) {
            return BigDecimal.ZERO;
        }
        return divide(base.multiply(percentage), HUNDRED, scale);
    }

    public static BigDecimal round(final @Nullable BigDecimal d, final int newScale) {
        return round(d, newScale, HALF_UP);
    }

    public static BigDecimal round(final @Nullable BigDecimal d, final int newScale, final RoundingMode roundingMode) {
        if (d == null) {
            return null;
        }
        return d.setScale(newScale, roundingMode);
    }

    public static int signum(final Number n) {
        if (n == null) {
            return -2;
        }
        if (n instanceof final BigDecimal bd) {
            return bd.signum();
        }
        if (n instanceof final BigInteger bi) {
            return bi.signum();
        }
        if (n instanceof final Long l) {
            return Long.signum(l);
        }
        if (n instanceof final Double d) {
            return Safe.toBigDecimal(d).signum();
        }
        return Integer.signum(n.intValue()); // Integer & others
    }

    // -------------------------------------------------------------------------

    public static @Nullable <T> T firstNonNull(@Nullable final T first, @Nullable final T second) {
        return first != null ? first : second;
    }

    public static @Nullable String toString(final @Nullable Double value) {
        return toString(toBigDecimal(value, null));
    }

    public static @Nullable String toString(final @Nullable BigDecimal value) {
        return value == null ? null : value.stripTrailingZeros().toPlainString();
    }

    public static @Nullable String toString(final @Nullable Object value) {
        return toString(value, null);
    }

    public static @Nullable String toString(final @Nullable Object value, final String defaultValue) {
        return value == null ? defaultValue : value.toString();
    }

    public static boolean startsWith(final String str, final String... prefixes) {
        if (str != null && prefixes != null) {
            for (final String prefix : prefixes) {
                if (prefix != null && str.startsWith(prefix)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static int toInt(final Object o) {
        if (o instanceof final Number n) {
            return Safe.toInt(n, -1);
        }
        if (o instanceof final String s) {
            return Safe.toInt(s, -1);
        }
        return -1;
    }

    public static int toInt(final Number number) {
        return number == null ? 0 : number.intValue();
    }

    public static int toInt(final Number number, final int defaultValue) {
        return number == null ? defaultValue : number.intValue();
    }

    public static int toInt(final String s) {
        return toInt(s, -1);
    }

    public static int toInt(final String s, final int defaultValue) {
        if (NumberUtils.isNumber(s)) {
            try {
                return Integer.parseInt(s);

            } catch (final NumberFormatException nfe) {
                // Ignore exception
            }
        }
        return defaultValue;
    }

    public static Integer toInteger(final Object o) {
        if (o instanceof final Number n) {
            return Safe.toInteger(n);
        }
        if (o instanceof final String s) {
            return Safe.toInteger(s);
        }
        return null;
    }

    public static Integer toInteger(final Number d) {
        return toInteger(d, null);
    }

    public static Integer toInteger(final Number d, final Integer defaultValue) {
        if (d == null) {
            return defaultValue;
        }
        return d.intValue();
    }

    public static Integer toInteger(final String s) {
        return toInteger(s, null);
    }

    public static Integer toInteger(final String s, final Integer defaultValue) {
        if (NumberUtils.isNumber(s)) {
            try {
                return Integer.valueOf(s);

            } catch (final NumberFormatException nfe) {
                // Ignore exception
            }
        }
        return defaultValue;
    }

    public static String toStringInt(final String s) {
        if (NumberUtils.isNumber(s)) {
            try {
                return Integer.valueOf(s).toString();

            } catch (final NumberFormatException nfe) {
                // Ignore exception
            }
        }
        return s;
    }

    public static Integer add(final Integer n1, final Number n2) {
        return add(n1, n2, null);
    }

    public static Integer add(final Integer n1, final Number n2, final Integer defaultValue) {
        if (n2 == null) {
            return n1 == null ? defaultValue : n1;
        }
        if (n1 == null) {
            return n2.intValue();
        }
        return n1 + n2.intValue();
    }

    public static Integer subtract(final Integer n1, final Number n2) {
        return subtract(n1, n2, null);
    }

    public static Integer subtract(final Integer n1, final Number n2, final Integer defaultValue) {
        if (n2 == null) {
            return n1 == null ? defaultValue : n1;
        }
        if (n1 == null) {
            return -n2.intValue();
        }
        return n1 - n2.intValue();
    }

    public static Long toLong(final Object o) {
        if (o instanceof final Number n) {
            return Safe.toLong(n);
        }
        if (o instanceof final String s) {
            return Safe.toLong(s);
        }
        return null;
    }

    public static Long toLong(final Number d) {
        return toLong(d, null);
    }

    public static Long toLong(final Number d, final Long defaultValue) {
        if (d == null) {
            return defaultValue;
        }
        return d.longValue();
    }

    public static Long toLong(final String s) {
        return toLong(s, null);
    }

    public static Long toLong(final String s, final Long defaultValue) {
        if (NumberUtils.isNumber(s)) {
            try {
                return Long.valueOf(s);

            } catch (final NumberFormatException nfe) {
                // Ignore exception
            }
        }
        return defaultValue;
    }

    public static Long add(final Long n1, final Number n2) {
        return add(n1, n2, null);
    }

    public static Long add(final Long n1, final Number n2, final Long defaultValue) {
        if (n2 == null) {
            return n1 == null ? defaultValue : n1;
        }
        if (n1 == null) {
            return n2.longValue();
        }
        return n1 + n2.longValue();
    }

    public static Long subtract(final Long n1, final Number n2) {
        return subtract(n1, n2, null);
    }

    public static Long subtract(final Long n1, final Number n2, final Long defaultValue) {
        if (n2 == null) {
            return n1 == null ? defaultValue : n1;
        }
        if (n1 == null) {
            return -n2.longValue();
        }
        return n1 - n2.longValue();
    }

    public static Double toDouble(final Number number) {
        return number == null ? null : number.doubleValue();
    }

    public static @Nullable BigDecimal toBigDecimal(final @Nullable Object o) {
        return toBigDecimal(o, null);
    }

    public static @Nullable BigDecimal toBigDecimal(final @Nullable Object o, final @Nullable BigDecimal defaultValue) {
        if (o != null) {
            if (o instanceof final BigDecimal bd) {
                return bd;
            }
            if (o instanceof final Number n) {
                return toBigDecimal(n, defaultValue);
            }
            if (o instanceof final String s) {
                return toBigDecimal(s, defaultValue);
            }
        }
        return defaultValue;
    }

    public static @Nullable BigDecimal toBigDecimal(final @Nullable Number n) {
        return toBigDecimal(n, null);
    }

    public static @Nullable BigDecimal toBigDecimal(final @Nullable Number n, final @Nullable BigDecimal defaultValue) {
        if (n == null) {
            return defaultValue;
        }
        final BigDecimal bd;
        if (n instanceof final Double d) {
            bd = BigDecimal.valueOf(d);
        } else {
            bd = BigDecimal.valueOf(n.longValue());
        }
        return bd.stripTrailingZeros();
    }

    public static @Nullable BigDecimal toBigDecimal(final @Nullable String s) {
        return toBigDecimal(s, null);
    }

    public static @Nullable BigDecimal toBigDecimal(final @Nullable String s, final @Nullable BigDecimal defaultValue) {
        if (StringUtils.isNotBlank(s)) {
            try {
                return new BigDecimal(s);

            } catch (final NumberFormatException nfe) {
                // Ignore exception
            }
        }
        return defaultValue;
    }

    public static @Nullable BigInteger toBigInteger(final @Nullable Object o) {
        return toBigInteger(o, null);
    }

    public static @Nullable BigInteger toBigInteger(final @Nullable Object o, final @Nullable BigInteger defaultValue) {
        if (o != null) {
            if (o instanceof final BigInteger bi) {
                return bi;
            }
            if (o instanceof final Number n) {
                return toBigInteger(n, defaultValue);
            }
            if (o instanceof final String s) {
                return toBigInteger(s, defaultValue);
            }
        }
        return defaultValue;
    }

    public static @Nullable BigInteger toBigInteger(final @Nullable Number n) {
        return toBigInteger(n, null);
    }

    public static @Nullable BigInteger toBigInteger(final @Nullable Number n, final @Nullable BigInteger defaultValue) {
        if (n != null) {
            return BigInteger.valueOf(n.longValue());
        }
        return defaultValue;
    }

    public static @Nullable BigInteger toBigInteger(final @Nullable String s) {
        return toBigInteger(s, null);
    }

    public static @Nullable BigInteger toBigInteger(final @Nullable String s, final @Nullable BigInteger defaultValue) {
        if (StringUtils.isNotBlank(s)) {
            try {
                return new BigInteger(s);

            } catch (final NumberFormatException nfe) {
                // Ignore exception
            }
        }
        return defaultValue;
    }

    public static String format(final Number number) {
        return format(number, FRACTION_DIGITS, LOCALE_ES);
    }

    public static String format(final Number number, final Locale locale) {
        return format(number, FRACTION_DIGITS, locale);
    }

    public static String format(final Number number, final int minFractionDigits, final Locale locale) {
        return format(number, minFractionDigits, MAX_FRACTION_DIGITS, locale);
    }

    public static String format(final Number number, final int minFractionDigits, final int maxFractionDigits) {
        return format(number, minFractionDigits, maxFractionDigits, LOCALE_ES);
    }

    public static String format(final Number number, final int minFractionDigits, final int maxFractionDigits,
            final Locale locale) {
        if (number == null) {
            return null;
        }
        final NumberFormat numberFormat = NumberFormat.getInstance(locale);
        if (numberFormat instanceof final DecimalFormat df) {
            df.setMinimumFractionDigits(minFractionDigits);
            df.setMaximumFractionDigits(maxFractionDigits);
        }
        return numberFormat.format(number);
    }

    public static BigDecimal parseDecimal(final String value, final Locale locale) {
        if (isBlank(value) || !DECIMAL_PATTERN.matcher(value).matches()) {
            return null;
        }
        final NumberFormat numberFormat = NumberFormat.getInstance(locale);
        if (numberFormat instanceof final DecimalFormat df) {
            df.setParseBigDecimal(true);
        }
        try {
            return (BigDecimal) numberFormat.parse(value);
        } catch (final ParseException e) {
            return null;
        }
    }

    public static BigDecimal parseDecimal(final String value, final Locale locale, final int precision,
            final int scale) {
        BigDecimal decimal = Safe.parseDecimal(value, locale);
        if (decimal != null) {
            decimal = Safe.round(decimal, scale);
            if (decimal.precision() > precision) {
                decimal = null;
            }
        }
        return decimal;
    }

    public static <I extends Serializable, T extends DomainEntity<I>, S extends T> boolean equalsId(final T e1,
            final S e2) {
        return Objects.equals(Safe.getId(e1), Safe.getId(e2));
    }

    public static <I extends Serializable, T extends DomainEntity<I>, S extends T> boolean equalsIdentifier(final T e1,
            final S e2) {
        return Objects.equals(Safe.getIdentifier(e1), Safe.getIdentifier(e2));
    }

    public static <T> T get(final @Nullable List<? extends T> list, final int pos) {
        if (list != null && pos >= 0 && pos < list.size()) {
            return list.get(pos);
        }
        return null;
    }

    public static <T> T get(final @Nullable T[] array, final int pos) {
        if (array != null && pos >= 0 && pos < array.length) {
            return array[pos];
        }
        return null;
    }

    public static <T> T getFirst(final @Nullable Iterable<? extends T> iterable) {
        if (iterable != null) {
            for (final T element : iterable) {
                if (element != null) {
                    return element;
                }
            }
        }
        return null;
    }

    public static <T> T getFirst(final @Nullable T[] array) {
        if (array != null) {
            for (final T element : array) {
                if (element != null) {
                    return element;
                }
            }
        }
        return null;
    }

    public static <T> T getFirstOne(final @Nullable Iterable<? extends T> iterable) {
        if (iterable != null) {
            final Iterator<? extends T> it = iterable.iterator();
            if (it.hasNext()) {
                final T element = it.next();
                if (!it.hasNext()) {
                    return element;
                }
            }
        }
        return null;
    }

    public static <T> T getLast(final @Nullable Iterable<? extends T> iterable) {
        T lastElement = null;
        if (iterable != null) {
            for (final T element : iterable) {
                if (element != null) {
                    lastElement = element;
                }
            }
        }
        return lastElement;
    }

    public static boolean isLoaded(final Object entity, final String attributeName) {
        return entity != null && Persistence.getPersistenceUtil().isLoaded(entity, attributeName);
    }

    @SuppressWarnings("unchecked")
    public static <E extends DomainEntity<I>, I extends Serializable> I getIdentifier(final E entity) {
        if (entity != null) {
            if (entity instanceof final HibernateProxy p) {
                return (I) p.getHibernateLazyInitializer().getIdentifier();
            }
            if (Persistence.getPersistenceUtil().isLoaded(entity)) {
                return entity.getId();
            }
        }
        return null;
    }

    public static <I extends Serializable, T extends DomainEntity<I>> I getId(final T entity) {
        if (entity == null) {
            return null;
        }
        return entity.getId();
    }

    public static <I extends Serializable, T extends DomainEntity<I>> List<I> getIds(final Collection<T> entities) {
        if (entities == null) {
            return Collections.emptyList();
        }
        final List<I> ids = new ArrayList<>(entities.size());
        for (final T entity : entities) {
            if (entity != null) {
                ids.add(entity.getId());
            }
        }
        return ids;
    }

    public static <T extends DomainEntityCode> String getCode(final T entity) {
        if (entity == null) {
            return null;
        }
        return entity.getCode();
    }

    public static <T extends DomainEntityDescription> String getDescription(final T entity) {
        if (entity == null) {
            return null;
        }
        return entity.getDescription();
    }

    public static <T extends DomainEntityCode & DomainEntityDescription> String getLabel(final T entity) {
        return Safe.getLabel(Safe.getCode(entity), Safe.getDescription(entity));
    }

    public static String getLabel(final String code, final String description) {
        if (StringUtils.isBlank(code)) {
            return null;
        }
        return StringUtils.join(new String[] { code, description }, SEPARATOR);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Map<?, ?>> T toMap(final Object... values) {
        final Map<Object, Object> map = new HashMap<>();
        for (int i = 0; i < values.length; i += 2) {
            map.put(values[i], values[i + 1]);
        }
        return (T) map;
    }

    public static <K, V> List<V> putList(final Map<K, List<V>> map, final K key, final V value) {
        if (map == null || value == null) {
            return Collections.emptyList();
        }
        List<V> values;
        if (map.containsKey(key)) {
            values = map.get(key);
        } else {
            values = new ArrayList<>();
            map.put(key, values);
        }
        values.add(value);
        return values;
    }

    public static boolean isSame(final String a, final String b) {
        if (a == null || b == null) {
            return a == null && b == null;
        }
        final Collator insenstiveStringComparator = Collator.getInstance();
        insenstiveStringComparator.setStrength(Collator.PRIMARY);
        return insenstiveStringComparator.compare(a, b) == 0;
    }

    public static <T extends Enum<T>> T getEnum(final Class<T> enumType, final String value) {
        if (value == null) {
            return null;
        }
        final String name = value.trim().toUpperCase();
        try {
            return Enum.valueOf(enumType, name);
        } catch (final Exception ex) {
            final T[] elements = enumType.getEnumConstants();
            for (final T element : elements) {
                if (name.equalsIgnoreCase(element.toString())) {
                    return element;
                }
            }
            return null;
        }
    }

    public static <T extends EnumValue<?>> T fromValue(final Class<T> enumType, final Object value) {
        if (value == null) {
            return null;
        }
        final T[] elements = enumType.getEnumConstants();
        for (final T element : elements) {
            if (value.equals(element.getValue())) {
                return element;
            }
        }
        return null;
    }

    public static boolean overlaps(@Nullable final Date start, @Nullable final Date end, @Nullable final Date date) {
        return overlaps(start, end, date, date);
    }

    public static boolean overlaps(@Nullable final Date start1, @Nullable final Date end1, @Nullable final Date start2,
            @Nullable final Date end2) {
        return (start1 == null || end2 == null || start1.compareTo(end2) <= 0)
                && (end1 == null || start2 == null || end1.compareTo(start2) >= 0);
    }

    public static void sleep(final long ms) {
        try {
            Thread.sleep(ms);
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> Class<T> getGenericSuperclassType(final Class<?> clazz, final int index) {
        final Type type = clazz.getGenericSuperclass();
        if (type instanceof final ParameterizedType t) {
            final Type[] actualTypeArguments = t.getActualTypeArguments();
            if (actualTypeArguments.length > index && actualTypeArguments[index] instanceof Class) {
                return (Class<T>) actualTypeArguments[index];
            }
        } else if (type instanceof TypeVariable && ArrayUtils.contains(clazz.getTypeParameters(), type)) {
            return getGenericSuperclassType(clazz, index);
        }
        return null;
    }

    public static String key(final CharSequence... elements) {
        return String.join(DOT, elements);
    }

    public static String code(final Integer code, final int size) {
        if (code == null) {
            return EMPTY;
        }
        return Safe.code(String.valueOf(code), size);
    }

    public static String code(String code, final int size) {
        if (code == null) {
            return EMPTY;
        }
        code = StringUtils.trim(code);
        if (code.length() > size) {
            code = code.substring(code.length() - size);
        }
        return StringUtils.leftPad(code, size, '0');
    }

    @SuppressWarnings("unchecked")
    public static <V> V deepCopy(final V value) {
        if (value == null) {
            return null;
        }
        try {
            // Write to new byte array to clone.
            final ByteArrayOutputStream baos = new ByteArrayOutputStream(4096);
            try (final ObjectOutputStream oos = new ObjectOutputStream(baos)) {
                oos.writeObject(value);
            }

            // Read it back and return a true copy.
            final ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            try (final ObjectInputStream ois = new ObjectInputStream(bais)) {
                return (V) ois.readObject();
            }
        } catch (final NotSerializableException e) {
            return value;
        } catch (final IOException | ClassNotFoundException e) {
            return null;
        }
    }

    @SuppressWarnings("unchecked")
    public static <T> T getProperty(final Object object, final String property) {
        try {
            final Field field = ReflectionUtils.findField(object.getClass(), property);
            if (field != null) {
                ReflectionUtils.makeAccessible(field);
                return (T) field.get(object);
            }
        } catch (final IllegalAccessException e) {
            // Ignore exception
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    public static <T> T getNestedProperty(final Object object, final String propertyPath) {
        Object value = object;
        if (StringUtils.isNotBlank(propertyPath)) {
            for (final String property : StringUtils.split(propertyPath, '.')) {
                if (value != null) {
                    value = getProperty(value, property);
                }
            }
        }
        return (T) value;
    }

    public static boolean setProperty(final Object object, final String property, final Object value) {
        try {
            final Field field = ReflectionUtils.findField(object.getClass(), property);
            if (field != null) {
                ReflectionUtils.makeAccessible(field);
                ReflectionUtils.setField(field, object, value);
                return true;
            }
        } catch (final Exception e) {
            // Ignore exception
        }
        return false;
    }

    public static String getHostName() {
        try {
            return InetAddress.getLocalHost().getHostName();
        } catch (final UnknownHostException e) {
            return "?"; //$NON-NLS-1$
        }
    }

    public static <T extends Comparable<T>> boolean equals(final T d1, final T d2) {
        return Safe.compareTo(d1, d2, false) == 0;
    }

    public static <T extends Comparable<T>> int compareTo(final T d1, final T d2) {
        return Safe.compareTo(d1, d2, false);
    }

    public static <T extends Comparable<T>> int compareTo(final T d1, final T d2, final boolean nullsAreHigh) {
        if (d1 == d2) {
            return 0;
        }
        if (d1 == null) {
            return nullsAreHigh ? 1 : -1;
        }
        if (d2 == null) {
            return nullsAreHigh ? -1 : 1;
        }
        return d1.compareTo(d2);
    }

    public static final String getMessageCode(final Object[] params) {
        if (params == null || params.length == 0) {
            return null;
        }
        final Object firstEntry = params[0];
        if (firstEntry instanceof final String s) {
            return s;
        }
        return null;
    }

    public static final Throwable getThrowable(final Object[] params) {
        if (params == null || params.length == 0) {
            return null;
        }
        final Object lastEntry = params[params.length - 1];
        if (lastEntry instanceof final Throwable e) {
            return e;
        }
        return null;
    }

    public static final String abbreviate(final String str, final String abbrevMarker, final String breakPoint,
            final int maxWidth) {
        if (StringUtils.length(str) > maxWidth) {
            final int abbrevMarkerLength = abbrevMarker.length();
            final int breakPointLengh = breakPoint.length();
            final int offset = maxWidth - abbrevMarkerLength - breakPointLengh;
            int index = StringUtils.lastIndexOf(str, breakPoint, offset);
            if (index == -1) {
                index = offset;
            } else if (Character.isWhitespace(str.charAt(index + 1))
                    && index + abbrevMarkerLength + breakPointLengh < maxWidth) {
                index++;
            }
            return str.substring(0, index + breakPointLengh) + abbrevMarker;
        }
        return str;
    }

    @SuppressWarnings("unchecked")
    public static <T> T[] flatten(final Object... params) {
        return (T[]) flattenList(params).toArray();
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> flattenList(final Object... params) {
        final List<T> parameters = new ArrayList<>();
        if (params != null) {
            for (final Object param : params) {
                if (param instanceof final Object[] p) {
                    parameters.addAll((Collection<? extends T>) flattenList(p));
                } else if (param instanceof Collection<?>) {
                    parameters.addAll((Collection<? extends T>) param);
                } else {
                    parameters.add((T) param);
                }
            }
        }
        return parameters;
    }

    @SafeVarargs
    public static <E> boolean equals(final E element, final E... elements) {
        if (element == null || elements == null) {
            return false;
        }
        for (final E each : elements) {
            if (each != null && element.equals(each)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isStringTrue(final String str) {

        if (str == null) {
            return false;
        }

        if (BooleanUtils.isTrue(toBoolean(str))) {
            return true;
        }

        switch (str.length()) {
            case 1: {
                final char ch0 = str.charAt(0);
                if (ch0 == 's' || ch0 == 'S') {
                    return true;
                }
                break;
            }
            case 2: {
                final char ch0 = str.charAt(0);
                final char ch1 = str.charAt(1);
                if ((ch0 == 's' || ch0 == 'S') && (ch1 == 'i' || ch1 == 'I' || ch1 == 'í' || ch1 == 'Í')) {
                    return true;
                }
                break;
            }
            default:
                return false;
        }
        return false;
    }

    public static boolean isStringFalse(final String str) {
        return !isStringTrue(str);
    }

    public static boolean isTrue(final Object value) {

        if (value instanceof final Boolean b) {
            return b;

        } else if (value instanceof final Number n) {
            return n.intValue() == 1;

        } else if (value instanceof final Character c) {
            return isStringTrue(c.toString());

        } else if (value instanceof final CharSequence s) {
            return isStringTrue(trimToNull(s.toString()));
        }
        return false;
    }

    @SafeVarargs
    public static final <E> String join(final String separator, final String lastSeparator, final E... elements) {
        if (elements == null) {
            return null;
        }
        final String sep = separator == null ? EMPTY : separator;
        final String lastSep = lastSeparator == null ? sep : lastSeparator;
        final StringBuilder builder = new StringBuilder();
        final List<E> notBlankElements = new LinkedList<>();
        for (final E element : elements) {
            if (element != null && isNotBlank(element.toString())) {
                notBlankElements.add(element);
            }
        }
        final Iterator<E> iterator = notBlankElements.iterator();
        while (iterator.hasNext()) {
            final E next = iterator.next();
            if (builder.length() > 0) {
                if (iterator.hasNext()) {
                    builder.append(sep);
                } else {
                    builder.append(lastSep);
                }
            }
            builder.append(next);
        }
        return builder.toString();
    }

    public static long min(final Long a, final Long b) {
        if (a == null) {
            return 0L;
        }
        if (b == null) {
            return a;
        }
        return a <= b ? a : b;
    }

    public static long max(final Long a, final Long b) {
        if (a == null && b == null) {
            return 0L;
        }
        if (a == null) {
            return b;
        }
        if (b == null) {
            return a;
        }
        return a >= b ? a : b;
    }

}
