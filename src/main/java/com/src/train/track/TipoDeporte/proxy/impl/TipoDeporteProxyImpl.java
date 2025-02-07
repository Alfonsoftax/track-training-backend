package com.src.train.track.TipoDeporte.proxy.impl;
import org.springframework.stereotype.Component;

import com.src.train.track.TipoDeporte.domain.TipoDeporte;
import com.src.train.track.TipoDeporte.domain.TipoDeporteDto;
import com.src.train.track.TipoDeporte.domain.TipoDeporteFilter;
import com.src.train.track.TipoDeporte.mapper.TipoDeporteMapper;
import com.src.train.track.TipoDeporte.proxy.TipoDeporteProxy;
import com.src.train.track.TipoDeporte.service.TipoDeporteService;
import com.src.train.track.general.helper.DefaultProxyImpl;

@Component
public class TipoDeporteProxyImpl
        extends DefaultProxyImpl<TipoDeporteDto, TipoDeporte, Long, TipoDeporteFilter, TipoDeporteService>
        implements TipoDeporteProxy {

    public TipoDeporteProxyImpl(final TipoDeporteService service, final TipoDeporteMapper mapper) {
        super(service, mapper);
    }

}
