package com.src.train.track.general;

import static org.apache.commons.lang3.StringUtils.defaultString;

import java.io.Serializable;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

import org.slf4j.Logger;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.MoreObjects;
import com.src.train.track.User.model.Usuario;
import com.src.train.track.general.domain.DomainEntity;
import com.src.train.track.general.domain.TransactionType;
import com.src.train.track.general.utils.Safe;

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
    private Map<Serializable, DomainEntity<?>> oldEntitiesMap; // used by DefaultService to audit changes

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
            this.userCode = user.getPassword();
            this.fullName = user.getUsername();
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

    @JsonIgnore
    @SuppressWarnings("unchecked")
    public <T extends Serializable> T setOldEntity(final Serializable oldEntity) {
        this.oldEntity = Safe.deepCopy(oldEntity);
        return (T) this.oldEntity;
    }

    @JsonIgnore
    @SuppressWarnings("unchecked")
    public <I extends Serializable, T extends DomainEntity<I>> Map<I, T> getOldEntitiesMap() {
        return (Map<I, T>) this.oldEntitiesMap;
    }

    @JsonIgnore
    @SuppressWarnings("unchecked")
    public <I extends Serializable, T extends DomainEntity<I>> void setOldEntitiesMap(final Map<I, T> oldEntitiesMap) {
        this.oldEntitiesMap = (Map<Serializable, DomainEntity<?>>) oldEntitiesMap;
    }

    @SuppressWarnings("unchecked")
    public <I extends Serializable, T extends DomainEntity<I>> Map<I, T> setOldEntities(final Iterable<T> oldEntities) {
        this.oldEntitiesMap = new HashMap<>();
        oldEntities.forEach(e -> this.oldEntitiesMap.put(e.getId(), Safe.<T> deepCopy(e)));
        return (Map<I, T>) this.oldEntitiesMap;
    }

    // -------------------------------------------------------------------------

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this).omitNullValues()//
                .add(companyCode, this.companyCode)//
                .add(processCode, this.processCode)//
                .add(currentProcessCode, this.currentProcessCode)//
                .add(parameter, this.parameter)//
                .toString();
    }

}
