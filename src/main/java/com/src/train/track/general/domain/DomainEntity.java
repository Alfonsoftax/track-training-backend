package com.src.train.track.general.domain;

import java.io.Serializable;

/**
 * The Interface DomainEntity.
 *
 * @param <I>
 *            the generic type
 */
public interface DomainEntity<I extends Serializable> extends Serializable {

    /**
     * Gets the id.
     *
     * @return the id
     */
    I getId();

}
