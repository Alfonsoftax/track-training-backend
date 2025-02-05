package com.track.training.app.interfaces;

import java.util.List;

import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.track.training.app.interfaces.CustomQuery.CustomQueryBuilder;

/**
 * The Interface CustomQueryDslPredicateExecutor.
 *
 * @param <T>
 *            the generic type
 */
public interface CustomQueryDslPredicateExecutor<T extends DomainEntity<?>> extends QuerydslPredicateExecutor<T> {

    /**
     * Retrieves an entity by its id.
     *
     * @param queryBuilder
     *            query builder used for select results
     * @return the entity with the given id or {@literal null} if none found
     * @throws IllegalArgumentException
     *             if {@code id} is {@literal null}
     */
    T findOne(CustomQueryBuilder<T> queryBuilder);

    /**
     * Returns all entities matching the given {@link Predicate}. In case no match could be found an empty
     * {@link Iterable} is returned.
     * This also uses provided projections (can be JavaBean or constructor or anything supported by QueryDSL)
     *
     * @param queryBuilder
     *            query builder used for select results
     * @return all entities matching the given {@link Predicate}.
     */
    List<T> findAll(CustomQueryBuilder<T> queryBuilder);

    /**
     * Returns all entities matching the given {@link Predicate} applying the given {@link Sort}. In case no match could
     * be found an empty {@link Iterable} is returned.
     * This also uses provided projections (can be JavaBean or constructor or anything supported by QueryDSL)
     *
     * @param queryBuilder
     *            query builder used for select results
     * @param sort
     *            the {@link Sort} specification to sort the results by, must not be {@literal null}.
     * @return all entities matching the given {@link Predicate}.
     */
    List<T> findAll(CustomQueryBuilder<T> queryBuilder, Sort sort);

    /**
     * Returns a {@link Page} of entities matching the given {@link Predicate}. In case no match could be found, an
     * empty {@link Page} is returned.
     * This also uses provided projections (can be JavaBean or constructor or anything supported by QueryDSL)
     *
     * @param queryBuilder
     *            query builder used for select results
     * @param pageable
     *            can be {@literal null}.
     * @return a {@link Page} of entities matching the given {@link Predicate}.
     */
    Page<T> findAll(CustomQueryBuilder<T> queryBuilder, Pageable pageable);

    /**
     * Creates a JPAQuery and add sources to this query.
     *
     * @param <U>
     *            the generic type
     * @param sources
     *            sources
     * @return the JPA query
     */
    <U> JPAQuery<U> query(EntityPath<?>... sources);

    /**
     * Find all custom.
     *
     * @param <U>
     *            the generic type
     * @param queryBuilder
     *            the query builder
     * @return the list
     */
    <U> List<U> findAllCustom(CustomQueryBuilder<U> queryBuilder);

    /**
     * Find all custom.
     *
     * @param <U>
     *            the generic type
     * @param queryBuilder
     *            the query builder
     * @param sort
     *            the sort
     * @return the list
     */
    <U> List<U> findAllCustom(CustomQueryBuilder<U> queryBuilder, Sort sort);

    /**
     * Find all custom.
     *
     * @param <U>
     *            the generic type
     * @param queryBuilder
     *            the query builder
     * @param pageable
     *            the pageable
     * @return the page
     */
    <U> Page<U> findAllCustom(CustomQueryBuilder<U> queryBuilder, Pageable pageable);

    /**
     * Gets the session.
     *
     * @return the session
     */
    Session getSession();

}
