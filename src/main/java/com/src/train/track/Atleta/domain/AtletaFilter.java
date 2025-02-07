package com.src.train.track.Atleta.domain;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AtletaFilter implements Serializable {

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
    private String tipoDeporte;
}
