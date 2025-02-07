package com.src.train.track.general.helper;

import static com.src.train.track.general.helper.CustomQuery.ID;
import static com.src.train.track.general.helper.CustomQuery.Type.FIND_ALL;
import static com.src.train.track.general.helper.CustomQuery.Type.FIND_ONE;
import static com.src.train.track.general.helper.CustomQuery.Type.FIND_PAGINATED;

import java.io.Serializable;
import java.util.List;
import java.util.function.Consumer;

import org.hibernate.Session;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.CrudMethodMetadata;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.jpa.repository.support.QuerydslJpaPredicateExecutor;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.data.support.PageableExecutionUtils;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.AbstractJPAQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.src.train.track.general.domain.DomainEntity;
import com.src.train.track.general.helper.CustomQuery.CustomQueryBuilder;
import com.src.train.track.general.utils.Safe;
import com.src.train.track.interfaz.CustomQueryDslPredicateExecutor;
import com.src.train.track.general.helper.CustomQuery.Type;

import jakarta.persistence.EntityManager;



/**
 * The Class CustomQueryDslJpaRepository.
 *
 * @param <T>
 *            the generic type
 * @param <I>
 *            the generic type
 */
public class CustomQueryDslJpaRepository<T extends DomainEntity<I>, I extends Serializable>
        extends QuerydslJpaPredicateExecutor<T> implements CustomQueryDslPredicateExecutor<T> {

    /** The path. */
    protected final EntityPath<T> path;

    /** The builder. */
    protected final PathBuilder<T> pathBuilder;

    /** The querydsl. */
    protected final Querydsl querydsl;

    /** The em. */
    protected final EntityManager em;

    /**
     * Instantiates a new custom query dsl jpa repository.
     *
     * @param entityInformation
     *            the entity information
     * @param entityManager
     *            the entity manager
     * @param resolver
     *            the resolver
     * @param crudMethodMetadata
     *            the crud method metadata
     * @param path
     *            the path
     * @param pathBuilder
     *            the pathBuilder
     * @param querydsl
     *            the querydsl
     */
    public CustomQueryDslJpaRepository(final JpaEntityInformation<T, I> entityInformation,
            final EntityManager entityManager, final EntityPathResolver resolver,
            final CrudMethodMetadata crudMethodMetadata, final EntityPath<T> path, final PathBuilder<T> pathBuilder,
            final Querydsl querydsl) {
        super(entityInformation, entityManager, resolver, crudMethodMetadata);

        this.path = path;
        this.pathBuilder = pathBuilder;
        this.querydsl = querydsl;
        this.em = entityManager;
    }

    // -------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    @Override
    public T findOne(final CustomQueryBuilder<T> queryBuilder) {
        return Safe.getFirst(queryBuilder.build(this.configure(FIND_ONE)).getResults());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> findAll(final CustomQueryBuilder<T> queryBuilder) {
        return queryBuilder.build(this.configure(FIND_ALL)).getResults();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> findAll(final CustomQueryBuilder<T> queryBuilder, final Sort sort) {
        return queryBuilder.sort(sort).build(this.configure(FIND_ALL)).getResults();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("deprecation")
    @Override
    public Page<T> findAll(final CustomQueryBuilder<T> queryBuilder, final Pageable pageable) {
        final CustomQuery<T> custom = queryBuilder.pageable(pageable).build(this.configure(FIND_PAGINATED));
        final List<T> results = custom.getResults();

        if (custom.queryCount == null) {
            final JPQLQuery<?> countQuery = this.createCountQuery(custom.predicate);
            return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchCount);
        } else {
            return PageableExecutionUtils.getPage(results, pageable, custom.queryCount::fetchCount);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <U> JPAQuery<U> query(final EntityPath<?>... sources) {
        final AbstractJPAQuery<U, JPAQuery<U>> query = this.querydsl.createQuery();
        if (sources != null && sources.length > 0) {
            query.from(sources);
        } else {
            query.from(this.path);
        }
        return (JPAQuery<U>) query;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <U> List<U> findAllCustom(final CustomQueryBuilder<U> queryBuilder) {
        return queryBuilder.repository(this).build().getResults();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public <U> List<U> findAllCustom(final CustomQueryBuilder<U> queryBuilder, final Sort sort) {
        return queryBuilder.repository(this).sort(sort).build().getResults();
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("deprecation")
    @Override
    public <U> Page<U> findAllCustom(final CustomQueryBuilder<U> queryBuilder, final Pageable pageable) {
        final CustomQuery<U> custom = queryBuilder.pageable(pageable).repository(this).build();
        final List<U> results = custom.getResults();

        if (custom.queryCount == null) {
            final JPQLQuery<?> countQuery = this.createCountQuery(custom.predicate);
            return PageableExecutionUtils.getPage(results, pageable, countQuery::fetchCount);
        } else {
            return PageableExecutionUtils.getPage(results, pageable, custom.queryCount::fetchCount);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Session getSession() {
        return (Session) this.em.getDelegate();
    }

    /**
     * Configure custom query.
     *
     * @param type
     *            the type
     * @return the build consumer
     */
    protected Consumer<CustomQueryBuilder<T>> configure(final Type type) {
        return builder -> builder.repository(this).type(type).query(() -> {
            if (FIND_ONE == type && builder.getPredicate() == null) {
                final Object filter = builder.getFilter();
                return (JPAQuery<T>) this.createQuery(this.pathBuilder.get(ID).eq(filter)).select(this.path);
            }
            return (JPAQuery<T>) this.createQuery(builder.getPredicate()).select(this.path);

        }).queryCount(() -> {
            if (FIND_PAGINATED == type && builder.isCustomCount()) {
                return (JPAQuery<T>) this.createQuery(builder.getPredicate()).select(this.path);
            }
            return null;
        });
    }

}
