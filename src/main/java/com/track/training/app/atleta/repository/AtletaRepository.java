package com.track.training.app.atleta.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.track.training.app.customer.app.domain.Atleta;
import com.track.training.app.interfaces.CustomQueryDslPredicateExecutor;


@Repository
public interface AtletaRepository extends JpaRepository<Atleta, Long>,
        CustomQueryDslPredicateExecutor<Atleta> {

}

