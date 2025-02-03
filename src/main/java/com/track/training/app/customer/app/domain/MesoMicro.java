package com.track.training.app.customer.app.domain;

import java.io.Serializable;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/** */

@Entity
@Table(name = "MESO_MICROS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@EntityListeners(AuditingEntityListener.class)
@IdClass(MesoMicroId.class)
@Getter
@Setter
public class MesoMicro implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @ManyToOne
    @JoinColumn(name = "ID_MICROCICLO", nullable = false)
    private Microciclo microciclo;

    @Id
    @ManyToOne
    @JoinColumn(name = "ID_MESOCICLO", nullable = false)
    private Mesociclo mesociclo;

    @Column(name = "ORDEN_MICRO", nullable = false)
    private Long ordenMicro;

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
