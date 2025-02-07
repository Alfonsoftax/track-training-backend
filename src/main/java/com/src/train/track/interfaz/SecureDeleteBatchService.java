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
public interface SecureDeleteBatchService<T, I extends Serializable> extends SecureDeleteService<T, I> {

    /**
     * Deletes entities in the database.
     *
     * @param identifiers
     *            The entities ids to delete
     * @param generalData
     *            The general data
     */
    void deleteAll(Iterable<I> identifiers, GeneralData generalData);
}
