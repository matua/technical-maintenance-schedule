package ug.payway.technicalmaintenanceschedule.model;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

@Getter
public enum TerminalType {

    HARDWARE("Hardware"),
    MOBILE("Mobile"),
    SOFTWARE("Software"),
    WEB("Web");

    @JsonValue
    private final String name;

    TerminalType(String name) {
        this.name = name;
    }
}