package com.src.train.track.Atleta.domain;

import java.io.Serializable;

import com.src.train.track.TipoDeporte.domain.TipoDeporteDto;

import lombok.Getter;
import lombok.Setter;

/** CustomerSearchCriteria. */
@Setter
@Getter
public class AtletaDto implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long id;
    private String nombre;
    private Long edad;
    private String sexo;
    private Long altura;
    private Long peso;
    private TipoDeporteDto tipoDeporte;

}
