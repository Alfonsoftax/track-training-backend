package com.src.train.track.TipoDeporte.domain;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class TipoDeporteFilter implements Serializable {

    private Long id;
    private String nombreDeporte;

}
