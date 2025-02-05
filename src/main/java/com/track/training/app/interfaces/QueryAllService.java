package com.track.training.app.interfaces;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Sort.Order;

import com.track.training.app.serialization.SearchRequest;

/**
 * Interface for services that perform queries on given entities.
 *
 * @param <T>
 *            The entity type
 * @param <I>
 *            the generic type
 * @param <F>
 *            The filter type
 */
public interface QueryAllService<T, I extends Serializable, F extends Serializable>
        extends QueryService<T, I, SearchRequest<F>> {

    /**
     * Exists.
     *
     * @param identifier
     *            the identifier
     * @return true, if successful
     */
    boolean exists(I identifier);

    /**
     * Find a single entity from a given filter.
     *
     * @param filter
     *            the filter
     * @return the entity
     */
    T findOne(F filter);

    /**
     * Load a single entity from the identifier.
     *
     * @param identifier
     *            The identifier
     * @param properties
     *            the properties
     * @return the entity
     */
    T load(I identifier, String... properties);

    /**
     * Returns all of the entities from the database.
     *
     * @param filter
     *            the filter
     * @param order
     *            the order
     * @return the list
     */
    List<T> findAll(F filter, Order... order);

    /**
     * Returns the number of entities from the database.
     *
     * @param filter
     *            the filter
     * @return the count
     */
    long count(F filter);

}
