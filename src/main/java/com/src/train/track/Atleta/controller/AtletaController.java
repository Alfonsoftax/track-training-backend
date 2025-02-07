package com.src.train.track.Atleta.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.src.train.track.Atleta.domain.AtletaDto;
import com.src.train.track.Atleta.domain.AtletaFilter;
import com.src.train.track.Atleta.proxy.AtletaProxy;
import com.src.train.track.annotation.ProcessCode;
import com.src.train.track.general.domain.TransactionType;
import com.src.train.track.general.helper.ErrorHelper;
import com.src.train.track.general.serialization.SearchRequestImpl;

import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/atleta")
@RequiredArgsConstructor
public class AtletaController {
    
    private static final Logger logger = LoggerFactory.getLogger(AtletaController.class);

    @Autowired
    private AtletaProxy proxy;
    
	@PostMapping(value = "/search")
    @ProcessCode(transactionType = TransactionType.EJECUTAR)
    public Page<AtletaDto> findPaginated(
            @RequestBody final SearchRequestImpl<AtletaFilter> atletaFilter) {
        if (logger.isDebugEnabled()) {
            logger.debug("SearchRequestImpl<Atleta> atletaFilter - start"); //$NON-NLS-1$
        }

        final Page<AtletaDto> returnPage;
        try {
            returnPage = this.proxy.findPaginated(atletaFilter);

        } catch (final RuntimeException e) {
            ErrorHelper.logError("SearchRequestImpl<Atleta> atletaFilter - start", this.getClass(), //$NON-NLS-1$
                    e);
            throw e;
        }

        if (logger.isDebugEnabled()) {
            logger.debug("SearchRequestImpl<Atleta> atletaFilter - end"); //$NON-NLS-1$
        }
        return returnPage;
    }
}
