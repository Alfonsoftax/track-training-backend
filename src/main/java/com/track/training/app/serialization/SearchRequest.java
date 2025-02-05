package com.track.training.app.serialization;

import java.io.Serializable;

import org.springframework.data.domain.Pageable;


/**
 * The Interface SearchRequest.
 *
 * @param <T>
 *            the generic type
 */
public interface SearchRequest<T extends Serializable> extends Serializable {
	
	/**
	 * Gets the page request.
	 *
	 * @return the page request
	 */
	Pageable getPageRequest();

	/**
	 * Gets the filter.
	 *
	 * @return the filter
	 */
	T getFilter();
}
