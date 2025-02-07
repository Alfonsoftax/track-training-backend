//package com.src.train.track.general.helper;
//
//
//import static org.apache.commons.lang3.StringUtils.EMPTY;
//
//import java.math.BigDecimal;
//import java.sql.Timestamp;
//import java.util.Collection;
//import java.util.Date;
//import java.util.function.Consumer;
//
//import org.apache.commons.lang3.ArrayUtils;
//import org.apache.commons.lang3.BooleanUtils;
//
//import com.querydsl.core.types.dsl.NumberTemplate;
//import com.querydsl.core.types.dsl.StringExpression;
//import com.src.train.track.User.model.User;
//import com.src.train.track.general.Constants;
//import com.src.train.track.general.GeneralData;
//import com.src.train.track.general.domain.DomainEmbeddable;
//import com.src.train.track.general.domain.DomainEntity;
//import com.src.train.track.general.domain.TransactionType;
//import com.src.train.track.general.utils.EnumValue;
//import com.src.train.track.general.utils.Safe;
//
//import jakarta.annotation.Nullable;
//import jakarta.persistence.Tuple;
//import lombok.AccessLevel;
//import lombok.NoArgsConstructor;
//
//@NoArgsConstructor(access = AccessLevel.PRIVATE)
//public class NativeQueryUtils {
//
//    public static final String COMMA = ", ";
//    public static final String DOT = ".";
//    public static final String QUOTE = "'";
//    public static final String COMMA_QUOTE = COMMA + QUOTE;
//    public static final String QUOTE_COMMA = QUOTE + COMMA;
//    public static final String QUOTE_COMMA_QUOTE = QUOTE + COMMA + QUOTE;
//    public static final String OPEN_PARENTHESES = "(";
//    public static final String CLOSE_PARENTHESES = ")";
//    public static final String SPACE_OPEN_PARENTHESES = " (";
//    public static final String CLOSE_PARENTHESES_SPACE = ") ";
//    public static final String CONCAT = " || ";
//    public static final String EQUAL = " = ";
//    public static final String NOT_EQUAL = " != ";
//    public static final String GOE = " >= ";
//    public static final String LOE = " <= ";
//    public static final String ADD = " + ";
//    public static final String MINUS = " - ";
//    public static final String MULTIPLY = " * ";
//    public static final String DIVIDE = " / ";
//    public static final String BETWEEN = " BETWEEN ";
//
//    public static final String SUM = "SUM(";
//    public static final String ROUND = "ROUND(";
//    public static final String NVL = "NVL(";
//    public static final String TRUNC = "TRUNC(";
//    public static final String TODAY = TRUNC + "SYSDATE" + CLOSE_PARENTHESES;
//    public static final String PARAM_MONTH = ", 'MM')";
//    public static final String LAST_DAY = "LAST_DAY(";
//    public static final String CURRENT_TIMESTAMP = "CURRENT_TIMESTAMP";
//    public static final String TO_DATE = "'TO_DATE('''dd/MM/yyyy''',''DD/MM/YYYY'')'";
//    public static final String TO_DATE_TIME = "'TO_DATE('''dd/MM/yyyy HH:mm:ss''',''DD/MM/YYYY HH24:MI:SS'')'";
//    public static final String DATE_COLUMN_PREFIX = "FECHA";
//    public static final String TO_CHAR_START = "TO_CHAR(";
//    public static final String TO_CHAR_END = ",'DD/MM/YYYY')";
//    public static final String NULL = "NULL";
//    public static final String IS = " IS ";
//    public static final String NOT = "NOT ";
//    public static final String EXISTS = "EXISTS ";
//    public static final String NOT_EXISTS = "NOT EXISTS ";
//
//    public static final String SELECT = "SELECT ";
//    public static final String SELECT_ONE_FROM = "SELECT 1 FROM ";
//    public static final String ALL = "*";
//    public static final String DISTINCT = "DISTINCT ";
//    public static final String AS = " AS ";
//    public static final String FROM = " FROM ";
//    public static final String UNION_ALL = " UNION ALL ";
//    public static final String AND = " AND ";
//    public static final String OR = " OR ";
//    public static final char ZERO = '0';
//
//    public static final String INNER_JOIN = " INNER JOIN ";
//    public static final String LEFT_JOIN = " LEFT JOIN ";
//    public static final String ON = " ON ";
//
//    public static final String INSERT_INTO = "INSERT INTO ";
//    public static final String VALUES = " VALUES ";
//
//    public static final String UPDATE_SET = "UPDATE ";
//    public static final String SET = " SET ";
//
//    public static final String DELETE_FROM = "DELETE" + FROM;
//
//    public static final String WHERE = " WHERE ";
//    public static final String IN = " IN ";
//    public static final String FAKE_VALUE = "1";
//    public static final String YES = "'S'";
//    public static final String NO = "'N'";
//
//    public static final String GROUP_BY = " GROUP BY ";
//    public static final String ORDER_BY = " ORDER BY ";
//
//    // for SpEL expressions:
//    public static final String PARAM = ":#{#";
//    public static final String PARAM_T = ":#{T";
//    public static final String GET_VALUE = ".getValue()}";
//    public static final String TO_STRING = ".toString()}";
//
//    
//
//    // -------------------------------------------------------------------------
//
//    public static final String FUNC_PURCHASE_DISCOUNT = "NVL(F_BUSCA_DTO_COMPRA({0},{1},{2},{3},{4}), 0)";
//
//    public static NumberTemplate<BigDecimal> purchaseDiscount(final StringExpression publicationCode,
//            final StringExpression issueCode, final StringExpression supplierCode, final StringExpression clientCode,
//            final boolean isBreakdown) {
//
//        return numberTemplate(BigDecimal.class, FUNC_PURCHASE_DISCOUNT, publicationCode, issueCode, supplierCode,
//                clientCode, constant(isBreakdown ? Constants.YES : Constants.NO));
//    }
//
//    // -------------------------------------------------------------------------
//
//    public static StringBuilder update(final String table, final GeneralData generalData, final Collection<?> ids,
//            final Object idColumns, final Object... updateValues) {
//
//        final StringBuilder queryString = new StringBuilder();
//
//        queryString.append(UPDATE_SET).append(table).append(SET);
//
//        appendUpdateSet(queryString, generalData, updateValues);
//
//        appendWhere(queryString, null, idColumns, ids);
//
//        return queryString;
//    }
//
//    public static StringBuilder updateStrict(final String table, final GeneralData generalData, final Collection<?> ids,
//            final Object idColumns, final Object... updateValues) {
//
//        final StringBuilder queryString = new StringBuilder();
//
//        queryString.append(UPDATE_SET).append(table).append(SET);
//
//        appendUpdateSet(queryString, generalData, updateValues);
//
//        appendWhere(queryString, null, idColumns, ids, updateValues);
//
//        return queryString;
//    }
//
//    public static StringBuilder delete(final String table, final Collection<?> ids, final Object idColumns) {
//
//        final StringBuilder queryString = new StringBuilder();
//
//        queryString.append(DELETE_FROM).append(table);
//
//        appendWhere(queryString, null, idColumns, ids);
//
//        return queryString;
//    }
//
//    public static StringBuilder audit(final String table, final TransactionType transactionType, // NOSONAR
//            final String processCode, final String genericUser, @Nullable final Integer audSid, final Collection<?> ids,
//            final String auditIdColumn, final Object idColumns, final Object... auditValues) {
//
//        return audit(table, table, transactionType, processCode, genericUser, audSid,
//                queryString -> appendArgument(queryString, auditIdColumn, idColumns, auditValues),
//                queryString -> appendWhere(queryString, null, idColumns, ids));
//    }
//
//    public static StringBuilder auditStrict(final String table, final TransactionType transactionType, // NOSONAR
//            final String processCode, final String genericUser, @Nullable final Integer audSid, final Collection<?> ids,
//            final String auditIdColumn, final Object idColumns, final Object... auditValues) {
//
//        return audit(table, table, transactionType, processCode, genericUser, audSid,
//                queryString -> appendArgument(queryString, auditIdColumn, idColumns, auditValues),
//                queryString -> appendWhere(queryString, null, idColumns, ids, auditValues));
//    }
//
//    public static StringBuilder audit(final String tableAudit, final String tableWhere, // NOSONAR
//            final TransactionType transactionType, final String processCode, final String genericUser,
//            @Nullable final Integer audSid, final Consumer<StringBuilder> argumentConsumer,
//            final Consumer<StringBuilder> whereConsumer) {
//
//        final Date now = new Date();
//        final String startDate = formatDate(now, TO_DATE);
//        final String startHour = formatDate(now, HH_MM_SS_FORMAT);
//
//        final User user = SessionUtils.getUserInSession();
//        final String userCode = user != null ? user.getUsername() : genericUser;
//
//        final StringBuilder queryString = new StringBuilder();
//
//        argumentConsumer.accept(queryString); // ARGUMENT
//        queryString.append(startDate).append(COMMA); // DATE
//        queryString.append(startDate).append(COMMA_QUOTE); // DATE_INIT
//        queryString.append(startHour).append(QUOTE_COMMA_QUOTE); // HOUR_INIT
//        queryString.append(tableAudit).append(QUOTE_COMMA_QUOTE); // PARAMETERS
//        queryString.append(transactionType.getValue()).append(QUOTE_COMMA_QUOTE); // TRANSACTION_TYPE
//        queryString.append(processCode).append(QUOTE_COMMA_QUOTE); // PROCESS_CODE
//        queryString.append(genericUser).append(QUOTE_COMMA_QUOTE); // USER_CODE
//        queryString.append(userCode).append(QUOTE_COMMA); // USER
//        queryString.append(audSid); // AUDSID
//
//        queryString.append(FROM).append(tableWhere);
//
//        whereConsumer.accept(queryString);
//
//        return queryString;
//    }
//
//    public static void appendAuditableValues(final StringBuilder queryString, final String userCode,
//            final String processCode) {
//        queryString.append(QUOTE).append(userCode).append(QUOTE).append(COMMA);
//        queryString.append(CURRENT_TIMESTAMP).append(COMMA);
//        queryString.append(QUOTE).append(userCode).append(QUOTE).append(COMMA);
//        queryString.append(CURRENT_TIMESTAMP).append(COMMA);
//        queryString.append(QUOTE).append(processCode).append(QUOTE).append(COMMA);
//        queryString.append(QUOTE).append(processCode).append(QUOTE);
//    }
//
//    // -------------------------------------------------------------------------
//
//    public static void appendUpdateSet(final StringBuilder queryString, final GeneralData generalData,
//            final Object... updateValues) {
//
//        int i = 0;
//        for (; i < updateValues.length; i++) {
//            Object value = updateValues[i];
//            if (value instanceof CharSequence) {
//                queryString.append(value).append(EQUAL);
//                value = updateValues[++i];
//                if (NEW.equals(value)) {
//                    value = updateValues[++i];
//                }
//            }
//            if (COL.equals(value)) {
//                value = updateValues[++i];
//                queryString.append(value);
//            } else {
//                appendWhereValue(queryString, value);
//            }
//            queryString.append(COMMA);
//        }
//
//        if (generalData != null) {
//            queryString.append(AuditableColumns.MODIFIED_PROCESS_CODE).append(EQUAL);
//            appendWhereValue(queryString, generalData.getProcessCode());
//            queryString.append(COMMA);
//
//            queryString.append(AuditableColumns.LAST_MODIFIED_BY).append(EQUAL);
//            appendWhereValue(queryString, generalData.getUsername());
//            queryString.append(COMMA);
//
//            queryString.append(AuditableColumns.LAST_MODIFIED_AT).append(EQUAL);
//            queryString.append(CURRENT_TIMESTAMP);
//        } else {
//            queryString.setLength(queryString.length() - COMMA.length()); // remove last separator
//        }
//    }
//
//    public static void appendArgument(final StringBuilder queryString, final String auditIdColumn,
//            final Object idColumns, final Object... auditValues) {
//
//        queryString.append(QUOTE).append(auditIdColumn).append(COLON);
//        appendValueColumn(queryString, idColumns);
//        queryString.append(SEMICOLON);
//
//        int i = 0;
//        for (; i < auditValues.length; i++) {
//            boolean isOptionalColumn = false;
//            Object value = auditValues[i];
//            if (value instanceof final CharSequence valueChar) {
//                isOptionalColumn = COL_OPTIONAL.equals(Safe.get(auditValues, i + 2));
//                appendColumn(queryString, isOptionalColumn, valueChar);
//
//                value = auditValues[++i];
//                if (NEW.equals(value)) {
//                    queryString.append(NEW);
//                    value = auditValues[++i];
//                }
//                queryString.append(COLON);
//            }
//
//            if (isOptionalColumn) {
//                value = auditValues[++i];
//                appendValueColumnOptional(queryString, value);
//
//            } else if (COL.equals(value)) {
//                value = auditValues[++i];
//                appendValueColumn(queryString, value);
//            } else {
//                AuditSimpleUtils.appendValue(queryString, value);
////            }
//            queryString.append(SEMICOLON);
//        }
//        queryString.setLength(queryString.length() - SEMICOLON.length()); // remove last separator
//
//        final boolean endsWithQuote = queryString.charAt(queryString.length() - 1) == '\'';
//        if (endsWithQuote) {
//            queryString.setLength(queryString.length() - (CONCAT.length() + QUOTE.length())); // remove last concat
//        } else {
//            queryString.append(QUOTE);
//        }
//        queryString.append(COMMA);
//    }
//
//    private static void appendColumn(final StringBuilder queryString, final boolean isOptionalColumn,
//            final CharSequence value) {
//
//        if (isOptionalColumn) {
//            final boolean endsWithSemicolon = queryString.charAt(queryString.length() - 1) == ';';
//            if (endsWithSemicolon) {
//                queryString.setLength(queryString.length() - 1);
//            }
//            final boolean endsWithQuote = queryString.charAt(queryString.length() - 1) == '\'';
//            if (endsWithQuote) {
//                queryString.setLength(queryString.length() - 1);
//            }
//            queryString.append("NVL2").append(OPEN_PARENTHESES).append(value).append(COMMA).append(QUOTE);
//            if (endsWithSemicolon) {
//                queryString.append(SEMICOLON);
//            }
//        }
//        queryString.append(value);
//    }
//
//    private static void appendValueColumnOptional(final StringBuilder queryString, final Object value) {
//
//        appendColumn(queryString, (String) value);
//
//        queryString.setLength(queryString.length() - (CONCAT.length() + QUOTE.length())); // remove last concat
//        queryString.append(COMMA).append("''").append(CLOSE_PARENTHESES).append(CONCAT).append(QUOTE);
//    }
//
//    private static void appendValueColumn(final StringBuilder queryString, final Object value) {
//        if (value instanceof final String[] values) {
//            queryString.append(BRACKET_OPEN);
//            appendColumn(queryString, values);
//            queryString.append(BRACKET_CLOSE);
//        } else {
//            appendColumn(queryString, (String) value);
//        }
//    }
//
//    private static void appendColumn(final StringBuilder queryString, final String... columns) {
//        final int length = columns.length;
//        for (final String column : columns) {
//            if (length > 1) {
//                queryString.append(column).append(COLON);
//            }
//            queryString.append(QUOTE).append(CONCAT);
//            if (column.startsWith(DATE_COLUMN_PREFIX)) {
//                queryString.append(TO_CHAR_START).append(column).append(TO_CHAR_END);
//            } else {
//                queryString.append(column);
//            }
//            queryString.append(CONCAT).append(QUOTE).append(SEMICOLON);
//        }
//        if (length > 0) {
//            queryString.setLength(queryString.length() - SEMICOLON.length()); // remove last separator
//        }
//    }
//
//    public static void appendArgumentInfo(final StringBuilder queryString, final String auditInfo,
//            final Object idColumn) {
//
//        queryString.append(QUOTE).append(auditInfo).append(COLON).append(SPACE);
//
//        if (idColumn instanceof final String[] idColumns) {
//            appendColumnInfo(queryString, idColumns);
//        } else {
//            appendColumnInfo(queryString, (String) idColumn);
//        }
//
//        queryString.append(QUOTE).append(COMMA);
//    }
//
//    private static void appendColumnInfo(final StringBuilder queryString, final String... columns) {
//        final int length = columns.length;
//        for (final String column : columns) {
//            queryString.append(QUOTE).append(CONCAT);
//            if (column.startsWith(DATE_COLUMN_PREFIX)) {
//                queryString.append(TO_CHAR_START).append(column).append(TO_CHAR_END);
//            } else {
//                queryString.append(column);
//            }
//            queryString.append(CONCAT).append(QUOTE).append(SLASH);
//        }
//        if (length > 0) {
//            queryString.setLength(queryString.length() - SLASH.length()); // remove last separator
//        }
//    }
//
//    private static void appendWhereValue(final StringBuilder queryString, final Object value) {
//        if (value == null) {
//            queryString.append(NULL);
//
//        } else if (value instanceof final BigDecimal bd) {
//            queryString.append(bd.stripTrailingZeros().toPlainString());
//
//        } else if (value instanceof final Double d) {
//            queryString.append(BigDecimal.valueOf(d).stripTrailingZeros().toPlainString());
//
//        } else if (value instanceof Number) {
//            queryString.append(value);
//
//        } else if (value instanceof final Boolean b) {
//            queryString.append(QUOTE).append(BooleanUtils.toString(b, S, N)).append(QUOTE);
//
//        } else if (value instanceof Timestamp) {
//            queryString.append(formatDate(value, TO_DATE_TIME));
//
//        } else if (value instanceof Date) {
//            queryString.append(formatDate(value, TO_DATE));
//
//        } else if (value instanceof EnumValue) {
//            appendWhereValue(queryString, ((EnumValue<?>) value).getValue());
//
//        } else if (value instanceof final DomainEmbeddable embeddable) {
//            final Object[] whereEmbeddable = embeddable.getValues();
//            if (ArrayUtils.isEmpty(whereEmbeddable)) {
//                queryString.append(NULL);
//            } else if (whereEmbeddable.length == 1) {
//                appendWhereEmbeddable(queryString, whereEmbeddable);
//            } else {
//                queryString.append(OPEN_PARENTHESES);
//                appendWhereEmbeddable(queryString, whereEmbeddable);
//                queryString.append(CLOSE_PARENTHESES);
//            }
//
//        } else if (value instanceof DomainEntity<?>) {
//            appendWhereValue(queryString, ((DomainEntity<?>) value).getId());
//
//        } else {
//            queryString.append(QUOTE).append(value.toString()).append(QUOTE);
//        }
//    }
//
//    private static void appendWhereEmbeddable(final StringBuilder queryString, final Object... values) {
//        for (final Object value : values) {
//            appendWhereValue(queryString, value);
//            queryString.append(COMMA);
//        }
//        queryString.setLength(queryString.length() - COMMA.length()); // remove last separator
//    }
//
//    // -------------------------------------------------------------------------
//
//    public static void appendWhere(final StringBuilder queryString, @Nullable final String aliasPrefix,
//            final Object idColumns, final Collection<?> ids) {
//
//        final boolean compositeId = idColumns instanceof String[];
//        final boolean multiValueIn = ids.size() > MAX_IN_VALUES;
//        final String alias = aliasPrefix == null ? EMPTY : aliasPrefix + '.';
//
//        queryString.append(WHERE);
//        if (compositeId) {
//            queryString.append(OPEN_PARENTHESES);
//            for (final String idColumn : (String[]) idColumns) {
//                queryString.append(alias).append(idColumn).append(COMMA);
//            }
//            queryString.setLength(queryString.length() - COMMA.length()); // remove last separator
//            queryString.append(CLOSE_PARENTHESES);
//
//        } else if (multiValueIn) {
//            queryString.append(OPEN_PARENTHESES).append(FAKE_VALUE).append(COMMA);
//            queryString.append(alias).append(idColumns);
//            queryString.append(CLOSE_PARENTHESES);
//
//        } else {
//            queryString.append(alias).append(idColumns);
//        }
//
//        queryString.append(IN).append(OPEN_PARENTHESES);
//
//        if (!compositeId && multiValueIn) {
//            for (final Object id : ids) {
//                queryString.append(OPEN_PARENTHESES).append(FAKE_VALUE).append(COMMA);
//                appendWhereValue(queryString, id);
//                queryString.append(CLOSE_PARENTHESES).append(COMMA);
//            }
//        } else {
//            for (final Object id : ids) {
//                appendWhereValue(queryString, id);
//                queryString.append(COMMA);
//            }
//        }
//
//        queryString.setLength(queryString.length() - COMMA.length()); // remove last separator
//        queryString.append(CLOSE_PARENTHESES);
//    }
//
//    public static void appendWhere(final StringBuilder queryString, @Nullable final String aliasPrefix,
//            final Object idColumns, final Collection<?> ids, final Object... auditValues) {
//
//        appendWhere(queryString, aliasPrefix, idColumns, ids);
//
//        int i = 0;
//        for (; i < auditValues.length; i++) {
//            Object value = auditValues[i];
//            if (value instanceof final CharSequence valueChar) {
//                queryString.append(AND).append(valueChar).append(NOT_EQUAL);
//                value = auditValues[++i];
//                if (NEW.equals(value)) {
//                    value = auditValues[++i];
//                }
//            }
//            appendWhereValue(queryString, value);
//        }
//    }
//
//    // -------------------------------------------------------------------------
//
//    public static String getString(final Tuple tuple, final String alias) {
//        return tuple.get(alias, String.class);
//    }
//
//    public static Integer getInteger(final Tuple tuple, final String alias) {
//        return Safe.toInteger(tuple.get(alias, Number.class));
//    }
//
//    public static Long getLong(final Tuple tuple, final String alias) {
//        return Safe.toLong(tuple.get(alias, Number.class));
//    }
//
//    public static Date getDate(final Tuple tuple, final String alias) {
//        return tuple.get(alias, Date.class);
//    }
//
//    public static boolean getBoolean(final Tuple tuple, final String alias) {
//        return Safe.isTrue(tuple.get(alias));
//    }
//
//    public static <T extends EnumValue<?>> T getEnum(final Tuple tuple, final String alias, final Class<T> enumType) {
//        return Safe.fromValue(enumType, tuple.get(alias, String.class));
//    }
//
//}
