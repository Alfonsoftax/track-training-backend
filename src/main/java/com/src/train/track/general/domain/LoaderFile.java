package com.src.train.track.general.domain;

import java.io.File;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoaderFile implements Serializable {

    public static final String BATCH = "Batch - {} - {}";
    public static final String LOADER = "Loader - {} - {}";

    private static final long serialVersionUID = 4237642003839471009L;

    private final String version;
    private final String identificator;
    private final File file;

    private String group;
    private boolean simpleLoad;
    private boolean simpleRegs;
    private long numTransmision;

    private boolean success;
    private boolean error;
    private boolean rejection;
    private boolean systemError;
    private boolean clearSuccessInError;

    @ToString.Exclude
    private List<String> successes;
    @ToString.Exclude
    private List<String> errors;
    @ToString.Exclude
    private List<String> rejections;
    @ToString.Exclude
    private List<String> systemErrors;

    public LoaderFile(final String version, final String identificator, final File file) { // used in batchs
        this.version = version;
        this.identificator = identificator;
        this.file = file;
        this.numTransmision = 0;
        this.success = true;
        this.error = false;
        this.rejection = false;
        this.systemError = false;
        this.clearSuccessInError = false;
        this.successes = new LinkedList<>();
        this.errors = new LinkedList<>();
        this.rejections = new LinkedList<>();
        this.systemErrors = new LinkedList<>();
    }

    public LoaderFile(final File file) { // not used in batchs
        this(null, null, file);
    }

    public boolean isBatch() {
        return this.identificator != null;
    }

    public void addSuccess(final String success) {
        this.success = true;
        this.error = false;
        this.rejection = false;
        this.systemError = false;
        this.successes.add(success);
        if (this.isBatch()) {
            ParametersContext.addSuccesess(success);
        }
    }

    public void addError(final String error) {
        this.success = false;
        this.error = true;
        this.rejection = false;
        this.systemError = false;
        this.errors.add(error);
        if (this.isBatch()) {
            ParametersContext.addError(error);
        }
        if (this.clearSuccessInError && CollectionUtils.isNotEmpty(this.successes)) {
            if (this.isBatch()) {
                for (final String string : this.successes) {
                    ParametersContext.getSuccesses().remove(string);
                }
            }
            this.successes.clear();
        }
    }

    public void addRejection(final String rejection) {
        this.success = false;
        this.error = false;
        this.rejection = true;
        this.systemError = false;
        this.rejections.add(rejection);
        if (this.isBatch()) {
            ParametersContext.addRejection(rejection);
        }
        if (this.clearSuccessInError && CollectionUtils.isNotEmpty(this.successes)) {
            if (this.isBatch()) {
                for (final String string : this.successes) {
                    ParametersContext.getSuccesses().remove(string);
                }
            }
            this.successes.clear();
        }
    }

    public void addSystemError(final String error) {
        this.success = false;
        this.error = false;
        this.rejection = false;
        this.systemError = true;
        this.systemErrors.add(error);
        if (this.isBatch()) {
            ParametersContext.addSystemError(error);
        }
    }

    // -------------------------------------------------------------------------

    public String addMessage(final Logger logger, final String message) {
        logger.info(BATCH, ParametersContext.getCodeProcess(), message);
        return message;
    }

    public String addSuccess(final Logger logger, final String success) {
        logger.info(BATCH, ParametersContext.getCodeProcess(), success);
        this.addSuccess(success);
        return success;
    }

    public String addRejection(final Logger logger, final String rejection) {
        logger.warn(BATCH, ParametersContext.getCodeProcess(), rejection);
        this.addRejection(rejection);
        return rejection;
    }

    public String addError(final Logger logger, final String error) {
        logger.error(BATCH, ParametersContext.getCodeProcess(), error);
        this.addError(error);
        return error;
    }

    public String addRejectionError(final Logger logger, final String rejectionError) {
        logger.error(BATCH, ParametersContext.getCodeProcess(), rejectionError);
        this.addRejection(rejectionError);
        return rejectionError;
    }

    public String addSystemError(final Logger logger, final String error, final Exception e) {
        logger.error(BATCH, ParametersContext.getCodeProcess(), error, e);
        this.addSystemError(error);
        return error;
    }

}
