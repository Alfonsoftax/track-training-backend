package com.src.train.track.general.helper.seq;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.src.train.track.general.domain.DomainEntitySeq;


@NoRepositoryBean
public interface JpaSeqRepository<T extends DomainEntitySeq> extends JpaRepository<T, Long> {

}
