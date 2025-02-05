package com.track.training.app.interfaces;

import java.io.Serializable;

import com.track.training.app.general.GeneralData;


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
