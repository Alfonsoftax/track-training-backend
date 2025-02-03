package com.track.training.app.customer.app.domain;

import java.io.Serializable;

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
@Table(name = "ENTRENAMIENTO_EJERCICIOS")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
public class EntrenamientoEjercicios implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_EJERCICIOS", nullable = false)
    private Ejercicios ejercicios;
	
	@Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_ENTRENAMIENTOS", nullable = false)
    private Entrenamientos entrenamientos;

    @Column(name = "RPE_OBJETIVO", nullable = false)
    private Long rpeObjetivo;

    @Column(name = "RIR_OBJETIVO")
    private Long rirObjetivo;

    @Column(name = "PORCENTAJE_CARGA_OBJETIVO")
    private Long porcentajeCargaObjetivo;

    @Column(name = "CARGA_OBJETIVO")
    private Long cargaObjetivo;

    @Column(name = "NUMERO_SET", nullable = false)
    private Integer numeroSet;

    @Column(name = "NUMERO_REP", nullable = false)
    private Integer numeroRep;

    @Column(name = "REST")
    private Long rest;

    @Column(name = "CARGA_REAL")
    private Long cargaReal;

    @Column(name = "RPE_REAL")
    private Long rpeReal;

    @Column(name = "RIR_REAL")
    private Long rirReal;

    @Column(name = "CARGA_SEMANA_PASADA")
    private Long cargaSemanaPasada;

    @Column(name = "OBSERVACIONES", length = 70)
    private String observaciones;

    @Column(name = "GRABAR", nullable = false, length = 1)
    private String grabar;

    @Column(name = "EXPLICACION_CARGA_OBJETIVO", length = 50)
    private String explicacionCargaObjetivo;

    @Column(name = "NOTAS", length = 150)
    private String notas;

    @Column(name = "ORDEN")
    private Integer orden;

    @Column(name = "SERIE_COMPLETA", length = 50)
    private String serieCompleta;

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}
