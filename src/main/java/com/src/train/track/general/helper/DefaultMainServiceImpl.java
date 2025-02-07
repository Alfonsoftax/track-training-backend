package com.src.train.track.general.helper;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static com.src.train.track.general.Constants.MAX_IN_VALUES;

import org.aspectj.weaver.tools.Trace;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.data.querydsl.QSort;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.querydsl.core.types.Predicate;
import com.src.train.track.general.Constants;
import com.src.train.track.general.GeneralData;
import com.src.train.track.general.domain.DomainEntity;
import com.src.train.track.general.domain.TransactionType;
import com.src.train.track.general.serialization.PageImplHelper;
import com.src.train.track.general.serialization.SearchRequest;
import com.src.train.track.general.utils.PredicateBuilder;
import com.src.train.track.general.utils.Safe;
import com.src.train.track.interfaz.CustomQueryDslPredicateExecutor;
import com.src.train.track.interfaz.CustomQueryFunction;
import com.src.train.track.interfaz.DefaultService;

import jakarta.annotation.Nullable;



/**
 * The Class DefaultMainServiceImpl.
 *
 * @param <T>
 *            the generic type of entity
 * @param <I>
 *            the generic type of identifier
 * @param <F>
 *            the generic type of filter
 * @param <R>
 *            the generic type of repository
 */
