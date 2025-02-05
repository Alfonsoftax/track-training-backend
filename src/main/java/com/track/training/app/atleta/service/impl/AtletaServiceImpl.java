package com.track.training.app.atleta.service.impl;

import org.springframework.stereotype.Service;

import com.track.training.app.atleta.predicate.AtletaPredicate;
import com.track.training.app.atleta.repository.AtletaRepository;
import com.track.training.app.atleta.service.AtletaService;
import com.track.training.app.customer.app.domain.Atleta;
import com.track.training.app.customer.core.inbound.dtos.AtletaFilter;
import com.track.training.app.interfaces.DefaultService;
import com.track.training.app.serialization.DefaultServiceImpl;
import com.querydsl.core.types.Predicate;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;


@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AtletaServiceImpl extends
        DefaultServiceImpl<Atleta, Long, AtletaFilter, AtletaRepository>
        implements AtletaService {

    public AtletaServiceImpl(final AtletaRepository repository) {
        super(repository);
    }

    @Override
    protected Predicate getSearchPredicate(final AtletaFilter filter) {
        return AtletaPredicate.searchPredicate(filter);
    }

}

