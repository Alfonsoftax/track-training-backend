package com.track.training.app.interfaces;

import java.util.List;


/**
 * Interface for services that save entities to the database.
 *
 * @param <T>
 *            The entity type
 */
public interface SaveService<T> {

	/**
	 * Save an entity to the database.
	 *
	 * @param entity
	 *            The entity to be saved
	 * @return The saved entity
	 */
	T save(T entity);

	/**
	 * Save a list of entities to the database.
	 *
	 * @param entity
	 *            The entities to save
	 * @return The updated entities
	 */
	List<T> save(Iterable<T> entity);

	/**
	 * Updates an entity in the database.
	 *
	 * @param entity
	 *            The entity to update
	 * @return The updated entity
	 */
	T update(T entity);

}
