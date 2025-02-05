package com.track.training.app.utils;

import static org.apache.commons.lang3.BooleanUtils.isTrue;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.StringUtils.leftPad;

import java.util.Collection;
import java.util.Date;

import jakarta.annotation.Nullable;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.Visitor;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.core.types.dsl.SimpleExpression;
import com.querydsl.core.types.dsl.StringExpression;
import com.querydsl.core.types.dsl.StringExpressions;
import com.querydsl.core.types.dsl.TemporalExpression;
import com.track.training.app.interfaces.DomainEntityCode;



public final class PredicateBuilder implements Predicate {

    private static final long serialVersionUID = 5873848371031039405L;

    private final BooleanBuilder builder;

    // -------------------------------------------------------------------------

    public PredicateBuilder() {
        this.builder = new BooleanBuilder();
    }

    public PredicateBuilder(final Predicate initial) {
        this.builder = new BooleanBuilder(initial);
    }

    // -------------------------------------------------------------------------

    public <T> PredicateBuilder eq(final SimpleExpression<T> simpleExpression, final T value) {
        if (value != null) {
            this.builder.and(simpleExpression.eq(value));
        }
        return this;
    }

    public <T> PredicateBuilder ne(final SimpleExpression<T> simpleExpression, final T value) {
        if (value != null) {
            this.builder.and(simpleExpression.ne(value));
        }
        return this;
    }

    public PredicateBuilder eq(final StringExpression stringExpression, final String value) {
        return this.eq(stringExpression, value, false);
    }

    public PredicateBuilder eq(final StringExpression stringExpression, final String value, final boolean ignoreCase) {
        if (isNotBlank(value)) {
            if (ignoreCase) {
                this.builder.and(stringExpression.equalsIgnoreCase(value));
            } else {
                this.builder.and(stringExpression.eq(value));
            }
        }
        return this;
    }

    public PredicateBuilder ne(final StringExpression stringExpression, final String value) {
        return this.ne(stringExpression, value, false);
    }
    public static boolean isNotEmpty(Collection coll) {
        return isEmpty(coll);
    }
    
    public static boolean isEmpty(Collection coll) {
        return (coll == null || coll.isEmpty());
    }
    public PredicateBuilder ne(final StringExpression stringExpression, final String value, final boolean ignoreCase) {
        if (isNotBlank(value)) {
            if (ignoreCase) {
                this.builder.and(stringExpression.notEqualsIgnoreCase(value));
            } else {
                this.builder.and(stringExpression.ne(value));
            }
        }
        return this;
    }

    public PredicateBuilder eq(final StringExpression stringExpression, final DomainEntityCode entity) {
        return this.eq(stringExpression, Safe.getCode(entity), false);
    }

    public PredicateBuilder ne(final StringExpression stringExpression, final DomainEntityCode entity) {
        return this.ne(stringExpression, Safe.getCode(entity), false);
    }

    public PredicateBuilder neOrNull(final StringExpression stringExpression, final String value) {
        return this.neOrNull(stringExpression, value, false);
    }

    public PredicateBuilder neOrNull(final StringExpression stringExpression, final String value,
            final boolean ignoreCase) {
        if (isNotBlank(value)) {
            if (ignoreCase) {
                this.builder.and(stringExpression.isNull().or(stringExpression.notEqualsIgnoreCase(value)));
            } else {
                this.builder.and(stringExpression.isNull().or(stringExpression.ne(value)));
            }
        }
        return this;
    }

    public PredicateBuilder eqAny(final StringExpression stringExpression1, final StringExpression stringExpression2,
            final String value) {
        return this.eqAny(stringExpression1, stringExpression2, value, false);
    }

    public PredicateBuilder eqAny(final StringExpression stringExpression1, final StringExpression stringExpression2,
            final String value, final boolean ignoreCase) {
        if (isNotBlank(value)) {
            if (ignoreCase) {
                this.builder.andAnyOf(stringExpression1.equalsIgnoreCase(value),
                        stringExpression2.equalsIgnoreCase(value));
            } else {
                this.builder.andAnyOf(stringExpression1.eq(value), stringExpression2.eq(value));
            }
        }
        return this;
    }

    public PredicateBuilder contains(final StringExpression stringExpression, final String value) {
        return this.contains(stringExpression, value, false);
    }

    public PredicateBuilder contains(final StringExpression stringExpression, final String value,
            final boolean ignoreCase) {
        if (isNotBlank(value)) {
            if (ignoreCase) {
                this.builder.and(stringExpression.containsIgnoreCase(value));
            } else {
                this.builder.and(stringExpression.contains(value));
            }
        }
        return this;
    }

