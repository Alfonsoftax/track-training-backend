package com.src.train.track.general.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * The Class SpringUtil.
 */
@Component
public class SpringUtil implements ApplicationContextAware {

    /** The context. */
    private static ApplicationContext context;

    /**
     * {@inheritDoc}
     */
    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) {
        context = applicationContext; // NOSONAR
    }

    /**
     * Gets the context.
     *
     * @return the context
     */
    public static ApplicationContext getContext() {
        return context;
    }

}
