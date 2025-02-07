package com.src.train.track.general.domain;

import static org.apache.commons.lang3.StringUtils.defaultIfBlank;

import com.src.train.track.general.helper.SessionUtils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ProcessContext {

    private static final String PROCESS = "PROCESS";

    private static ThreadLocal<String> currentProcess = new ThreadLocal<>();
    private static ThreadLocal<String> currentMethod = new ThreadLocal<>();
    private static ThreadLocal<Integer> currentAudSid = new ThreadLocal<>();

    // -------------------------------------------------------------------------

    public static void setCurrentProcess(final String process) {
        setCurrentProcess(process, null);
    }

    public static void setCurrentProcess(final String process, final String method) {
        currentProcess.set(process);
        currentMethod.set(method);
        currentAudSid.remove();
        SessionUtils.getUserDetails().put(PROCESS, process);
    }

    public static String getCurrentProcess() {
        final String processCode = currentProcess.get();
        final String sessionProcessCode = (String) SessionUtils.getUserDetails().computeIfAbsent(PROCESS,
                k -> processCode);
        return defaultIfBlank(processCode, sessionProcessCode);
    }

    // -------------------------------------------------------------------------

    public static void setCurrentMethod(final String method) {
        currentMethod.set(method);
    }

    public static String getCurrentMethod(final boolean clear) {
        final String method = currentMethod.get();
        if (clear) {
            currentMethod.remove();
        }
        return method;
    }

    // -------------------------------------------------------------------------

    public static void setCurrentAudSid(final Integer audSid) {
        currentAudSid.set(audSid);
    }

    public static Integer getCurrentAudSid() {
        return currentAudSid.get();
    }

    // -------------------------------------------------------------------------

    public static void clear() {
        currentProcess.remove();
        currentMethod.remove();
        currentAudSid.remove();
    }

}