    public PredicateBuilder notContains(final StringExpression stringExpression, final String value) {
        return this.notContains(stringExpression, value, false);
    }

    public PredicateBuilder notContains(final StringExpression stringExpression, final String value,
            final boolean ignoreCase) {
        if (isNotBlank(value)) {
            if (ignoreCase) {
                this.builder.and(stringExpression.containsIgnoreCase(value).not());
            } else {
                this.builder.and(stringExpression.contains(value).not());
            }
        }
        return this;
    }

    public PredicateBuilder notContainsOrNull(final StringExpression stringExpression, final String value) {
        return this.notContainsOrNull(stringExpression, value, false);
    }

    public PredicateBuilder notContainsOrNull(final StringExpression stringExpression, final String value,
            final boolean ignoreCase) {
        if (isNotBlank(value)) {
            if (ignoreCase) {
                this.builder.and(stringExpression.isNull().or(stringExpression.containsIgnoreCase(value).not()));
            } else {
                this.builder.and(stringExpression.isNull().or(stringExpression.contains(value).not()));
            }
        }
        return this;
    }

    public PredicateBuilder containsAny(final StringExpression stringExpression1,
            final StringExpression stringExpression2, final String value) {
        return this.containsAny(stringExpression1, stringExpression2, value, false);
    }

    public PredicateBuilder containsAny(final StringExpression stringExpression1,
            final StringExpression stringExpression2, final String value, final boolean ignoreCase) {
        if (isNotBlank(value)) {
            if (ignoreCase) {
                this.builder.andAnyOf(stringExpression1.containsIgnoreCase(value),
                        stringExpression2.containsIgnoreCase(value));
            } else {
                this.builder.andAnyOf(stringExpression1.contains(value), stringExpression2.contains(value));
            }
        }
        return this;
    }

    public PredicateBuilder like(final StringExpression stringExpression, final String value) {
        return this.like(stringExpression, value, false);
    }

    public PredicateBuilder like(final StringExpression stringExpression, final String value,
            final boolean ignoreCase) {
        if (isNotBlank(value)) {
            if (ignoreCase) {
                this.builder.and(stringExpression.likeIgnoreCase(value));
            } else {
                this.builder.and(stringExpression.like(value));
            }
        }
        return this;
    }

    public <T> PredicateBuilder in(final SimpleExpression<T> expression, final Collection<? extends T> right) {
        if (isNotEmpty(right)) {
            this.builder.and(expression.in(right));
        }
        return this;
    }

    public <T> PredicateBuilder notIn(final SimpleExpression<T> expression, final Collection<? extends T> right) {
        if (isNotEmpty(right)) {
            this.builder.and(expression.notIn(right));
        }
        return this;
    }

    public <T extends Date> PredicateBuilder loe(final TemporalExpression<T> temporalExpression, final T value) {
        if (value != null) {
            this.builder.and(temporalExpression.loe(value));
        }
        return this;
    }

    public <T extends Date> PredicateBuilder goe(final TemporalExpression<T> temporalExpression, final T value) {
        if (value != null) {
            this.builder.and(temporalExpression.goe(value));
        }
        return this;
    }

    // -------------------------------------------------------------------------

    public <T extends Number & Comparable<?>> PredicateBuilder range(final NumberExpression<T> numberExpression,
            final T from, final T to) {
        if (from != null && to != null) {
            this.builder.and(numberExpression.between(from, to));
        } else {
            if (from != null) {
                this.builder.and(numberExpression.goe(from));
            }
            if (to != null) {
                this.builder.and(numberExpression.loe(to));
            }
        }
        return this;
    }

    public <T extends Number & Comparable<?>> PredicateBuilder range2(final NumberExpression<T> numberExpression,
            final T from, final T to) {
        if (from != null) {
            if (Safe.signum(from) == 0) {
                this.builder.and(numberExpression.isNull().or(numberExpression.goe(from)));
            } else {
                this.builder.and(numberExpression.goe(from));
            }
        }
        if (to != null) {
            if (Safe.signum(to) == 0) {
                this.builder.and(numberExpression.isNull().or(numberExpression.loe(to)));
            } else {
                this.builder.and(numberExpression.loe(to));
            }
        }
        return this;
    }

