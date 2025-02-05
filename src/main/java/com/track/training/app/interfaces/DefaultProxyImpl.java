package com.track.training.app.interfaces;

import java.io.Serializable;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort.Order;

import com.track.training.app.customer.adapters.web.mappers.BaseMapper;
import com.track.training.app.general.GeneralData;
import com.track.training.app.serialization.SearchRequest;
import com.track.training.app.utils.Safe;


/**
 * The Class DefaultProxyImpl.
 *
 * @param <D>
 *            the generic type of DTO
 * @param <T>
 *            the generic type of entity
 * @param <I>
 *            the generic type of identifier
 * @param <F>
 *            the generic type of filter
 * @param <S>
 *            the generic type of service
 */
public abstract class DefaultProxyImpl<D, T extends DomainEntity<I>, I extends Serializable, F extends Serializable, //
        S extends QueryAllService<T, I, F> & SecureSaveService<T> & SecureDeleteBatchService<T, I>>
        implements DefaultProxy<D, I, F> {

    /** Logger for this class. */
    protected final Logger logger;

    /** The DTO name. */
    protected final String dtoName;

    /** The id name. */
    protected final String idName;

    /** The filter name. */
    protected final String filterName;

    /** The service. */
    protected final S service;

    /** The mapper. */
    protected final BaseMapper<T, D> mapper;

    // -------------------------------------------------------------------------
    /**
     * Instantiates a new default service impl.
     *
     * @param service
     *            the service
     * @param mapper
     *            the mapper
     */
    protected DefaultProxyImpl(final S service, final BaseMapper<T, D> mapper) {
        this.logger = LoggerFactory.getLogger(this.getClass());

        final Class<D> dtoClass = Safe.getGenericSuperclassType(this.getClass(), 0);
        final Class<I> idClass = Safe.getGenericSuperclassType(this.getClass(), 2);
        final Class<F> filterClass = Safe.getGenericSuperclassType(this.getClass(), 3);

        this.dtoName = dtoClass.getSimpleName();
        this.idName = idClass.getSimpleName();
        this.filterName = filterClass.getSimpleName();

        this.service = service;
        this.mapper = mapper;
    }

    // -------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    @Override
    public boolean exists(final I identifier) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("exists(%s) - start", this.idName)); //$NON-NLS-1$
        }

        final boolean exists = this.service.exists(identifier);

        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("exists(%s) - end", this.idName)); //$NON-NLS-1$
        }
        return exists;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public D find(final I identifier) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("find(%s) - start", this.idName)); //$NON-NLS-1$
        }

        final D returnDto = this.mapper.convertToDto(this.service.find(identifier));

        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("find(%s) - end", this.idName)); //$NON-NLS-1$
        }
        return returnDto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public D findOne(final F filter) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("findOne(%s) - start", this.filterName)); //$NON-NLS-1$
        }

        final D returnDto = this.mapper.convertToDto(this.service.findOne(filter));

        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("findOne(%s) - end", this.filterName)); //$NON-NLS-1$
        }
        return returnDto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public D load(final I identifier, final String... properties) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("load(%s, String...) - start", this.idName)); //$NON-NLS-1$
        }

        final D returnDto = this.mapper.convertToDto(this.service.load(identifier, properties));

        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("load(%s, String...) - end", this.idName)); //$NON-NLS-1$
        }
        return returnDto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Page<D> findPaginated(final SearchRequest<F> request) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("findPaginated(SearchRequest<%s>) - start", this.filterName)); //$NON-NLS-1$
        }

        final Page<D> returnPage = this.mapper.convertPageToDto(this.service.findPaginated(request));

        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("findPaginated(SearchRequest<%s>) - end", this.filterName)); //$NON-NLS-1$
        }
        return returnPage;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<D> findAll() {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug("findAll() - start"); //$NON-NLS-1$
        }

        final List<D> returnList = this.mapper.convertListToDto(this.service.findAll());

        if (this.logger.isDebugEnabled()) {
            this.logger.debug("findAll() - end"); //$NON-NLS-1$
        }
        return returnList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<D> findAll(final F filter, final Order... orders) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("findAll(%s) - start", this.filterName)); //$NON-NLS-1$
        }

        final List<D> returnList = this.mapper.convertListToDto(this.service.findAll(filter, orders));

        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("findAll(%s) - end", this.filterName)); //$NON-NLS-1$
        }
        return returnList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public long count(final F filter) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("count(%s) - start", this.filterName)); //$NON-NLS-1$
        }

        final long count = this.service.count(filter);

        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("count(%s) - end", this.filterName)); //$NON-NLS-1$
        }
        return count;
    }

    // -------------------------------------------------------------------------
    /**
     * {@inheritDoc}
     */
    @Override
    public D save(final D entity, final GeneralData generalData) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("save(%s, GeneralData) - start", this.dtoName)); //$NON-NLS-1$
        }

        final D returnDto = this.mapper
                .convertToDto(this.service.save(this.mapper.convertFromDto(entity), generalData));

        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("save(%s, GeneralData) - end", this.dtoName)); //$NON-NLS-1$
        }
        return returnDto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<D> save(final Iterable<D> entities, final GeneralData generalData) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("save(Iterable<%s>, GeneralData) - start", this.dtoName)); //$NON-NLS-1$
        }

        final List<D> returnList = this.mapper
                .convertListToDto(this.service.save(this.mapper.convertListFromDto(entities), generalData));

        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("save(Iterable<%s>, GeneralData) - end", this.dtoName)); //$NON-NLS-1$
        }
        return returnList;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public D update(final D entity, final GeneralData generalData) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("update(%s, GeneralData) - start", this.dtoName)); //$NON-NLS-1$
        }

        final D returnDto = this.mapper
                .convertToDto(this.service.update(this.mapper.convertFromDto(entity), generalData));

        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("update(%s, GeneralData) - end", this.dtoName)); //$NON-NLS-1$
        }
        return returnDto;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(final D entity, final GeneralData generalData) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("delete(%s, GeneralData) - start", this.dtoName)); //$NON-NLS-1$
        }

        this.service.delete(this.mapper.convertFromDto(entity), generalData);

        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("delete(%s, GeneralData) - end", this.dtoName)); //$NON-NLS-1$
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void delete(final I identifier, final GeneralData generalData) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("delete(%s, GeneralData) - start", this.idName)); //$NON-NLS-1$
        }

        this.service.delete(identifier, generalData);

        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("delete(%s, GeneralData) - end", this.idName)); //$NON-NLS-1$
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void deleteAll(final Iterable<I> identifiers, final GeneralData generalData) {
        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("deleteAll(Iterable<%s>, GeneralData) - start", this.idName)); //$NON-NLS-1$
        }

        this.service.deleteAll(identifiers, generalData);

        if (this.logger.isDebugEnabled()) {
            this.logger.debug(String.format("deleteAll(Iterable<%s>, GeneralData) - end", this.idName)); //$NON-NLS-1$
        }
    }

}
