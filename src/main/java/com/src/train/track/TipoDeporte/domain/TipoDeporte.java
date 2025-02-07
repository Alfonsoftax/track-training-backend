package com.src.train.track.TipoDeporte.domain;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.src.train.track.general.domain.DomainEntity;

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
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

/** */

@Entity
@Table(name = "TIPO_DEPORTE")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@FieldNameConstants
@ToString
public class TipoDeporte implements DomainEntity<Long> {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TIPO_DEPORTE_ID")
    @SequenceGenerator(name = "SEQ_TIPO_DEPORTE_ID", sequenceName = "SEQ_TIPO_DEPORTE_ID", allocationSize = 1)
    @Column(name = "ID_DEPORTE", nullable = false)    
	private Long id;

    @Column(name = "NOMBRE_DEPORTE", nullable = false, length = 100)
    private String nombreDeporte;

    @Column(name = "DESCRIPCION", length = 255)
    private String descripcion;

    @Column(name = "FECHA_CREACION", nullable = false, columnDefinition = "DATE DEFAULT SYSDATE")
    @Temporal(TemporalType.DATE)
    private Date fechaCreacion;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TipoDeporte)) {
            return false;
        }
        TipoDeporte other = (TipoDeporte) o;
        return getId() != null && getId().equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
