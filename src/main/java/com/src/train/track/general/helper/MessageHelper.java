package com.src.train.track.general.helper;

import static com.src.train.track.general.utils.Safe.COMMA_SEP;
import static org.apache.commons.lang3.StringUtils.containsAny;
import static org.apache.commons.lang3.StringUtils.removeEnd;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Component;

import com.src.train.track.general.utils.Safe;


@Component
public class MessageHelper {

    @Autowired
    private MessageSource messageSource;

    // -------------------------------------------------------------------------

    public String getMessage(final String messageCode) {
        return this.getMessage(messageCode, messageCode);
    }

    public String getMessage(final String messageCode, final String defaultMessage) {
        try {
            final Locale locale = SessionUtils.getUserLocale();
            return this.messageSource.getMessage(messageCode, null, locale);
        } catch (final NoSuchMessageException e) {
            return defaultMessage;
        }
    }

    public String getMessageWithParams(final String messageCode, final Object... params) {
        String message = this.getMessage(messageCode);
        if (message != null && params != null && params.length > 0) {
            message = String.format(message, params);
        }
        return message;
    }

    public String getMessageFile(final boolean ok, final String fileName) {
        final StringBuilder msgKey = new StringBuilder("saving.file");
        if (containsAny(fileName, "*?")) {
            msgKey.append("s");
        }
        msgKey.append(ok ? ".ok" : ".ko");

        String msg = this.getMessage(msgKey.toString());
        if (fileName != null && msg != null) {
            msg = removeEnd(msg, ".") + ": " + fileName;
        }
        return msg;
    }

    public String join(final String lastSeparatorCode, final String... values) {
        final String lastSep = this.getMessage(lastSeparatorCode, null);
        return Safe.join(COMMA_SEP, lastSep, values);
    }

}
