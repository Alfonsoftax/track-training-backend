package com.track.training.app.interfaces;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Stream;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.NullHandling;
import org.springframework.data.domain.Sort.Order;
import org.springframework.util.Assert;

import com.google.common.collect.Lists;
import com.querydsl.core.FetchableQuery;
import com.querydsl.core.ResultTransformer;
import com.querydsl.core.group.GroupBy;
import com.querydsl.core.types.Expression;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.QBean;
import com.querydsl.core.types.dsl.DateExpression;
import com.querydsl.core.types.dsl.DslExpression;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.TemporalExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.track.training.app.utils.Safe;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * The Class CustomQueryUtils.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomQueryUtils {

    private static final ConcurrentMap<String, Expression<?>> pathCache = new ConcurrentHashMap<>(64);

    /**
     * Select.
     *
     * @param <I>
     *            the generic type
     * @param <T>
     *            the generic type
     * @param type
     *            the type
     * @param id
     *            the id
     * @param exprs
     *            the exprs
     * @return the result transformer
     */
    public static <I, T> ResultTransformer<Map<I, T>> select(final Path<T> type, final Expression<I> id,
            final Expression<?>... exprs) {
        return GroupBy.groupBy(id)
                .as(Projections.bean(type, Stream.concat(Stream.of(id), Stream.of(exprs)).toArray(Expression[]::new)));
    }

    /**
     * Fetch results.
     *
     * @param queryResult
     *            the query result
     * @return the results
     */
    @SuppressWarnings("unchecked")
    public static <T> List<T> fetch(final JPAQuery<?> query, final Object queryResult) {
        List<T> result;
        if (queryResult instanceof ResultTransformer<?>) {
            // transform & list results
            final Object transformed = query.transform((ResultTransformer<?>) queryResult);
            if (transformed instanceof Map) {
                result = new ArrayList<>(((Map<?, T>) transformed).values());
            } else if (transformed instanceof List) {
                result = (List<T>) transformed;
            } else if (transformed instanceof Iterable) {
                result = Lists.newArrayList((Iterable<T>) transformed);
            } else {
                result = new ArrayList<>();
                result.add((T) transformed);
            }

        } else if (queryResult instanceof FetchableQuery<?, ?>) {
            result = ((FetchableQuery<T, ?>) queryResult).fetch();

        } else if (queryResult instanceof List) {
            result = (List<T>) queryResult;

        } else if (queryResult instanceof Iterable) {
            result = Lists.newArrayList((Iterable<T>) queryResult);

        } else if (queryResult != null) { // unknown result
            result = Collections.emptyList();

        } else { // no transform
            result = (List<T>) query.fetch();
        }
        return result;
    }

    /**
     * Bean.
     *
     * @param <T>
     *            the generic type
     * @param type
     *            the type
     * @param exprs
     *            the exprs
     * @return the expression
     */
    public static <T> Expression<T> bean(final Path<T> type, final Expression<?>... exprs) {
        return bean(type, Projections.bean(type, exprs));
    }

    /**
     * Bean.
     *
     * @param <T>
     *            the generic type
     * @param type
     *            the type
     * @param bean
     *            the bean
     * @return the expression
     */
    public static <T> Expression<T> bean(final Path<T> type, final QBean<T> bean) {
        return ExpressionUtils.as(bean.skipNulls(), type);
    }

    /**
     * List.
     *
     * @param <T>
     *            the generic type
     * @param alias
     *            the alias
     * @param type
     *            the type
     * @param exprs
     *            the exprs
     * @return the dsl expression
     */
    public static <T> DslExpression<List<T>> list(final Path<List<T>> alias, final Path<T> type,
            final Expression<?>... exprs) {
        return GroupBy.list(Projections.bean(type, exprs).skipNulls()).as(alias);
    }

    /**
     * Set.
     *
     * @param <T>
     *            the generic type
     * @param alias
     *            the alias
     * @param type
     *            the type
     * @param exprs
     *            the exprs
     * @return the dsl expression
     */
    public static <T> DslExpression<Set<T>> set(final Path<Set<T>> alias, final Path<T> type,
            final Expression<?>... exprs) {
        return GroupBy.set(Projections.bean(type, exprs).skipNulls()).as(alias);
    }

    /**
     * Order.
     *
     * @param target
     *            the target
     * @return the order specifier
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static OrderSpecifier order(final Expression<?> target) {
        return new OrderSpecifier(com.querydsl.core.types.Order.ASC, target);
    }

    /**
     * Order.
     *
     * @param target
     *            the target
     * @param nullHandling
     *            the null handling
     * @return the order specifier
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static OrderSpecifier order(final Expression<?> target, final NullHandling nullHandling) {
        return new OrderSpecifier(com.querydsl.core.types.Order.ASC, target, nullHandling(nullHandling));
    }

    /**
     * Order.
     *
     * @param order
     *            the order
     * @param target
     *            the target
     * @return the order specifier
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static OrderSpecifier order(final Direction order, final Expression<?> target) {
        return new OrderSpecifier(
                order.isAscending() ? com.querydsl.core.types.Order.ASC : com.querydsl.core.types.Order.DESC, target);
    }

    /**
     * Order.
     *
     * @param order
     *            the order
     * @param target
     *            the target
     * @param nullHandling
     *            the null handling
     * @return the order specifier
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static OrderSpecifier order(final Direction order, final Expression<?> target,
            final NullHandling nullHandling) {
        return new OrderSpecifier(
                order.isAscending() ? com.querydsl.core.types.Order.ASC : com.querydsl.core.types.Order.DESC, target,
                nullHandling(nullHandling));
    }

    /**
     * Order.
     *
     * @param order
     *            the order
     * @param target
     *            the target
     * @return the order specifier
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static OrderSpecifier order(final Order order, final Expression<?> target) {
        return new OrderSpecifier(
                order.isAscending() ? com.querydsl.core.types.Order.ASC : com.querydsl.core.types.Order.DESC, target,
                nullHandling(order.getNullHandling()));
    }

    /**
     * Adds the order.
     *
     * @param custom
     *            the custom
     * @param property
     *            the property
     * @param expression
     *            the expression
     */
    public static void addOrder(final CustomQuery<?> custom, final String property, final Expression<?> expression) {

        final Sort sort = custom.getSort();
        final Order order = sort == null ? null : sort.getOrderFor(property);
        if (order != null) {
            custom.order(order, expression);
        }
    }

    /**
     * Path.
     *
     * @param <T>
     *            the generic type
     * @param qEntity
     *            the q entity
     * @param property
     *            the property
     * @return the t
     */
    @SuppressWarnings("unchecked")
    public static <T extends Expression<?>> T path(final EntityPathBase<?> qEntity, final String property) {
        return (T) pathCache.computeIfAbsent(qEntity + property, k -> Safe.getNestedProperty(qEntity, property));
    }

    /**
     * To query dsl null handling.
     *
     * @param nullHandling
     *            the null handling
     * @return the null handling
     */
    public static OrderSpecifier.NullHandling nullHandling(final NullHandling nullHandling) {
        Assert.notNull(nullHandling, "NullHandling must not be null!");
        return switch (nullHandling) {
            case NULLS_FIRST -> OrderSpecifier.NullHandling.NullsFirst;
            case NULLS_LAST -> OrderSpecifier.NullHandling.NullsLast;
            case NATIVE -> OrderSpecifier.NullHandling.Default;
            default -> OrderSpecifier.NullHandling.Default;
        };
    }

    public static DateExpression<Date> trunc(final TemporalExpression<Date> expression) {
        return Expressions.dateTemplate(Date.class, "TRUNC({0}, DAY)", expression);
    }

}
