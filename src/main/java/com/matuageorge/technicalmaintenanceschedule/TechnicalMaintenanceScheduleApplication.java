package com.matuageorge.technicalmaintenanceschedule;

import com.matuageorge.technicalmaintenanceschedule.exception.NotFoundException;
import com.matuageorge.technicalmaintenanceschedule.model.TerminalType;
import com.matuageorge.technicalmaintenanceschedule.service.TerminalService;
import com.matuageorge.technicalmaintenanceschedule.service.api.PayWayApiService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class TechnicalMaintenanceScheduleApplication implements CommandLineRunner {

    private final PayWayApiService payWayApiService;
    private final TerminalService terminalService;
    private final ModelMapper modelMapper;

    public static void main(String[] args) {
        SpringApplication.run(TechnicalMaintenanceScheduleApplication.class, args);
    }

    @Override
    public void run(String... args) throws NotFoundException {
        log.info("Updating the Terminals DB...");
        terminalService.updateListOfTerminalsInDb(TerminalType.HARDWARE);
    }

}
