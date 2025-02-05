package com.track.training.app.interfaces;

import java.util.List;

import com.track.training.app.general.GeneralData;


/**
 * Interface for services that save entities to the database.
 *
 * @param <T>
 *            The entity type
 */
public interface SecureSaveService<T> {

    /**
     * Save an entity to the database.
     *
     * @param entity
     *            The entity to be saved
     * @param generalData
     *            The general data
     * @return The saved entity
     */
    T save(T entity, GeneralData generalData);

    /**
     * Save a list of entities to the database.
     *
     * @param entity
     *            The entities to save
     * @param generalData
     *            The general data
     * @return The updated entities
     */
    List<T> save(Iterable<T> entity, GeneralData generalData);

    /**
     * Updates an entity in the database.
     *
     * @param entity
     *            The entity to update
     * @param generalData
     *            The general data
     * @return The updated entity
     */
    T update(T entity, GeneralData generalData);

}
