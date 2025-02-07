package com.src.train.track.general.domain;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * The Class AuditableEntity.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@ToString
public abstract class AuditableEntity {

    /** The created by. */
    @CreatedBy
    @Column(name = AuditableColumns.CREATED_BY, updatable = false)
    @JsonIgnore
    private String createdBy;

    /** The created date. */
    @CreatedDate
    @Column(name = AuditableColumns.CREATED_AT, updatable = false)
    @JsonIgnore
    private Date createdDate;

    /** The last modified by. */
    @LastModifiedBy
    @Column(name = AuditableColumns.LAST_MODIFIED_BY)
    @JsonIgnore
    private String lastModifiedBy;

    /** The last modified date. */
    @LastModifiedDate
    @Column(name = AuditableColumns.LAST_MODIFIED_AT)
    @JsonIgnore
    private Date lastModifiedDate;

    /** The create process code. */
    @Column(name = AuditableColumns.CREATED_PROCESS_CODE, updatable = false)
    @JsonIgnore
    private String createProcessCode;

    /** The modified process code. */
    @Column(name = AuditableColumns.MODIFIED_PROCESS_CODE)
    @JsonIgnore
    private String modifiedProcessCode;

    // -------------------------------------------------------------------------
    /**
     * Gets the created date.
     *
     * @return the created date
     */
    public Date getCreatedDate() {
        return this.createdDate == null ? null : new Date(this.createdDate.getTime());
    }

    /**
     * Sets the created date.
     *
     * @param createdDate
     *            the new created date
     */
    public void setCreatedDate(final Date createdDate) {
        this.createdDate = createdDate == null ? null : new Date(createdDate.getTime());
    }

    /**
     * Gets the last modified date.
     *
     * @return the last modified date
     */
    public Date getLastModifiedDate() {
        return this.lastModifiedDate == null ? null : new Date(this.lastModifiedDate.getTime());
    }

    /**
     * Sets the last modified date.
     *
     * @param lastModifiedDate
     *            the new last modified date
     */
    public void setLastModifiedDate(final Date lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate == null ? null : new Date(lastModifiedDate.getTime());
    }

    // -------------------------------------------------------------------------
    /**
     * Column name constants.
     */
    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class AuditableColumns {
        public static final String CREATED_BY = "CREATED_BY";
        public static final String CREATED_AT = "CREATED_AT";
        public static final String LAST_MODIFIED_BY = "LAST_MODIFIED_BY";
        public static final String LAST_MODIFIED_AT = "LAST_MODIFIED_AT";
        public static final String CREATED_PROCESS_CODE = "CODIGO_PROCESO_CREATED";
        public static final String MODIFIED_PROCESS_CODE = "CODIGO_PROCESO_MODIFIED";
    }

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class AuditableProperties {
        public static final String CREATED_BY = "createdBy";
        public static final String CREATED_DATE = "createdDate";
        public static final String LAST_MODIFIED_BY = "lastModifiedBy";
        public static final String LAST_MODIFIED_DATE = "lastModifiedDate";
        public static final String CREATE_PROCESS_CODE = "createProcessCode";
        public static final String MODIFIED_PROCESS_CODE = "modifiedProcessCode";
    }

}
