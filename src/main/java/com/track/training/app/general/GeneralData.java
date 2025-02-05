package com.track.training.app.general;

import static org.apache.commons.lang3.StringUtils.defaultString;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.Objects;

import org.slf4j.Logger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.track.training.app.customer.core.domain.Usuario;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import lombok.experimental.FieldNameConstants;

@Getter
@Setter
@FieldNameConstants
@EqualsAndHashCode(callSuper = false, onlyExplicitlyIncluded = true)
public class GeneralData implements Serializable {

    private static final long serialVersionUID = 2891722849421513821L;

    @EqualsAndHashCode.Include
    private String companyCode;
    @EqualsAndHashCode.Include
    private TransactionType transactionType;

    @EqualsAndHashCode.Include
    private String processCode;
    private String currentProcessCode;

    private String subprocessCode;

    private boolean isPermissionChecked;
    private boolean isBatch;
    private boolean isScreen;

    private String parameter;

    @Setter(AccessLevel.NONE)
    private Usuario user;
    private Long userId;
    @EqualsAndHashCode.Include
    private String userCode;
    private String fullName;

    private String fileName;
    private String xsdNamespace;
    @Accessors(fluent = true)
    private boolean outputTemp;
    private transient Path outputPath;

    private transient Logger logger;

    private String originException;
    private String messagePrefix;

    @Accessors(fluent = true)
    private boolean cascadeOperations = true;

    @Accessors(fluent = true)
    private boolean throwExceptions = true;

    @Accessors(fluent = true)
    private boolean validateEntity = true;

    @Accessors(fluent = true)
    private boolean validateParameters = false;

    private Integer batchSize;

    // -------------------------------------------------------------------------

    @Accessors(fluent = true)
    private Serializable oldEntity; // used by DefaultService to audit changes

    @Accessors(fluent = true)
    private boolean collectAudits; // collect audits

    @Accessors(fluent = true)
    private boolean skipSaveAudits; // mark to avoid saving audits in internal services (flag resets when checked)

    // -------------------------------------------------------------------------

    public GeneralData() {
        this.isPermissionChecked = false;
        this.isScreen = true;
        this.isBatch = false;
    }

    public GeneralData(final GeneralData generalData, final String processCode) {
        this.companyCode = generalData.companyCode;
        this.transactionType = generalData.transactionType;
        this.processCode = processCode;
        this.subprocessCode = generalData.subprocessCode;
        this.currentProcessCode = generalData.getProcessCode();
        this.isPermissionChecked = false;
        this.isBatch = generalData.isBatch;
        this.isScreen = generalData.isScreen;
        this.parameter = generalData.parameter;
        this.user = generalData.user;
        this.userId = generalData.userId;
        this.userCode = generalData.userCode;
        this.fullName = generalData.fullName;

        this.fileName = generalData.fileName;
    }

    public GeneralData(final GeneralData generalData) {
        this(generalData, generalData.getProcessCode());
    }

    // -------------------------------------------------------------------------

    public GeneralData parameter(final String parameter) {
        this.parameter = parameter;
        return this;
    }

    public void setUser(final Usuario user) {
        this.user = user;
        if (user != null) {
            this.userId = user.getId();
            this.userCode = user.getUsername();
            this.fullName = user.getPassword();
        }
    }

    public String getUsername() {
        return this.userCode;
    }

    public void setTransactionType(final TransactionType transactionType) {
        if (this.transactionType != transactionType) {
            this.isPermissionChecked = false;
        }
        this.transactionType = transactionType;
    }

    public void setProcessCode(final String processCode) {
        if (!Objects.equals(this.processCode, processCode)) {
            this.isPermissionChecked = false;
        }
        this.processCode = processCode;
    }

    public void setProcessCodeSilently(final String processCode) {
        this.processCode = processCode;
    }

    public String getCurrentProcessCode() {
        return defaultString(this.currentProcessCode, this.processCode);
    }

    public boolean isCheckPermissionNeeded() {
        return !this.isPermissionChecked && this.transactionType != null;
    }

    // -------------------------------------------------------------------------

    @JsonIgnore
    @SuppressWarnings("unchecked")
    public <T extends Serializable> T getOldEntity() {
        return (T) this.oldEntity;
    }


}
