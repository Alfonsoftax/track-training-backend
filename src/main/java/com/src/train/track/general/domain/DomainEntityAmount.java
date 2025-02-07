package com.src.train.track.general.domain;

import java.math.BigDecimal;

/**
 * The Interface DomainEntityAmount.
 */
public interface DomainEntityAmount {

    /**
     * Gets the vat base.
     *
     * @return the vat base
     */
    BigDecimal getVatBase();

    /**
     * Gets the vat amount.
     *
     * @return the vat amount
     */
    BigDecimal getVatAmount();

}
