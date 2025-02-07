package com.src.train.track.general.utils;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import com.src.train.track.general.serialization.PageImplHelper;


/**
 * MapStruct base mapper.
 *
 * @param <E>
 *            the entity type.
 * @param <D>
 *            the DTO type.
 */
public interface BaseMapper<E, D> {

    /**
     * Convert entity to DTO.
     *
     * @param entity
     *            the entity
     * @return the DTO
     */
    D convertToDto(E entity);

    /**
     * Convert DTO to entity.
     *
     * @param dto
     *            the DTO
     * @return the entity.
     */
    E convertFromDto(D dto);

    /**
     * Convert entity list to DTO list.
     *
     * @param list
     *            the entity list
     * @return the DTO list
     */
    List<D> convertListToDto(List<E> list);

    /**
     * Convert entity DTO list to entity list.
     *
     * @param listDto
     *            the DTO list
     * @return the entity list
     */
    List<E> convertListFromDto(Iterable<D> listDto);

    /**
     * Convert page to dto.
     *
     * @param page
     *            the page
     * @return the page impl helper
     */
    default PageImplHelper<D> convertPageToDto(final Page<E> page) {
        if (page == null) {
            return null;
        }
        final List<D> list = this.convertListToDto(page.getContent());

        return new PageImplHelper<>(list, PageRequest.of(page.getNumber(), page.getSize(), page.getSort()),
                page.getTotalElements());
    }

    // -------------------------------------------------------------------------
    /**
     * Determine the load state of a given persistent attribute.
     *
     * @param entity
     *            entity containing the attribute
     * @param attributeName
     *            name of attribute whose load state is
     *            to be determined
     *
     * @return false if entity's state has not been loaded or
     *         if the attribute state has not been loaded, else true
     */
    static boolean isLoaded(final Object entity, final String attributeName) {
        return Safe.isLoaded(entity, attributeName);
    }

    /**
     * Return the bean instance that uniquely matches the given object type, if any.
     *
     * @param <T>
     *            the generic type
     * @param requiredType
     *            the required type
     * @return the bean
     */
    static <T> T getBean(final Class<T> requiredType) {
        return SpringUtil.getContext().getBean(requiredType);
    }

}
