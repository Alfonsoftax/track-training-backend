package com.track.training.app.atleta.predicate;

import com.querydsl.core.types.Predicate;
import com.track.training.app.customer.core.inbound.dtos.AtletaFilter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AtletaPredicate {

    public static Predicate searchPredicate(final AtletaFilter filter) {
    	
        return null;
    }
}

