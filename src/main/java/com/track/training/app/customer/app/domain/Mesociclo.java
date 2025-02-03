package com.track.training.app.customer.app.domain;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.track.training.app.customer.core.domain.Customer;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/** */

@Entity
@Table(name = "MESOCICLO")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Mesociclo implements Serializable {

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_mesociclo_id")
    @SequenceGenerator(name = "seq_mesociclo_id", sequenceName = "seq_mesociclo_id", allocationSize = 1)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "DESCRIPCION", nullable = false, length = 50)
    private String descripcion;

    @Column(name = "FECHA_INICIO", nullable = false)
    private Timestamp fechaInicio;

    @Column(name = "FECHA_FIN")
    private Timestamp fechaFin;

    @Column(name = "LAST_MODIFIED_BY", length = 50)
    private String lastModifiedBy;

    @Column(name = "LAST_MODIFIED_AT")
    private Timestamp lastModifiedAt;

    @Column(name = "CREATED_BY", length = 50)
    private String createdBy;

    @Column(name = "CREATED_AT")
    private Timestamp createdAt;

    @OneToMany(mappedBy = "mesociclo", fetch = FetchType.LAZY)
    private List<AtletaMesociclo> atletas;

    @OneToMany(mappedBy = "mesociclo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MesoMicro> microciclos;
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        Mesociclo other = (Mesociclo) o;
        return getId() != null && getId().equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
