package com.track.training.app.serialization;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import com.track.training.app.utils.Safe;


/**
 * The Class PageImplHelper.
 *
 * @param <T>
 *            the generic type
 */
public class PageImplHelper<T> extends PageImpl<T> {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1067168747726637249L;

    /** The host name. */
    private String hostName;

    /**
     * Instantiates a new page impl helper.
     */
    public PageImplHelper() {
        this(new ArrayList<>());
    }

    /**
     * Instantiates a new page impl helper.
     *
     * @param content
     *            the content
     * @param pageable
     *            the pageable
     * @param total
     *            the total
     */
    public PageImplHelper(final List<T> content, final Pageable pageable, final long total) {
        super(content, pageable, total);
        this.hostName = Safe.getHostName();
    }

    /**
     * Instantiates a new page impl helper.
     *
     * @param content
     *            the content
     */
    public PageImplHelper(final List<T> content) {
        super(content);
        this.hostName = Safe.getHostName();
    }

    /**
     * Adds the.
     *
     * @param element
     *            the element
     */
    public void add(final T element) {
        this.getContent().add(element);
    }

    /**
     * Sets the size.
     *
     * @param size
     *            the new size
     */
    public void setSize(final int size) {
        // empty
    }

    /**
     * Gets the host name.
     *
     * @return the host name
     */
    public String getHostName() {
        return this.hostName;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        return super.equals(obj);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
