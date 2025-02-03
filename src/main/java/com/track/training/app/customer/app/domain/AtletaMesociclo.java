package com.track.training.app.customer.app.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/** */

@Entity
@Table(name = "ATLETA_MESOCICLO")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class AtletaMesociclo implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ATLETA", referencedColumnName = "ID", nullable = false)
    private Atleta atleta;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_MESOCICLO", referencedColumnName = "ID", nullable = false)
    private Mesociclo mesociclo;

    @Column(name = "FECHA_ASIGNACION", nullable = false)
    private Timestamp fechaAsignacion;

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
