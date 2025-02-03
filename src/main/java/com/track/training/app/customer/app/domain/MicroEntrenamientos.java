package com.track.training.app.customer.app.domain;

import java.io.Serializable;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
@Table(name = "MICRO_ENTRENAMIENTOS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@EntityListeners(AuditingEntityListener.class)
@IdClass(MicroEntrenamientosId.class)
@Getter
@Setter
public class MicroEntrenamientos implements Serializable {

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
	    @JoinColumn(name = "ID_ENTRENAMIENTOS", nullable = false)
	    private Entrenamientos entrenamiento;

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
