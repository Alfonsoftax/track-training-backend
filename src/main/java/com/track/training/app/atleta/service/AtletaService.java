package com.track.training.app.atleta.service;

import com.track.training.app.customer.app.domain.Atleta;
import com.track.training.app.customer.core.inbound.dtos.AtletaFilter;
import com.track.training.app.interfaces.DefaultService;


public interface AtletaService
extends DefaultService<Atleta, Long, AtletaFilter> {

}

