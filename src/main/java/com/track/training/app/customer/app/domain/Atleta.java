package com.track.training.app.customer.app.domain;

import java.sql.Timestamp;
import java.util.List;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.track.training.app.customer.core.domain.Customer;
import com.track.training.app.interfaces.DomainEntity;

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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldNameConstants;

/** */

@Entity
@Table(name = "ATLETA")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
@FieldNameConstants
@ToString
public class Atleta implements DomainEntity<Long> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_atleta_id")
    @SequenceGenerator(name = "seq_atleta_id", sequenceName = "seq_atleta_id", allocationSize = 1)
    @Column(name = "ID", nullable = false)
    private Long id;

    @Column(name = "NOMBRE", nullable = false, length = 50)
    private String nombre;

    @Column(name = "EDAD", nullable = false)
    private Long edad;

    @Column(name = "SEXO", nullable = false, length = 1)
    private String sexo;

    @Column(name = "ALTURA", nullable = false)
    private Long altura;

    @Column(name = "PESO")
    private Long peso;

    @Column(name = "LAST_MODIFIED_BY", length = 50)
    private String lastModifiedBy;

    @Column(name = "LAST_MODIFIED_AT")
    private Timestamp lastModifiedAt;

    @Column(name = "CREATED_BY", length = 50)
    private String createdBy;

    @Column(name = "CREATED_AT")
    private Timestamp createdAt;
    
    @OneToMany(mappedBy = "atleta", fetch = FetchType.LAZY)
    private List<AtletaMesociclo> mesociclos;
    
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Customer)) {
            return false;
        }
        Atleta other = (Atleta) o;
        return getId() != null && getId().equals(other.getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
