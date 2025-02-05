package com.track.training.app.atleta.mapper;

import org.mapstruct.Mapper;

import com.track.training.app.customer.adapters.web.mappers.BaseMapper;
import com.track.training.app.customer.app.domain.Atleta;
import com.track.training.app.customer.core.inbound.dtos.AtletaDto;

@Mapper
public interface AtletaMapper
        extends BaseMapper<Atleta, AtletaDto> {

}