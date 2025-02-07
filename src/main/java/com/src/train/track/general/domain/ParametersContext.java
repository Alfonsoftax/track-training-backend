package com.src.train.track.general.domain;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.NavigableMap;
import java.util.Queue;


import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Parameters Context
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParametersContext implements Serializable {

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = -4505158028118062265L;

    @NoArgsConstructor(access = AccessLevel.PRIVATE)
    public static final class Parameters implements Serializable {

        /** The Constant serialVersionUID. */
        private static final long serialVersionUID = -7344457003996572268L;

        private String userCode;
        private boolean input;
        private boolean output;
        private String inputDirectory;
        private String outputDirectory;
        private Integer numConcurrency;
        private String codeProcess;
        private String company;
        private String companyCode;
        private String daysAgo;
        private String daysAhead;
        private Long numTransmision;
        private boolean error = false;
        private transient Queue<LoaderFile> loaderFilesQueue;
        private transient NavigableMap<String, Queue<LoaderFile>> loaderFilesMap;
        private String fileName;
        private Integer numProcessed;
        private List<String> messages = new LinkedList<>();
        private List<String> successes = new LinkedList<>();
        private List<String> errors = new LinkedList<>();
        private List<String> rejections = new LinkedList<>();
        private List<String> systemErrors = new LinkedList<>();
        private boolean emptyTemplates = false;
    }

    private static final ThreadLocal<Parameters> PARAMETERS = ThreadLocal.withInitial(Parameters::new);

    // -------------------------------------------------------------------------

    public static Parameters get() {
        return PARAMETERS.get();
    }

    public static void set(final Parameters parameters) {
        PARAMETERS.set(parameters);
    }

    // -------------------------------------------------------------------------

    public static String getUserCode() {
        return PARAMETERS.get().userCode;
    }

    public static void setUserCode(final String userCode) {
        PARAMETERS.get().userCode = userCode;
    }

    public static boolean isInput() {
        return PARAMETERS.get().input;
    }

    public static void setInput(final boolean input) {
        PARAMETERS.get().input = input;
    }

    public static boolean isOutput() {
        return PARAMETERS.get().output;
    }

    public static void setOutput(final boolean output) {
        PARAMETERS.get().output = output;
    }

    public static String getInputDirectory() {
        return PARAMETERS.get().inputDirectory;
    }

    public static void setInputDirectory(final String inputDirectory) {
        PARAMETERS.get().inputDirectory = inputDirectory;
    }

    public static String getOutputDirectory() {
        return PARAMETERS.get().outputDirectory;
    }

    public static void setOutputDirectory(final String outputDirectory) {
        PARAMETERS.get().outputDirectory = outputDirectory;
    }

    public static Integer getNumConcurrency() {
        return PARAMETERS.get().numConcurrency;
    }

    public static void setNumConcurrency(final Integer numConcurrency) {
        PARAMETERS.get().numConcurrency = numConcurrency;
    }

    public static String getCodeProcess() {
        return PARAMETERS.get().codeProcess;
    }

    public static void setCodeProcess(final String codeProcess) {
        PARAMETERS.get().codeProcess = codeProcess;
    }

    public static String getDaysAgo() {
        return PARAMETERS.get().daysAgo;
    }

    public static void setDaysAgo(final String daysAgo) {
        PARAMETERS.get().daysAgo = daysAgo;
    }

    public static String getDaysAhead() {
        return PARAMETERS.get().daysAhead;
    }

    public static void setDaysAhead(final String daysAhead) {
        PARAMETERS.get().daysAhead = daysAhead;
    }

    public static Long getNumTransmision() {
        return PARAMETERS.get().numTransmision;
    }

    public static void setNumTransmision(final Long numTransmision) {
        PARAMETERS.get().numTransmision = numTransmision;
    }

    public static boolean isError() {
        return PARAMETERS.get().error;
    }

    public static void setError(final boolean error) {
        PARAMETERS.get().error = error;
    }

    public static Queue<LoaderFile> getLoaderFilesQueue() {
        return PARAMETERS.get().loaderFilesQueue;
    }

    public static void setLoaderFilesQueue(final Queue<LoaderFile> loaderFilesQueue) {
        PARAMETERS.get().loaderFilesQueue = loaderFilesQueue;
    }

    public static NavigableMap<String, Queue<LoaderFile>> getLoaderFilesMap() {
        return PARAMETERS.get().loaderFilesMap;
    }

    public static void setLoaderFilesMap(final NavigableMap<String, Queue<LoaderFile>> loaderFilesMap) {
        PARAMETERS.get().loaderFilesMap = loaderFilesMap;
    }

    public static String getFileName() {
        return PARAMETERS.get().fileName;
    }

    public static void setFileName(final String fileName) {
        PARAMETERS.get().fileName = fileName;
    }

    public static Integer getNumProcessed() {
        return PARAMETERS.get().numProcessed;
    }

    public static void setNumProcessed(final Integer numProcessed) {
        PARAMETERS.get().numProcessed = numProcessed;
    }

    public static List<String> getMessages() {
        return PARAMETERS.get().messages;
    }

    public static void setMessages(final List<String> messages) {
        PARAMETERS.get().messages = messages;
    }

    public static List<String> getSuccesses() {
        return PARAMETERS.get().successes;
    }

    public static void setSuccesses(final List<String> successes) {
        PARAMETERS.get().successes = successes;
    }

    public static List<String> getErrors() {
        return PARAMETERS.get().errors;
    }

    public static void setErrors(final List<String> errors) {
        PARAMETERS.get().errors = errors;
    }

    public static List<String> getRejections() {
        return PARAMETERS.get().rejections;
    }

    public static void setRejections(final List<String> rejections) {
        PARAMETERS.get().rejections = rejections;
    }

    public static List<String> getSystemErrors() {
        return PARAMETERS.get().systemErrors;
    }

    public static void setSystemErrors(final List<String> systemErrors) {
        PARAMETERS.get().systemErrors = systemErrors;
    }


    public static boolean isEmptyTemplates() {
        return PARAMETERS.get().emptyTemplates;
    }

    public static void setEmptyTemplates(final boolean emptyTemplates) {
        PARAMETERS.get().emptyTemplates = emptyTemplates;
    }

    // -------------------------------------------------------------------------

    public static String getCompany() {
        return PARAMETERS.get().company;
    }

    public static String getCompanyCode() {
        return PARAMETERS.get().companyCode;
    }

    // -------------------------------------------------------------------------

    public static void setCompany(final String company) {
        PARAMETERS.get().company = company;
        PARAMETERS.get().companyCode = TenantContext.getCompanyCode(company);
    }

    public static void addMessages(final String message) {
        getMessages().add(message);
    }

    public static void addSuccesess(final String success) {
        getSuccesses().add(success);
    }

    public static void addError(final String error) {
        getErrors().add(error);
    }

    public static void addRejection(final String rejection) {
        getRejections().add(rejection);
    }

    public static void addSystemError(final String error) {
        getSystemErrors().add(error);
    }

    // -------------------------------------------------------------------------

    public static void clear() {
        PARAMETERS.remove();
    }

}
