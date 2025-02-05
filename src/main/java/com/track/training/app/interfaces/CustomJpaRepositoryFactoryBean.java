package com.track.training.app.interfaces;

import static org.springframework.data.querydsl.QuerydslUtils.QUERY_DSL_PRESENT;

import java.io.Serializable;
import java.lang.reflect.Field;

import jakarta.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.CrudMethodMetadata;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.jpa.repository.support.Querydsl;
import org.springframework.data.jpa.repository.support.QuerydslJpaPredicateExecutor;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.querydsl.EntityPathResolver;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryComposition.RepositoryFragments;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;
import org.springframework.util.ReflectionUtils;

import com.querydsl.core.types.EntityPath;
import com.querydsl.core.types.dsl.PathBuilder;


/**
 * The Class CustomJpaRepositoryFactoryBean.
 *
 * @param <R>
 *            the generic type
 * @param <T>
 *            the generic type
 * @param <I>
 *            the generic type
 */
public class CustomJpaRepositoryFactoryBean<R extends JpaRepository<T, I>, T extends DomainEntity<I>, I extends Serializable>
        extends JpaRepositoryFactoryBean<R, T, I> {

    private static final Field querydslField;
    static {
        querydslField = ReflectionUtils.findField(QuerydslJpaPredicateExecutor.class, "querydsl", Querydsl.class);
        ReflectionUtils.makeAccessible(querydslField);
    }

    /**
     * Instantiates a new custom query dsl jpa repository factory bean.
     *
     * @param repositoryInterface
     *            the repository interface
     */
    public CustomJpaRepositoryFactoryBean(final Class<? extends R> repositoryInterface) {
        super(repositoryInterface);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected RepositoryFactorySupport createRepositoryFactory(final EntityManager entityManager) {
        return new CustomJpaRepositoryFactory<T, I>(entityManager);
    }

    /**
     * A factory for creating CustomJpaRepository objects.
     */
    private static class CustomJpaRepositoryFactory<T extends DomainEntity<I>, I extends Serializable>
            extends JpaRepositoryFactory {

        /**
         * Instantiates a new custom query dsl jpa repository factory.
         *
         * @param entityManager
         *            the entity manager
         */
        public CustomJpaRepositoryFactory(final EntityManager entityManager) {
            super(entityManager);
        }

        @Override
        protected RepositoryFragments getRepositoryFragments(final RepositoryMetadata metadata,
                final EntityManager entityManager, final EntityPathResolver resolver,
                final CrudMethodMetadata crudMethodMetadata) {

            @SuppressWarnings("unchecked")
            final JpaEntityInformation<T, I> entityInformation = this
                    .getEntityInformation((Class<T>) metadata.getDomainType());
            final EntityPath<T> path = resolver.createPath(entityInformation.getJavaType());
            final PathBuilder<T> pathBuilder = new PathBuilder<>(path.getType(), path.getMetadata());
            final Querydsl querydsl = new QuerydslHibernate6(entityManager, pathBuilder);

            final QuerydslJpaPredicateExecutor<T> executor;
            if (this.isCustomQueryDslExecutor(metadata.getRepositoryInterface())) {
                executor = new CustomQueryDslJpaRepository<>(entityInformation, entityManager, resolver,
                        crudMethodMetadata, path, pathBuilder, querydsl);
            } else {
                executor = new QuerydslJpaPredicateExecutor<>(entityInformation, entityManager, resolver,
                        crudMethodMetadata);
            }
            ReflectionUtils.setField(querydslField, executor, querydsl);

            return RepositoryFragments.just(executor);
        }

        @Override
        protected Class<?> getRepositoryBaseClass(final RepositoryMetadata metadata) {
            if (this.isJpaSeqRepository(metadata.getRepositoryInterface())) {
                return SimpleJpaSeqRepository.class;
            }
            return SimpleJpaRepository.class;
        }

        /**
         * Checks if is custom query dsl executor.
         *
         * @param repositoryInterface
         *            the repository interface
         * @return true, if is custom query dsl executor
         */
        private boolean isCustomQueryDslExecutor(final Class<?> repositoryInterface) {
            return QUERY_DSL_PRESENT && CustomQueryDslPredicateExecutor.class.isAssignableFrom(repositoryInterface);
        }

        private boolean isJpaSeqRepository(final Class<?> repositoryInterface) {
            return JpaSeqRepository.class.isAssignableFrom(repositoryInterface);
        }
    }
}
