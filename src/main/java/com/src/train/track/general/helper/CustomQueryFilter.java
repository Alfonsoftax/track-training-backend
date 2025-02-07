package com.src.train.track.general.helper;

import java.io.Serializable;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Class CustomQueryFilter.
 */
@Getter
@Setter
@NoArgsConstructor
public class CustomQueryFilter implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -2662647302270591884L;

    /** The custom query. */
    private Integer customQuery;

    /** The custom fetch. */
    private String[] customFetch;

    // -------------------------------------------------------------------------
    /**
     * Instantiates a new custom query filter.
     *
     * @param filter
     *            the filter
     */
    public CustomQueryFilter(final CustomQueryFilter filter) {
        this.customQuery = filter.customQuery;
        this.customFetch = filter.customFetch;
    }

    /**
     * Sets the custom fetch.
     *
     * @param customFetch
     *            the new custom fetch
     */
    public void setCustomFetch(final String... customFetch) {
        this.customFetch = customFetch;
    }

}
