package com.matuageorge.technicalmaintenanceschedule.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class KioskCash {
    @JsonProperty("Kiosk")
    public String name;
    @JsonProperty("Location")
    public String location;
}
