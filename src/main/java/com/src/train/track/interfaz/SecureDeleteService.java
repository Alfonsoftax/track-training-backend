package com.src.train.track.interfaz;

import java.io.Serializable;

import com.src.train.track.general.GeneralData;


/**
 * Interface for services that can delete entities from the database.
 *
 * @param <T>
 *            The entity type
 * @param <I>
 *            the generic type
 */
public interface SecureDeleteService<T, I extends Serializable> {

    /**
     * Deletes an entity in the database.
     *
     * @param entity
     *            The entity to delete
     * @param generalData
     *            The general data
     */
    void delete(T entity, GeneralData generalData);

    /**
     * Deletes an entity in the database.
     *
     * @param identifier
     *            The identifier of the entity
     * @param generalData
     *            The general data
     */
    void delete(I identifier, GeneralData generalData);
}
