package com.src.train.track.Atleta.predicate;

import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.src.train.track.Atleta.domain.AtletaFilter;
import com.src.train.track.Atleta.domain.QAtleta;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AtletaPredicate {

	public static Predicate searchPredicate(final AtletaFilter filter) {
	    QAtleta atleta = QAtleta.atleta;
	    
	    BooleanExpression predicate = null;

	    if (filter.getId() != null) {
	        predicate = atleta.id.eq(filter.getId());
	    }
	    if (filter.getNombre() != null && !filter.getNombre().isEmpty()) {
	        predicate = (predicate == null) ? atleta.nombre.like(filter.getNombre()) 
	                                        : predicate.and(atleta.nombre.like(filter.getNombre()));
	    }
	    if (filter.getEdad() != null) {
	        predicate = (predicate == null) ? atleta.edad.eq(filter.getEdad()) 
	                                        : predicate.and(atleta.edad.eq(filter.getEdad()));
	    }
	    if (filter.getSexo() != null && !filter.getSexo().isEmpty()) {
	        predicate = (predicate == null) ? atleta.sexo.eq(filter.getSexo()) 
	                                        : predicate.and(atleta.sexo.eq(filter.getSexo()));
	    }
	    if (filter.getAltura() != null) {
	        predicate = (predicate == null) ? atleta.altura.eq(filter.getAltura()) 
	                                        : predicate.and(atleta.altura.eq(filter.getAltura()));
	    }
	    if (filter.getPeso() != null) {
	        predicate = (predicate == null) ? atleta.peso.eq(filter.getPeso()) 
	                                        : predicate.and(atleta.peso.eq(filter.getPeso()));
	    }
	    if (filter.getTipoDeporte() != null && !filter.getTipoDeporte().isEmpty()) {
	        predicate = (predicate == null) ? atleta.tipoDeporte.nombreDeporte.eq(filter.getTipoDeporte()) 
	                                        : predicate.and(atleta.tipoDeporte.nombreDeporte.eq(filter.getTipoDeporte()));
	    }
	    return predicate;
	}
}

