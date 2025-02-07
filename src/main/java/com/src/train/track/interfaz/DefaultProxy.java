package com.src.train.track.interfaz;

import java.io.Serializable;

/**
 * Interface for default proxies.
 *
 * @param <D>
 *            the generic type of DTO
 * @param <I>
 *            the generic type of identifier
 * @param <F>
 *            the generic type of filter
 */
public interface DefaultProxy<D, I extends Serializable, F extends Serializable>
        extends QueryAllService<D, I, F>, SecureSaveService<D>, SecureDeleteBatchService<D, I> {

}
