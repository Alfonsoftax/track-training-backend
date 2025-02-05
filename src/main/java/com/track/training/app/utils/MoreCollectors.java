package com.track.training.app.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class MoreCollectors {

    private MoreCollectors() {
        super();
    }

    public static <T, K, U> Collector<T, ?, Map<K, U>> toLinkedMap(final Function<? super T, ? extends K> keyMapper, // NOSONAR
            final Function<? super T, ? extends U> valueMapper) {
        return Collectors.toMap(keyMapper, valueMapper, (u, v) -> {
            throw new IllegalStateException(String.format("Duplicate key %s", u));
        }, LinkedHashMap::new);
    }

    public static <T, K, U> Collector<T, ?, Map<K, U>> toTreeMap(final Function<? super T, ? extends K> keyMapper, // NOSONAR
            final Function<? super T, ? extends U> valueMapper) {
        return Collectors.toMap(keyMapper, valueMapper, (u, v) -> {
            throw new IllegalStateException(String.format("Duplicate key %s", u));
        }, TreeMap::new);
    }

    public static <R> Predicate<R> not(final Predicate<R> predicate) {
        return predicate.negate();
    }

    public static <T, K> Collector<T, ?, Map<K, List<T>>> groupingByWithNullKeys( // NOSONAR
            final Function<? super T, ? extends K> classifier) {
        return Collectors.toMap(classifier, Collections::singletonList,
                (final List<T> oldList, final List<T> newEl) -> {
                    final List<T> newList = new ArrayList<>(oldList.size() + 1);
                    newList.addAll(oldList);
                    newList.addAll(newEl);
                    return newList;
                });
    }

}
