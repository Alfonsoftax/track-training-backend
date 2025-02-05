package com.track.training.app.error;


import static org.apache.commons.lang3.StringUtils.contains;
import static org.apache.commons.lang3.StringUtils.split;
import static org.apache.commons.lang3.StringUtils.startsWith;
import org.apache.commons.lang3.exception.ExceptionUtils;

import java.io.Serializable;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.hibernate.dialect.lock.PessimisticEntityLockException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.exception.LockTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;

import jakarta.annotation.Nullable;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ValidationException;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorHelper {

    private static final Logger logger = LoggerFactory.getLogger(ErrorHelper.class);
    public static final String ENTITY_REFERENCED = "EREF";
    public static final String KO = "KO";
    private static final String APPLICATION_ERROR = "ORA-20"; // Oracle application errors starts with -20...
    private static final String INTEGRITY_CONSTRAINT_VIOLATED = "ORA-02292";
    private static final String ENTITY_NOT_FOUND = "%s with id %s not found";

    // -------------------------------------------------------------------------

    public static boolean isCheckedException(final Throwable e) {
        return e instanceof TrainTrackException || e instanceof DuplicateKeyException
                || e instanceof ValidationException && !(e instanceof jakarta.validation.ConstraintViolationException);
    }

    public static boolean isLockException(final Throwable e) {
        return e.getCause() instanceof LockTimeoutException || e.getCause() instanceof PessimisticEntityLockException;
    }

    public static String getApplicationError(final Throwable e) {
        final Throwable rootCause = ExceptionUtils.getRootCause(e);
        if (rootCause != null && startsWith(rootCause.getMessage(), APPLICATION_ERROR)) {
            return split(rootCause.getMessage(), '\n')[0];
        }
        return KO;
    }

    public static String getError(final Throwable e) {
        return getError(e, null);
    }

    public static String getError(final Throwable e, @Nullable final Consumer<String> consumer) {

        final String message = ExceptionUtils.getRootCauseMessage(e);

        if (isCheckedException(e) && e.getCause() == null) {
            if (consumer != null) {
                consumer.accept(message);
            }
            return e.getMessage();
        }

        if (consumer != null) {
            consumer.accept(null);
        }
        final boolean referenced = e.getCause() instanceof ConstraintViolationException
                || contains(message, INTEGRITY_CONSTRAINT_VIOLATED); // needed for DEFERRED contraints
        return referenced ? ENTITY_REFERENCED : getApplicationError(e);
    }

    public static String logError(final String method, final Class<?> clazz, final Throwable e) {

        return getError(e, message -> {
            if (message != null) {
                logger.error("*** Error en la llamada: {} desde {} con mensaje: {}", method, clazz.getName(), message); //$NON-NLS-1$
            } else {
                logger.error("*** Error en la llamada: {} desde {} con traza: ", method, clazz.getName(), e); //$NON-NLS-1$
            }
        });
    }

    public static boolean isErrorMessage(final String message) {
        return message != null && (message.contains(" ") || !message.equals(message.toUpperCase()));
    }

    public static String getErrorCode(final String message) {
        if (!ErrorHelper.isErrorMessage(message)) {
            return message;
        }
        return KO;
    }

    public static Supplier<EntityNotFoundException> notFound(final Class<?> entityClass, final Serializable entityId) {
        return () -> new EntityNotFoundException(
                String.format(ENTITY_NOT_FOUND, entityClass.getSimpleName(), entityId));
    }

}
