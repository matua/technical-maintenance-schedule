package com.matuageorge.technicalmaintenanceschedule.prototype;

import com.matuageorge.technicalmaintenanceschedule.model.Terminal;
import com.matuageorge.technicalmaintenanceschedule.model.TerminalType;

public class TerminalPrototype {
    public static Terminal aTerminal() {
        return Terminal.builder()
                .name("TERM-A")
                .active(true)
                .latitude(55.67966531329961)
                .latitude(55.67966531329962)
                .type(TerminalType.HARDWARE)
                .build();
    }

    public static Terminal bTerminal() {
        return Terminal.builder()
                .name("TERM-B")
                .active(false)
                .latitude(25.12345678901231)
                .latitude(25.12345678901232)
                .type(TerminalType.HARDWARE)
                .build();
    }
}
