package com.src.train.track.general.domain;

import org.springframework.data.domain.Persistable;

public interface DomainEntitySeq extends DomainEntity<Long>, Persistable<Long> {

    String NEW = "new";

    void setId(Long id);

    void setNew(boolean isNew);

    String getSeqName();

}
