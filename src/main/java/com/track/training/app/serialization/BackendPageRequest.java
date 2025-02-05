package com.track.training.app.serialization;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.NullHandling;
import org.springframework.data.domain.Sort.Order;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

/**
 * Customizes JSON deserialization for Spring's Pageable objects that lack of a default constructor.
 */
@JsonDeserialize(using = BackendPageRequestDeserializer.class)
public class BackendPageRequest extends PageRequest {

    /** The serial version uid. */
    private static final long serialVersionUID = -2221059457237603304L;

    /**
     * Constructor.
     *
     * @param page
     *            the page number
     * @param size
     *            the page size
     */
    public BackendPageRequest(final int page, final int size) {
        super(page, size, Sort.unsorted());
    }

    /**
     * Constructor.
     *
     * @param page
     *            the page number
     * @param size
     *            the page size
     * @param direction
     *            the ordering direction
     * @param properties
     *            the properties to be ordered
     */
    public BackendPageRequest(final int page, final int size, final Direction direction, final String... properties) {
        super(page, size, Sort.by(direction, properties));
    }

    /**
     * Constructor.
     *
     * @param page
     *            the page number
     * @param size
     *            the page size
     * @param direction
     *            the ordering direction
     * @param properties
     *            the properties to be ordered
     * @param nullHandling
     *            the null handling
     */
    public BackendPageRequest(final int page, final int size, final Direction direction, final String[] properties,
            final NullHandling[] nullHandling) {
        super(page, size, Sort.by(getOrders(direction, properties, nullHandling)));
    }

    /**
     * Constructor.
     *
     * @param page
     *            the page number
     * @param size
     *            the page size
     * @param sort
     *            the sort mode
     */
    public BackendPageRequest(final int page, final int size, final Sort sort) {
        super(page, size, sort);
    }

    // -------------------------------------------------------------------------
    /**
     * Gets the orders.
     *
     * @param direction
     *            the direction
     * @param properties
     *            the properties
     * @param nullHandling
     *            the null handling
     * @return the orders
     */
    private static List<Order> getOrders(final Direction direction, final String[] properties,
            final NullHandling[] nullHandling) {
        if (properties == null || properties.length == 0) {
            throw new IllegalArgumentException("You have to provide at least one property to sort by!");
        }
        final List<Order> orders = new ArrayList<>();
        for (int i = 0; i < properties.length; i++) {
            orders.add(new Order(direction, properties[i],
                    i < nullHandling.length ? nullHandling[i] : NullHandling.NATIVE));
        }
        return orders;
    }

}
