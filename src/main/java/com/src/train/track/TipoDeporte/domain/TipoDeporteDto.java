package com.src.train.track.TipoDeporte.domain;

import java.io.Serializable;

/** CustomerSearchCriteria. */
@lombok.Getter
@lombok.Setter
public class TipoDeporteDto implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
    private String nombreDeporte;
    private String descripcion;


}