    public <T extends Date> PredicateBuilder range(final TemporalExpression<T> temporalExpression, final T from,
            final T to) {
        if (from != null && to != null) {
            this.builder.and(temporalExpression.between(from, to));
        } else {
            if (from != null) {
                this.builder.and(temporalExpression.goe(from));
            }
            if (to != null) {
                this.builder.and(temporalExpression.loe(to));
            }
        }
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T extends Date> PredicateBuilder range(final TemporalExpression<T> temporalExpression, T from, T to,
            final boolean ignoreTime) {
        if (from != null || to != null) {
            if (ignoreTime) {
                from = (T) DateUtil.getStartDate(from);
                to = (T) DateUtil.getEndDate(to);
            }
            this.range(temporalExpression, from, to);
        }
        return this;
    }

    public PredicateBuilder range(final StringExpression stringExpression, final String from, final String to) {
        if (isNotBlank(from) && isNotBlank(to)) {
            this.builder.and(stringExpression.between(from, to));
        } else {
            if (isNotBlank(from)) {
                this.builder.and(stringExpression.goe(from));
            }
            if (isNotBlank(to)) {
                this.builder.and(stringExpression.loe(to));
            }
        }
        return this;
    }

    public PredicateBuilder range(final StringExpression stringExpression, final String from, final String to,
            final int length) {
        if (isNotBlank(from) || isNotBlank(to)) {
            final StringExpression stringExpressionLpad = StringExpressions.lpad(stringExpression, length, '0');
            this.range(stringExpressionLpad, leftPad(from, length, '0'), leftPad(to, length, '0'));
        }
        return this;
    }

    public <T extends Date> PredicateBuilder overlaps(final TemporalExpression<T> temporalStartExpression,
            final TemporalExpression<T> temporalEndExpression, final T from, final T to) {
        if (from != null) {
            this.builder.and(temporalEndExpression.isNull().or(temporalEndExpression.goe(from)));
        }
        if (to != null) {
            this.builder.and(temporalStartExpression.loe(to));
        }
        return this;
    }

    @SuppressWarnings("unchecked")
    public <T extends Date> PredicateBuilder overlaps(final TemporalExpression<T> temporalStartExpression,
            final TemporalExpression<T> temporalEndExpression, T from, T to, final boolean ignoreTime) {
        if (from != null || to != null) {
            if (ignoreTime) {
                from = (T) DateUtil.getStartDate(from);
                to = (T) DateUtil.getEndDate(to);
            }
            this.overlaps(temporalStartExpression, temporalEndExpression, from, to);
        }
        return this;
    }


    // -------------------------------------------------------------------------

    public <T> PredicateBuilder filter(final SimpleExpression<T> path, final T value, final Boolean withoutField) {
        if (isTrue(withoutField)) {
            this.builder.and(path.isNull());
        } else {
            this.eq(path, value);
        }
        return this;
    }

    public <T> PredicateBuilder filter(final SimpleExpression<T> path, final T value, final Boolean withoutField,
            final Boolean withField) {
        if (isTrue(withoutField)) {
            this.builder.and(path.isNull());
        } else if (isTrue(withField)) {
            this.builder.and(path.isNotNull());
        } else {
            this.eq(path, value);
        }
        return this;
    }

    // -------------------------------------------------------------------------

    public PredicateBuilder and(@Nullable final Predicate right) {
        this.builder.and(right);
        return this;
    }

    public PredicateBuilder andAnyOf(final Predicate... args) {
        this.builder.andAnyOf(args);
        return this;
    }

    public PredicateBuilder andNot(final Predicate right) {
        this.builder.andNot(right);
        return this;
    }

    public PredicateBuilder or(@Nullable final Predicate right) {
        this.builder.or(right);
        return this;
    }

    public PredicateBuilder orAllOf(final Predicate... args) {
        this.builder.orAllOf(args);
        return this;
    }

    public PredicateBuilder orNot(final Predicate right) {
        this.builder.orNot(right);
        return this;
    }

    @Nullable
    public Predicate getValue() {
        return getPredicate(this.builder.getValue());
    }

    public boolean hasValue() {
        return this.builder.hasValue();
    }

    @Override
    public Predicate not() {
        return this.builder.not();
    }

    @Override
    public <R, C> R accept(final Visitor<R, C> v, final C context) {
        return this.builder.accept(v, context);
    }

    @Override
    public Class<? extends Boolean> getType() {
        return this.builder.getType();
    }

    @Override
    public boolean equals(final Object o) {
        return this.builder.equals(o);
    }

    @Override
    public int hashCode() {
        return this.builder.hashCode();
    }

    @Override
    public String toString() {
        return this.builder.toString();
    }

    // -------------------------------------------------------------------------

    public static Predicate getPredicate(final Predicate predicate) {
        return predicate == null ? new BooleanBuilder() : predicate;
    }

}
