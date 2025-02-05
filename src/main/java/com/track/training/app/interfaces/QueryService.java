package com.track.training.app.interfaces;

import java.io.Serializable;
import java.util.List;

import org.springframework.data.domain.Page;

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
public interface QueryService<T, I extends Serializable, F extends SearchRequest<?>> {

    /**
     * Find a single entity from the identifier.
     *
     * @param identifier
     *            The identifier
     * @return The entity returned
     */
    T find(I identifier);

    /**
     * Find a paginated list of entities from a given filter.
     *
     * @param request
     *            The request
     * @return The paginated list
     */
    Page<T> findPaginated(F request);

    /**
     * Returns all of the entities from the database.
     *
     * @return the list
     */
    List<T> findAll();
}
