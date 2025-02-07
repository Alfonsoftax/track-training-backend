package com.src.train.track.interfaz;

import java.io.Serializable;


/**
 * Interface for services that can delete entities from the database.
 *
 * @param <T>
 *            The entity type
 * @param <I>
 *            the generic type
 */
public interface DeleteService<T, I extends Serializable> {
	
	/**
	 * Deletes an entity in the database.
	 *
	 * @param entity
	 *            The entity to delete
	 */
	void delete(T entity);

	/**
	 * Deletes an entity in the database.
	 *
	 * @param identifier
	 *            The identifier of the entity
	 */
	void delete(I identifier);
}
