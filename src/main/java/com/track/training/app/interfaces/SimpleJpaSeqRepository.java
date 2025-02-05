package com.track.training.app.interfaces;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.EntityManager;
import jakarta.validation.constraints.NotEmpty;

import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.JpaEntityInformationSupport;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.google.common.collect.Iterables;
import com.track.training.app.utils.Safe;

@NoRepositoryBean
@Transactional(readOnly = true)
public class SimpleJpaSeqRepository<T extends DomainEntitySeq> extends SimpleJpaRepository<T, Long>
        implements JpaSeqRepositoryImplementation<T> {

    private final EntityManager entityManager;
    private String querySeqValue;
    private String querySeqValues;

    public SimpleJpaSeqRepository(final JpaEntityInformation<T, ?> entityInformation,
            final EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    public SimpleJpaSeqRepository(final Class<T> domainClass, final EntityManager entityManager) {
        this(JpaEntityInformationSupport.getEntityInformation(domainClass, entityManager), entityManager);
    }

    // -------------------------------------------------------------------------

    @Transactional
    @Override
    public <S extends T> S save(final S entity) {
        Assert.notNull(entity, "Entity must not be null");

        this.setIdIfNeeded(entity);
        final S persistedEntity = super.save(entity);
        persistedEntity.setNew(false);
        return persistedEntity;
    }

    @Transactional
    @Override
    public <S extends T> List<S> saveAll(final Iterable<S> entities) {
        Assert.notNull(entities, "Entities must not be null");

        this.setIdsIfNeeded(entities);
        final List<S> persistedEntities = new ArrayList<>();
        for (final S entity : entities) {
            final S persistedEntity = super.save(entity);
            persistedEntity.setNew(false);
            persistedEntities.add(persistedEntity);
        }
        return persistedEntities;
    }

    @Transactional
    @Override
    public <S extends T> S saveAndFlush(final S entity) {
        final S persistedEntity = this.save(entity);
        this.flush();
        return persistedEntity;
    }

    @Transactional
    @Override
    public <S extends T> List<S> saveAllAndFlush(final Iterable<S> entities) {
        final List<S> persistedEntities = this.saveAll(entities);
        this.flush();
        return persistedEntities;
    }

    // -------------------------------------------------------------------------

    protected <S extends T> void setIdIfNeeded(final S entity) {
        if (entity.getId() == null) {
            entity.setId(this.getSeqValue(entity));
            entity.setNew(true);
        }
    }

    protected <S extends T> void setIdsIfNeeded(final Iterable<S> entities) {
        final S firstEntity = Safe.getFirst(entities);
        if (firstEntity != null && firstEntity.getId() == null) {
            final List<Long> ids = this.getSeqValues(entities);
            int i = 0;
            for (final T entity : entities) {
                entity.setId(ids.get(i++));
                entity.setNew(true);
            }
        }
    }

    protected <S extends T> Long getSeqValue(final S entity) {
        if (this.querySeqValue == null) {
            this.querySeqValue = "SELECT " + entity.getSeqName() + ".NEXTVAL FROM DUAL";
        }
        final Session session = (Session) this.entityManager.getDelegate();
        final Query<Long> query = session.createNativeQuery(this.querySeqValue, Long.class);
        return query.getSingleResult();
    }

    protected <S extends T> List<Long> getSeqValues(@NotEmpty final Iterable<S> entities) {
        if (this.querySeqValues == null) {
            final S firstEntity = Safe.getFirst(entities);
            this.querySeqValues = "SELECT " + firstEntity.getSeqName() + ".NEXTVAL FROM DUAL CONNECT BY LEVEL <= ?1";
        }
        final Session session = (Session) this.entityManager.getDelegate();
        final Query<Long> query = session.createNativeQuery(this.querySeqValues, Long.class);
        query.setParameter(1, Iterables.size(entities));
        return query.getResultList();
    }

}
