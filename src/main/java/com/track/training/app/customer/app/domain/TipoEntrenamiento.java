package com.track.training.app.customer.app.domain;

import java.io.Serializable;
import java.sql.Timestamp;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/** */

@Entity
@Table(name = "TIPO_ENTRENAMIENTO")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class TipoEntrenamiento implements Serializable {

	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TIPO_ENTRENAMIENTOS_ID")
	    @SequenceGenerator(name = "SEQ_TIPO_ENTRENAMIENTOS_ID", sequenceName = "SEQ_TIPO_ENTRENAMIENTOS_ID", allocationSize = 1)
	    @Column(name = "ID", nullable = false)
	    private Long id;

	    @Column(name = "DESCRIPCION", length = 50)
	    private String descripcion;

	    @Column(name = "LAST_MODIFIED_BY", length = 50)
	    private String lastModifiedBy;

	    @Column(name = "LAST_MODIFIED_AT")
	    private Timestamp lastModifiedAt;

	    @Column(name = "CREATED_BY", length = 50)
	    private String createdBy;

	    @Column(name = "CREATED_AT")
	    private Timestamp createdAt;

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
