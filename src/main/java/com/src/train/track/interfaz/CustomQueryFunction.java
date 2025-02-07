package com.src.train.track.interfaz;

import java.util.function.Function;

import com.src.train.track.general.helper.CustomQuery;


/**
 * The Interface CustomQuery.
 *
 * @param <I>
 *            the generic type
 */
@FunctionalInterface
public interface CustomQueryFunction<T> extends Function<CustomQuery<T>, Object> {

    /**
     * {@inheritDoc}
     */
    @Override
    Object apply(CustomQuery<T> custom);

}
