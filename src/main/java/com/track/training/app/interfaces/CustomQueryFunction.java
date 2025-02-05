package com.track.training.app.interfaces;

import java.util.function.Function;


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
