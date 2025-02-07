package com.src.train.track.Atleta.proxy.impl;
import org.springframework.stereotype.Component;

import com.src.train.track.Atleta.domain.Atleta;
import com.src.train.track.Atleta.domain.AtletaDto;
import com.src.train.track.Atleta.domain.AtletaFilter;
import com.src.train.track.Atleta.mapper.AtletaMapper;
import com.src.train.track.Atleta.proxy.AtletaProxy;
import com.src.train.track.Atleta.service.AtletaService;
import com.src.train.track.general.helper.DefaultProxyImpl;

@Component
public class AtletaProxyImpl
        extends DefaultProxyImpl<AtletaDto, Atleta, Long, AtletaFilter, AtletaService>
        implements AtletaProxy {

    public AtletaProxyImpl(final AtletaService service, final AtletaMapper mapper) {
        super(service, mapper);
    }



}
