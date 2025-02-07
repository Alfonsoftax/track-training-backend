package com.src.train.track.Microciclo;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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
@Table(name = "MICROCICLO")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Microciclo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MICROCICLO_ID")
    @SequenceGenerator(name = "SEQ_MICROCICLO_ID", sequenceName = "SEQ_MICROCICLO_ID", allocationSize = 1)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "DESCRIPCION", length = 70, nullable = false)
    private String descripcion;

    @Column(name = "FECHA_INICIO", nullable = false)
    private Timestamp fechaInicio;

    @Column(name = "FECHA_FIN")
    private Timestamp fechaFin;

    @Column(name = "ORDEN", nullable = false)
    private Integer orden;

    @Column(name = "LAST_MODIFIED_BY", length = 50)
    private String lastModifiedBy;

    @Column(name = "LAST_MODIFIED_AT")
    private Timestamp lastModifiedAt;

    @Column(name = "CREATED_BY", length = 50)
    private String createdBy;

    @Column(name = "CREATED_AT")
    private Timestamp createdAt;

    @OneToMany(mappedBy = "microciclo", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MesoMicro> mesociclos;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Microciclo)) {
            return false;
        }
        Microciclo other = (Microciclo) o;
        return getId() != null && getId().equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
