package com.src.train.track.interfaz;

import java.io.Serializable;

import com.src.train.track.general.domain.DomainEntity;

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
