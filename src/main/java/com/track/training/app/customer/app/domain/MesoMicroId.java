package com.track.training.app.customer.app.domain;

import java.io.Serializable;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

/** */
@Getter
@Setter
public class MesoMicroId implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long microciclo;
    private Long mesociclo;

    public MesoMicroId() {}

    public MesoMicroId(Long microciclo, Long mesociclo) {
        this.microciclo = microciclo;
        this.mesociclo = mesociclo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MesoMicroId that = (MesoMicroId) o;
        return Objects.equals(microciclo, that.microciclo) && 
               Objects.equals(mesociclo, that.mesociclo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(microciclo, mesociclo);
    }
}
