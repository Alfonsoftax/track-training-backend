package com.track.training.app.interfaces;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;


@NoRepositoryBean
public interface JpaSeqRepository<T extends DomainEntitySeq> extends JpaRepository<T, Long> {

}
