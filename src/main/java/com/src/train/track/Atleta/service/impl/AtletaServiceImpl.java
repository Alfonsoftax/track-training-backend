package com.src.train.track.Atleta.service.impl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Predicate;
import com.src.train.track.Atleta.domain.Atleta;
import com.src.train.track.Atleta.domain.AtletaFilter;
import com.src.train.track.Atleta.predicate.AtletaPredicate;
import com.src.train.track.Atleta.repository.AtletaRepository;
import com.src.train.track.Atleta.service.AtletaService;
import com.src.train.track.general.helper.DefaultServiceImpl;
import com.src.train.track.interfaz.LoaderProcessService;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class AtletaServiceImpl
        extends DefaultServiceImpl<Atleta, Long, AtletaFilter, AtletaRepository>
        implements AtletaService, LoaderProcessService {



    // -------------------------------------------------------------------------

    public AtletaServiceImpl(final AtletaRepository repository) {
        super(repository);
    }

    @Override
    protected Predicate getSearchPredicate(final AtletaFilter filter) {
    	return AtletaPredicate.searchPredicate(filter);
    }

}
