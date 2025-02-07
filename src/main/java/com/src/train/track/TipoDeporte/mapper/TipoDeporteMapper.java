package com.src.train.track.TipoDeporte.mapper;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import com.src.train.track.TipoDeporte.domain.TipoDeporte;
import com.src.train.track.TipoDeporte.domain.TipoDeporteDto;
import com.src.train.track.general.utils.BaseMapper;


@Mapper
public interface TipoDeporteMapper extends BaseMapper<TipoDeporte, TipoDeporteDto> {



    @AfterMapping
    default void fillEntity(final TipoDeporteDto dto, @MappingTarget final TipoDeporte entity) {

    }

    @AfterMapping
    default void fillDto(final TipoDeporte entity, @MappingTarget final TipoDeporteDto dto) {

    }
}
