package com.src.train.track.TipoDeporte.service.impl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.querydsl.core.types.Predicate;
import com.src.train.track.TipoDeporte.domain.TipoDeporte;
import com.src.train.track.TipoDeporte.domain.TipoDeporteFilter;
import com.src.train.track.TipoDeporte.predicate.TipoDeportePredicate;
import com.src.train.track.TipoDeporte.repository.TipoDeporteRepository;
import com.src.train.track.TipoDeporte.service.TipoDeporteService;
import com.src.train.track.general.helper.DefaultServiceImpl;
import com.src.train.track.interfaz.LoaderProcessService;

@Service
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true)
public class TipoDeporteServiceImpl
        extends DefaultServiceImpl<TipoDeporte, Long, TipoDeporteFilter, TipoDeporteRepository>
        implements TipoDeporteService, LoaderProcessService {



    // -------------------------------------------------------------------------

    public TipoDeporteServiceImpl(final TipoDeporteRepository repository) {
        super(repository);
    }

    @Override
    protected Predicate getSearchPredicate(final TipoDeporteFilter filter) {
    	return TipoDeportePredicate.searchPredicate(filter);
    }
}
