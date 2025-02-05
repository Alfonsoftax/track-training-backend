package com.track.training.app.customer.core.inbound.dtos;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class AtletaFilter implements Serializable {

    private Long id;
    private String nombre;
    private Long edad;
    private String sexo;
    private Long altura;
    private Double peso;

}
