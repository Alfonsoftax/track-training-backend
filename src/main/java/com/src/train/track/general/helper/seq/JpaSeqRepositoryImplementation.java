package com.src.train.track.general.helper.seq;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.query.EscapeCharacter;
import org.springframework.data.jpa.repository.support.CrudMethodMetadata;
import org.springframework.data.repository.NoRepositoryBean;

import com.src.train.track.general.domain.DomainEntitySeq;


@NoRepositoryBean
public interface JpaSeqRepositoryImplementation<T extends DomainEntitySeq>
        extends JpaSeqRepository<T>, JpaSpecificationExecutor<T> {

    /**
     * Configures the {@link CrudMethodMetadata} to be used with the repository.
     *
     * @param crudMethodMetadata
     *            must not be {@literal null}.
     */
    void setRepositoryMethodMetadata(CrudMethodMetadata crudMethodMetadata);

    /**
     * Configures the {@link EscapeCharacter} to be used with the repository.
     *
     * @param escapeCharacter
     *            Must not be {@literal null}.
     */
    default void setEscapeCharacter(final EscapeCharacter escapeCharacter) {

    }
}
