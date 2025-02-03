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
@Table(name = "EJERCICIOS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class Ejercicios implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_ejercicios_id")
    @SequenceGenerator(name = "seq_ejercicios_id", sequenceName = "seq_ejercicios_id", allocationSize = 1)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "DESCRIPCION", nullable = false, length = 70)
    private String descripcion;

    @Column(name = "LAST_MODIFIED_BY", length = 50)
    private String lastModifiedBy;

    @Column(name = "LAST_MODIFIED_AT")
    private Timestamp lastModifiedAt;

    @Column(name = "CREATED_BY", length = 50)
    private String createdBy;

    @Column(name = "CREATED_AT")
    private Timestamp createdAt;

    @Column(name = "BASICO", nullable = false, length = 1)
    private String basico;

    @OneToMany(mappedBy = "ejercicios", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<EntrenamientoEjercicios> entrenamientoEjercicios;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        Ejercicios other = (Ejercicios) o;
        return getId() != null && getId().equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
