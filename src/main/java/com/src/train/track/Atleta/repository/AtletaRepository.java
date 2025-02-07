package com.src.train.track.Atleta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.src.train.track.Atleta.domain.Atleta;
import com.src.train.track.interfaz.CustomQueryDslPredicateExecutor;

@Repository
public interface AtletaRepository
        extends JpaRepository<Atleta, Long>, CustomQueryDslPredicateExecutor<Atleta> {


}
