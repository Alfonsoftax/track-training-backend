package com.src.train.track.general.serialization;

import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.hibernate6.Hibernate6Module;

/**
 * Jackson serialization configuration, includes Hibernate4 specific configuration.
 */
public class JacksonObjectMapper extends ObjectMapper {

    /** The serial version uid. */
    private static final long serialVersionUID = -570991269408046667L;

    /**
     * Constructor.
     */
    public JacksonObjectMapper() {
        super();
        // Deserialization features
        this.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        // Serialization features
        this.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        this.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);

        // Module registration
        this.registerModule(new SimpleModule().addAbstractTypeMapping(Pageable.class, BackendPageRequest.class));

        this.registerModule(new Hibernate6Module()
                .configure(Hibernate6Module.Feature.SERIALIZE_IDENTIFIER_FOR_LAZY_NOT_LOADED_OBJECTS, true));
    }

}
