package com.src.train.track.TipoDeporte.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.src.train.track.TipoDeporte.domain.TipoDeporte;
import com.src.train.track.interfaz.CustomQueryDslPredicateExecutor;

@Repository
public interface TipoDeporteRepository
        extends JpaRepository<TipoDeporte, Long>, CustomQueryDslPredicateExecutor<TipoDeporte> {

}
