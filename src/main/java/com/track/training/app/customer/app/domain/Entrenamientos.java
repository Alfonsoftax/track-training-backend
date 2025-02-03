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
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/** */

@Entity
@Table(name = "ENTRENAMIENTOS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Entrenamientos implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_entrenamientos_id")
    @SequenceGenerator(name = "seq_entrenamientos_id", sequenceName = "seq_entrenamientos_id", allocationSize = 1)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "ORDEN_SEMANA")
    private Integer ordenSemana;

    @Column(name = "LAST_MODIFIED_BY", length = 50)
    private String lastModifiedBy;

    @Column(name = "LAST_MODIFIED_AT")
    private Timestamp lastModifiedAt;

    @Column(name = "CREATED_BY", length = 50)
    private String createdBy;

    @Column(name = "CREATED_AT")
    private Timestamp createdAt;

    @Column(name = "MOMENTO_DIA", nullable = false, length = 1)
    private String momentoDia;

    @Column(name = "DIA_SEMANA", length = 1)
    private String diaSemana;

    @Column(name = "TIEMPO_TOTAL_MINUTOS")
    private Integer tiempoTotalMinutos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_TIPO_ENTRENAMIENTO", nullable = false)
    private TipoEntrenamiento tipoEntrenamiento;

    @Column(name = "FECHA_ENTRENAMIENTO")
    private Timestamp fechaEntrenamiento;

    @OneToMany(mappedBy = "entrenamientos", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EntrenamientoEjercicios> entrenamientoEjercicios;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        Entrenamientos other = (Entrenamientos) o;
        return getId() != null && getId().equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
