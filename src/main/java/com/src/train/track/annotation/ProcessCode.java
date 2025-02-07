package com.src.train.track.annotation;

import static org.apache.commons.lang3.StringUtils.EMPTY;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.src.train.track.general.domain.TransactionType;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ProcessCode {

    String processCode() default EMPTY;

    TransactionType transactionType() default TransactionType.NONE;

}
