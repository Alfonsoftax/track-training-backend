package com.src.train.track.User.predicates;

import com.querydsl.core.types.Predicate;
import com.src.train.track.User.model.UserFilter;
import com.src.train.track.general.utils.PredicateBuilder;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserPredicates {

    public static Predicate searchPredicate(final UserFilter filter) {
        final PredicateBuilder builder = new PredicateBuilder();
        

        return builder.getValue();
    }
}
