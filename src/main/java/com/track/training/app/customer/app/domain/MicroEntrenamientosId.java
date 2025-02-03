package com.track.training.app.customer.app.domain;

import java.io.Serializable;
import java.util.Objects;

import lombok.Getter;
import lombok.Setter;

/** */
@Getter
@Setter
public class MicroEntrenamientosId implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long microciclo;
    private Long entrenamiento;

    public MicroEntrenamientosId() {}

    public MicroEntrenamientosId(Long microciclo, Long entrenamiento) {
        this.microciclo = microciclo;
        this.entrenamiento = entrenamiento;
    }
    // Equals y HashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MicroEntrenamientosId that = (MicroEntrenamientosId) o;
        return Objects.equals(microciclo, that.microciclo) &&
               Objects.equals(entrenamiento, that.entrenamiento);
    }

    @Override
    public int hashCode() {
        return Objects.hash(microciclo, entrenamiento);
    }
}
