package com.src.train.track.TipoDeporte.predicate;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.src.train.track.TipoDeporte.domain.QTipoDeporte;
import com.src.train.track.TipoDeporte.domain.TipoDeporteFilter;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TipoDeportePredicate {

	public static Predicate searchPredicate(final TipoDeporteFilter filter) {
		QTipoDeporte tipoDeporte = QTipoDeporte.tipoDeporte;

	    BooleanExpression predicate = null;

	    if (filter.getId() != null) {
	        predicate = tipoDeporte.id.eq(filter.getId());
	    }

	    if (filter.getNombreDeporte() != null && !filter.getNombreDeporte().isEmpty()) {
	        if (predicate == null) {
	            predicate = tipoDeporte.nombreDeporte.containsIgnoreCase(filter.getNombreDeporte());
	        } else {
	            predicate = predicate.and(tipoDeporte.nombreDeporte.containsIgnoreCase(filter.getNombreDeporte()));
	        }
	    }

	    return predicate;
	}
}
