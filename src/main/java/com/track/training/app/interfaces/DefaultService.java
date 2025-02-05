package com.track.training.app.interfaces;

import java.io.Serializable;

/**
 * Interface for default services.
 *
 * @param <T>
 *            The entity type
 * @param <I>
 *            the generic type
 * @param <F>
 *            The filter type
 */
public interface DefaultService<T extends DomainEntity<I>, I extends Serializable, F extends Serializable>
        extends QueryAllService<T, I, F>, SecureSaveService<T>, SecureDeleteBatchService<T, I> {

}
