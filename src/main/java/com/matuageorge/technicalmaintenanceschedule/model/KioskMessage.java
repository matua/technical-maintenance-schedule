package com.matuageorge.technicalmaintenanceschedule.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@SuperBuilder
public class KioskMessage extends AbstractBaseEntity {
    private String kiosk;
    private String args;
    private String arrived;
    @JsonProperty("id")
    private Long messageId;
    private String name;
    @JsonProperty("occured")
    private String occurred;

    public static class KioskMessageWrapper {
        private final KioskMessage kioskMessage;

        public KioskMessageWrapper(KioskMessage kioskMessage) {
            this.kioskMessage = kioskMessage;
        }

        public KioskMessage unwrap() {
            return kioskMessage;
        }

        public boolean equals(Object other) {
            if (other instanceof KioskMessageWrapper kioskMessageWrapper) {
                return kioskMessageWrapper.kioskMessage.getName().equals(kioskMessage.getName());
            } else {
                return false;
            }
        }

        public int hashCode() {
            return kioskMessage.getName().hashCode();
        }
    }
}