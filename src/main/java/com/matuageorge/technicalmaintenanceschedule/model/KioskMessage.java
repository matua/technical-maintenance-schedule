package com.matuageorge.technicalmaintenanceschedule.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.IOException;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@SuperBuilder
public class KioskMessage extends AbstractBaseEntity {
    @JsonProperty("args")
    @JsonDeserialize(using = StringToListOfMaps.class)
    private Map<String, Object> args;
    private String arrived;
    @JsonProperty("id")
    private Long messageId;
    private String kiosk;
    private String name;
    @JsonProperty("occured")
    private String occurred;

    @Data
    public static class ErrorMessage {
        private Map<String, String> args;
    }

    private static final class StringToListOfMaps extends JsonDeserializer<Map<String, String>> {

        @Override
        public Map<String, String> deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
            return new ObjectMapper().readValue(p.getValueAsString(), Map.class);
        }
    }
}