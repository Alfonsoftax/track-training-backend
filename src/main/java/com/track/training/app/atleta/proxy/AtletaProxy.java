package com.track.training.app.atleta.proxy;

import com.track.training.app.customer.core.inbound.dtos.AtletaDto;
import com.track.training.app.customer.core.inbound.dtos.AtletaFilter;
import com.track.training.app.interfaces.DefaultProxy;


public interface AtletaProxy
extends DefaultProxy<AtletaDto, Long, AtletaFilter> {

}

