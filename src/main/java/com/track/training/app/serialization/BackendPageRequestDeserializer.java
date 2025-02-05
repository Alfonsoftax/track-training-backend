package com.track.training.app.serialization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.NullHandling;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.NullNode;

public class BackendPageRequestDeserializer extends JsonDeserializer<BackendPageRequest> {

    /**
     * {@inheritDoc}
     */
    @Override
    public BackendPageRequest deserialize(final JsonParser jsonParser, final DeserializationContext ctxt)
            throws IOException {
        final BackendPageRequest result;
        final ObjectCodec oc = jsonParser.getCodec();
        final JsonNode node = oc.readTree(jsonParser);

        final JsonNode size = node.get("size");
        final JsonNode page = node.get("page");
        final JsonNode direction = node.get("direction");
        final JsonNode properties = node.get("properties");
        final JsonNode nullHandling = node.get("nullHandling");

        if (direction == null || properties == null || direction instanceof NullNode
                || properties instanceof NullNode) {
            result = new BackendPageRequest(page.asInt(), size.asInt());
        } else {
            final Iterator<JsonNode> it = ((ArrayNode) properties).iterator();
            final List<String> propertiesList = new ArrayList<>();
            while (it.hasNext()) {
                propertiesList.add(it.next().asText());
            }
            final String[] propertiesArray = new String[propertiesList.size()];

            if (nullHandling == null || nullHandling instanceof NullNode) {
                result = new BackendPageRequest(page.asInt(), size.asInt(), Direction.fromString(direction.asText()),
                        propertiesList.toArray(propertiesArray));

            } else {
                final Iterator<JsonNode> itNullHandling = ((ArrayNode) nullHandling).iterator();
                final List<NullHandling> nullHandlingList = new ArrayList<>();
                while (itNullHandling.hasNext()) {
                    nullHandlingList.add(getNullHandling(itNullHandling.next().asText()));
                }
                final NullHandling[] nullHandlingArray = new NullHandling[nullHandlingList.size()];

                result = new BackendPageRequest(page.asInt(), size.asInt(), Direction.fromString(direction.asText()),
                        propertiesList.toArray(propertiesArray), nullHandlingList.toArray(nullHandlingArray));
            }
        }

        return result;
    }

    /**
     * Gets the null handling.
     *
     * @param nullHandlingValue
     *            the null handling value
     * @return the null handling
     */
    private static NullHandling getNullHandling(final String nullHandlingValue) {
        try {
            return NullHandling.valueOf(nullHandlingValue);
        } catch (final Exception e) {
            return NullHandling.NATIVE;
        }
    }

}
