package com.track.training.app.atleta.proxy.impl;

import org.springframework.stereotype.Component;

import com.track.training.app.atleta.mapper.AtletaMapper;
import com.track.training.app.atleta.proxy.AtletaProxy;
import com.track.training.app.atleta.service.AtletaService;
import com.track.training.app.customer.app.domain.Atleta;
import com.track.training.app.customer.core.inbound.dtos.AtletaDto;
import com.track.training.app.customer.core.inbound.dtos.AtletaFilter;
import com.track.training.app.interfaces.DefaultProxyImpl;

@Component
public class AtletaProxyImpl extends
        DefaultProxyImpl<AtletaDto, Atleta, Long, AtletaFilter, AtletaService>
        implements AtletaProxy {

    public AtletaProxyImpl(final AtletaService service,
            final AtletaMapper mapper) {
        super(service, mapper);
    }

}