public abstract class DefaultMainServiceImpl<T extends DomainEntity<I>, I extends Serializable, F extends Serializable, //
        R extends JpaRepository<T, I> & QuerydslPredicateExecutor<T>> implements DefaultService<T, I, F> {

    protected final Logger logger;

    protected final Class<T> entityClass;
    protected final String entityName;
    protected final String idName;
    protected final String filterName;

    protected String entityTable;
    protected boolean audit = false;

    protected R repository;

    protected CustomQueryDslPredicateExecutor<T> customRepository;
    protected CustomQueryFunction<T> customQuery;
    protected CustomQueryFunction<T> customQueryCount;
    protected boolean custom = false;



    // -------------------------------------------------------------------------

    @SuppressWarnings("unchecked")
    protected DefaultMainServiceImpl(final R repository) {
        this.logger = LoggerFactory.getLogger(this.getClass());

        this.entityClass = Safe.getGenericSuperclassType(this.getClass(), 0);
        final Class<I> idClass = Safe.getGenericSuperclassType(this.getClass(), 1);
        final Class<I> filterClass = Safe.getGenericSuperclassType(this.getClass(), 2);

        this.repository = repository;
        if (repository instanceof CustomQueryDslPredicateExecutor) {
            this.customRepository = (CustomQueryDslPredicateExecutor<T>) this.repository;
            this.customQuery = this.getCustomQuery();
            this.customQueryCount = this.getCustomQueryCount();
            this.custom = this.customQuery != null || CustomQueryFilter.class.isAssignableFrom(filterClass);
        }

        this.entityName = this.entityClass.getSimpleName();
        this.idName = idClass.getSimpleName();
        this.filterName = filterClass.getSimpleName();
    }

    // -------------------------------------------------------------------------

    @Override
    public boolean exists(final I identifier) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("exists(%s) - start", this.idName)); //$NON-NLS-1$
        }

        final boolean exists = this.repository.existsById(identifier);

        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("exists(%s) - end", this.idName)); //$NON-NLS-1$
        }
        return exists;
    }

    @Override
    public T find(final I identifier) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("find(%s) - start", this.idName)); //$NON-NLS-1$
        }

        final T entity;
        if (this.custom) {
            entity = this.customRepository.findOne(CustomQuery.builder(this.customQuery).filter(identifier));
        } else {
            entity = this.repository.findById(identifier).orElse(null);
        }

        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("find(%s) - end", this.idName)); //$NON-NLS-1$
        }
        return entity;
    }

    @Override
    public T findOne(final F filter) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("findOne(%s) - start", this.filterName)); //$NON-NLS-1$
        }

        final T entity;
        final Predicate predicate = this.getPredicate(filter);

        if (this.custom) {
            entity = this.customRepository
                    .findOne(CustomQuery.builder(this.customQuery).filter(filter).predicate(predicate));
        } else {
            entity = this.repository.findOne(predicate).orElse(null);
        }

        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("findOne(%s) - end", this.filterName)); //$NON-NLS-1$
        }
        return entity;
    }

    @Override
    public T load(final I identifier, final String... properties) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("load(%s, String...) - start", this.idName)); //$NON-NLS-1$
        }

        T entity;
        if (this.custom) {
            entity = this.customRepository
                    .findOne(CustomQuery.builder(this.customQuery).filter(identifier).customFetch(properties));
        } else {
            entity = this.repository.findById(identifier).orElse(null);
        }
        if (entity != null) {
            entity = this.initEntity(entity);
        }

        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("load(%s, String...) - end", this.idName)); //$NON-NLS-1$
        }
        return entity;
    }

    @Override
    public Page<T> findPaginated(final SearchRequest<F> request) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("findPaginated(SearchRequest<%s>) - start", this.filterName)); //$NON-NLS-1$
        }

        final Page<T> returnPage;
        final F filter = request.getFilter();
        final Predicate predicate = this.getPredicate(filter);
        final Pageable pageable = this.getPageable(request.getPageRequest());

        if (pageable.getPageSize() == Integer.MAX_VALUE) { // findAll
            final Sort sort = pageable.getSort();
            final List<T> resultList;
            final Iterable<T> result;

            if (this.custom) {
                result = this.customRepository
                        .findAll(CustomQuery.builder(this.customQuery).filter(filter).predicate(predicate), sort);
            } else {
                result = this.repository.findAll(predicate, sort);
            }
            if (result instanceof List) {
                resultList = (List<T>) result;
            } else {
                resultList = Lists.newArrayList(result);
            }
            returnPage = new PageImplHelper<>(resultList, request.getPageRequest(), resultList.size());

        } else {
            final Page<T> page;
            if (this.custom) {
                page = this.customRepository.findAll(CustomQuery.builder(this.customQuery, this.customQueryCount)
                        .filter(filter).predicate(predicate), pageable);
            } else {
                page = this.repository.findAll(predicate, pageable);
            }
            returnPage = this.getPage(page, pageable, request.getPageRequest());
        }

        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("findPaginated(SearchRequest<%s>) - end", this.filterName)); //$NON-NLS-1$
        }
        return returnPage;
    }

    @Override
    public List<T> findAll() {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("findAll() - start"); //$NON-NLS-1$
        }

        final List<T> returnList;

        if (this.custom) {
            returnList = this.customRepository.findAll(CustomQuery.builder(this.customQuery));
        } else {
            returnList = this.repository.findAll();
        }

        if (this.logger.isDebugEnabled()) {
            this.logger.debug("findAll() - end"); //$NON-NLS-1$
        }
        return returnList;
    }

    @Override
    public List<T> findAll(final F filter, final Order... orders) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("findAll(%s) - start", this.filterName)); //$NON-NLS-1$
        }

        final List<T> returnList;
        final Iterable<T> result;
        final Predicate predicate = this.getPredicate(filter);

        if (orders != null && orders.length > 0) {
            final Sort sort = this.getSort(Sort.by(orders));
            if (this.custom) {
                result = this.customRepository
                        .findAll(CustomQuery.builder(this.customQuery).filter(filter).predicate(predicate), sort);
            } else {
                result = this.repository.findAll(predicate, sort);
            }
        } else if (this.custom) {
            result = this.customRepository
                    .findAll(CustomQuery.builder(this.customQuery).filter(filter).predicate(predicate));
        } else {
            result = this.repository.findAll(predicate);
        }
        if (result instanceof List) {
            returnList = (List<T>) result;
        } else {
            returnList = Lists.newArrayList(result);
        }

        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("findAll(%s) - end", this.filterName)); //$NON-NLS-1$
        }
        return returnList;
    }

    @Override
    public long count(final F filter) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("count(%s) - start", this.filterName)); //$NON-NLS-1$
        }

        final Predicate predicate = this.getPredicate(filter);

        final long count = this.repository.count(predicate);

        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("count(%s) - end", this.filterName)); //$NON-NLS-1$
        }
        return count;
    }

    // -------------------------------------------------------------------------

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, transactionManager = Constants.TRANSACTION_MANAGER_MAIN)
    @Override
    public T save(final T entity, final GeneralData generalData) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("save(%s, GeneralData) - start", this.entityName)); //$NON-NLS-1$
        }

        generalData.setTransactionType(TransactionType.ANIADIR);
        final T persistedEntity = this.processEntity(entity, generalData);

        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("save(%s, GeneralData) - end", this.entityName)); //$NON-NLS-1$
        }
        return persistedEntity;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, transactionManager = Constants.TRANSACTION_MANAGER_MAIN)
    @Override
    public List<T> save(final Iterable<T> entities, final GeneralData generalData) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("save(Iterable<%s>, GeneralData) - start", this.entityName)); //$NON-NLS-1$
        }

        List<T> persistedEntities = null;
        if (entities != null) {
            final T entity = Safe.getFirst(entities);
            if (entity != null) {
                generalData.setTransactionType(
                        entity.getId() == null ? TransactionType.ANIADIR : TransactionType.MODIFICAR);
                persistedEntities = this.processEntities(entities, generalData);
            } else {
                persistedEntities = new ArrayList<>();
            }
        }

        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("save(Iterable<%s>, GeneralData) - end", this.entityName)); //$NON-NLS-1$
        }
        return persistedEntities;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, transactionManager = Constants.TRANSACTION_MANAGER_MAIN)
    @Override
    public T update(final T entity, final GeneralData generalData) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("update(%s, GeneralData) - start", this.entityName)); //$NON-NLS-1$
        }

        generalData.setTransactionType(TransactionType.MODIFICAR);
        final T persistedEntity = this.processEntity(entity, generalData);

        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("update(%s, GeneralData) - end", this.entityName)); //$NON-NLS-1$
        }
        return persistedEntity;
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, transactionManager = Constants.TRANSACTION_MANAGER_MAIN)
    @Override
    public void delete(final T entity, final GeneralData generalData) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("delete(%s, GeneralData) - start", this.entityName)); //$NON-NLS-1$
        }

        generalData.setTransactionType(TransactionType.BORRAR);
        this.beforeDelete(entity, generalData);
        this.processEntity(entity, generalData);
        this.afterDelete(entity, generalData);

        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("delete(%s, GeneralData) - end", this.entityName)); //$NON-NLS-1$
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, transactionManager = Constants.TRANSACTION_MANAGER_MAIN)
    @Override
    public void delete(final I identifier, final GeneralData generalData) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("delete(%s, GeneralData) - start", this.idName)); //$NON-NLS-1$
        }

        final T entity = this.repository.findById(identifier).orElse(null);

        generalData.setTransactionType(TransactionType.BORRAR);
        this.beforeDelete(entity, generalData);
        this.processEntity(entity, generalData);
        this.afterDelete(entity, generalData);

        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("delete(%s, GeneralData) - end", this.idName)); //$NON-NLS-1$
        }
    }

    @Transactional(propagation = Propagation.REQUIRED, readOnly = false, transactionManager = Constants.TRANSACTION_MANAGER_MAIN)
    @Override
    public void deleteAll(final Iterable<I> identifiers, final GeneralData generalData) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("deleteAll(Iterable<%s>, GeneralData) - start", this.idName)); //$NON-NLS-1$
        }

        generalData.setTransactionType(TransactionType.BORRAR);
        this.internalDelete(identifiers, generalData);

        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("deleteAll(Iterable<%s>, GeneralData) - end", this.idName)); //$NON-NLS-1$
        }
    }

    // -------------------------------------------------------------------------



    // -------------------------------------------------------------------------

    protected Predicate getPredicate(final F filter) {
        return PredicateBuilder.getPredicate(filter != null ? this.getSearchPredicate(filter) : null);
    }

    protected Sort getSort(final Sort sort) {
        return sort;
    }

    protected Pageable getPageable(Pageable pageable) {
        final Sort sort = this.getSort(pageable.getSort());
        if (sort instanceof final QSort qSort) {
            pageable = QPageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), qSort);
        }
        return pageable;
    }

    protected Page<T> getPage(Page<T> page, final Pageable pageable, final Pageable originalPageable) {
        if (pageable instanceof QPageRequest) {
            page = new PageImplHelper<>(page.getContent(), originalPageable, page.getTotalElements());
        }
        return page;
    }

    // -------------------------------------------------------------------------

    protected T initEntity(final T entity) {
        return entity;
    }

    protected T initOldEntity(final I identifier) {
        return this.initOldEntity(this.repository.findById(identifier).orElse(null));
    }

    protected T initOldEntity(final T oldEntity) {
        return oldEntity;
    }

    protected void preSave(final T entity, final GeneralData generalData) {
        // to override
    }

    protected void preSave(final Iterable<T> entities, final GeneralData generalData) {
        // to override
    }

    protected T internalSave(final T entity, final GeneralData generalData) {
        this.preSave(entity, generalData);

        final T persistedEntity = this.repository.save(entity);

        this.postSave(entity, persistedEntity, generalData);

        return persistedEntity;
    }

    protected List<T> internalSave(final Iterable<T> entities, final GeneralData generalData) {
        this.preSave(entities, generalData);

        final List<T> persistedEntities = this.repository.saveAll(entities);

        this.postSave(entities, persistedEntities, generalData);

        return persistedEntities;
    }

    protected void postSave(final T entity, final T persistedEntity, final GeneralData generalData) {
        // to override
    }

    protected void postSave(final Iterable<T> entities, final Iterable<T> persistedEntities,
            final GeneralData generalData) {
        // to override
    }

    // -------------------------------------------------------------------------

    protected void preUpdate(final T entity, final T oldEntity, final GeneralData generalData) {
        // to override
    }

    protected void preUpdate(final Iterable<T> entities, final GeneralData generalData) {
        // to override
    }

    protected T internalUpdate(final T entity, final GeneralData generalData) {
        final T oldEntity = generalData.getOldEntity();
        generalData.setOldEntity(null); // clear old entity

        this.preUpdate(entity, oldEntity, generalData);

        final T persistedEntity = this.repository.save(entity);

        this.postUpdate(entity, oldEntity, persistedEntity, generalData);

        if (this.audit) {

            this.auditPostUpdate(entity, oldEntity, persistedEntity, generalData);
        }
        return persistedEntity;
    }

    protected List<T> internalUpdate(final Iterable<T> entities, final GeneralData generalData) {
        this.preUpdate(entities, generalData);

        final List<T> persistedEntities = this.repository.saveAll(entities);

        this.postUpdate(entities, persistedEntities, generalData);

        return persistedEntities;
    }

    protected void postUpdate(final T entity, final T oldEntity, final T persistedEntity,
            final GeneralData generalData) {
        // to override
    }

    protected void postUpdate(final Iterable<T> entities, final Iterable<T> persistedEntities,
            final GeneralData generalData) {
        // to override
    }

    // -------------------------------------------------------------------------

    protected void beforeDelete(final T entity, final GeneralData generalData) {
        // to override
    }

    protected void preDelete(final T entity, final GeneralData generalData) {
        // to override
    }

    protected void preDelete(final Iterable<I> identifiers, final GeneralData generalData) {
        // to override
    }

    protected T internalDelete(final T entity, final GeneralData generalData) {
        this.preDelete(entity, generalData);

        this.repository.delete(entity);

        this.postDelete(entity, generalData);
        
        return entity;
    }

    protected void internalDelete(final Iterable<I> identifiers, final GeneralData generalData) {
        this.preDelete(identifiers, generalData);

        for (final List<I> partList : Iterables.partition(identifiers, MAX_IN_VALUES)) {
            this.repository.deleteAllByIdInBatch(partList);
        }

        this.postDelete(identifiers, generalData);

    }

    protected void postDelete(final T entity, final GeneralData generalData) {
        // to override
    }

    protected void postDelete(final Iterable<I> identifiers, final GeneralData generalData) {
        // to override
    }

    protected void afterDelete(final T entity, final GeneralData generalData) {
        // to override
    }

    // -------------------------------------------------------------------------

    protected T processEntity(final T entity, final GeneralData generalData) {

        T oldEntity = null;
        if (TransactionType.MODIFICAR.equals(generalData.getTransactionType())) {
            if (generalData.getOldEntity() == null) {
                // to correct audit changes when entity already fetched
                oldEntity = generalData.setOldEntity(this.initOldEntity(entity.getId()));
            } else {
                oldEntity = generalData.getOldEntity();
            }
        }

        if (generalData.validateEntity()) {
            this.validate(entity, generalData);
        }

        final T persistedEntity;
        if (TransactionType.BORRAR.equals(generalData.getTransactionType())) {
            persistedEntity = this.internalDelete(entity, generalData);

        } else if (TransactionType.ANIADIR.equals(generalData.getTransactionType())) {
            persistedEntity = this.internalSave(entity, generalData);

        } else { // TransactionType.MODIFICAR
            persistedEntity = this.internalUpdate(entity, generalData);
        }

        this.postProcessEntity(entity, oldEntity, persistedEntity, generalData);

        return persistedEntity;
    }

    protected void postProcessEntity(final T entity, final T oldEntity, final T persistedEntity,
            final GeneralData generalData) {
        // To override
    }

    protected List<T> processEntities(final Iterable<T> entities, final GeneralData generalData) {

        if (generalData.validateEntity() && generalData.getBatchSize() == null) {
            for (final T entity : entities) {
                this.validate(entity, generalData);
            }
        }

        List<T> persistedEntities = null;
        if (TransactionType.ANIADIR.equals(generalData.getTransactionType())) {
            persistedEntities = this.internalSave(entities, generalData);

        } else { // TransactionType.MODIFICAR
            persistedEntities = this.internalUpdate(entities, generalData);
        }
        return persistedEntities;
    }

    protected void validate(final T entity, final GeneralData generalData) {
        // To override
        this.validate(entity, generalData, null); // NOSONAR
    }

    /**
     * @deprecated Use {@link #validate(T, GeneralData)}
     */
    @Deprecated(forRemoval = true)
    protected void validate(final T entity, final GeneralData generalData, final @Nullable Trace trace) { // NOSONAR
        // To override
    }

    // -------------------------------------------------------------------------

    // -------------------------------------------------------------------------

    protected Map<I, T> findOldEntities(final Iterable<T> entities) {
        final Map<I, T> oldEntitiesMap = new HashMap<>();
        final List<I> ids = new ArrayList<>();
        for (final T entity : entities) {
            ids.add(entity.getId());
        }
        for (final List<I> partList : Lists.partition(ids, MAX_IN_VALUES)) {
            for (final T oldEntity : this.repository.findAllById(partList)) {
                oldEntitiesMap.put(oldEntity.getId(), Safe.deepCopy(oldEntity));
            }
        }
        return oldEntitiesMap;
    }

    protected void auditPostUpdate(final T entity, final T oldEntity, final T persistedEntity,
            final GeneralData generalData) {
        // To override
    }


    // -------------------------------------------------------------------------

    protected CustomQueryFunction<T> getCustomQuery() {
        return null; // override only if repository is CustomQueryDslPredicateExecutor
    }

    protected CustomQueryFunction<T> getCustomQueryCount() {
        return null; // override only if repository is CustomQueryDslPredicateExecutor
    }

    protected abstract Predicate getSearchPredicate(F filter);

    // -------------------------------------------------------------------------



}
