package com.track.training.app.customer.core.inbound.dtos;

import java.io.Serializable;

/** CustomerSearchCriteria. */
@lombok.Getter
@lombok.Setter
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

}
