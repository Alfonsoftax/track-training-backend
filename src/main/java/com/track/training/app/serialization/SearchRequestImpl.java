package com.track.training.app.serialization;

import java.io.Serializable;

import org.springframework.data.domain.Pageable;


public class SearchRequestImpl<T extends Serializable> implements SearchRequest<T> {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 2371176174111385284L;

    /** The filter. */
    private T filter;

    /** The page request. */
    private BackendPageRequest pageRequest;

    /*
     * (non-Javadoc)
     * @see es.logista.jalphil.service.serialization.SearchRequest#getFilter()
     */
    @Override
    public T getFilter() {
        return this.filter;
    }

    /**
     * Sets the filter.
     *
     * @param filter
     *            the new filter
     */
    public void setFilter(final T filter) {
        this.filter = filter;
    }

    /*
     * (non-Javadoc)
     * @see es.logista.jalphil.service.serialization.SearchRequest#getPageRequest()
     */
    @Override
    public Pageable getPageRequest() {
        return this.pageRequest;
    }

    /**
     * Sets the page request.
     *
     * @param pageRequest
     *            the new page request
     */
    public void setPageRequest(final BackendPageRequest pageRequest) {
        this.pageRequest = pageRequest;
    }

}
